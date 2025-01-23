package org.mvplugins.multiverse.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.dumptruckman.minecraft.util.Logging;
import jakarta.inject.Inject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jvnet.hk2.annotations.Service;

import org.mvplugins.multiverse.core.commandtools.MVCommandIssuer;
import org.mvplugins.multiverse.core.commandtools.MVCommandManager;
import org.mvplugins.multiverse.core.commandtools.flags.CommandFlag;
import org.mvplugins.multiverse.core.commandtools.flags.CommandValueFlag;
import org.mvplugins.multiverse.core.commandtools.flags.ParsedCommandFlags;
import org.mvplugins.multiverse.core.commandtools.queue.CommandQueuePayload;
import org.mvplugins.multiverse.core.locale.MVCorei18n;
import org.mvplugins.multiverse.core.utils.WorldTickDeferrer;
import org.mvplugins.multiverse.core.locale.message.Message;
import org.mvplugins.multiverse.core.utils.result.Async;
import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.WorldManager;
import org.mvplugins.multiverse.core.world.helpers.PlayerWorldTeleporter;
import org.mvplugins.multiverse.core.world.options.RegenWorldOptions;

import static org.mvplugins.multiverse.core.locale.message.MessageReplacement.replace;

@Service
@CommandAlias("mv")
final class RegenCommand extends CoreCommand {

    private final WorldManager worldManager;
    private final PlayerWorldTeleporter playerWorldTeleporter;
    private final WorldTickDeferrer worldTickDeferrer;

    private final CommandValueFlag<String> SEED_FLAG = flag(CommandValueFlag.builder("--seed", String.class)
            .addAlias("-s")
            .completion(input -> Collections.singleton(String.valueOf(new Random().nextLong())))
            .optional()
            .build());

    private final CommandFlag RESET_WORLD_CONFIG_FLAG = flag(CommandFlag.builder("--reset-world-config")
            .addAlias("-wc")
            .build());

    private final CommandFlag RESET_GAMERULES_FLAG = flag(CommandFlag.builder("--reset-gamerules")
            .addAlias("-gm")
            .build());

    private final CommandFlag RESET_WORLD_BORDER_FLAG = flag(CommandFlag.builder("--reset-world-border")
            .addAlias("-wb")
            .build());

    private final CommandFlag REMOVE_PLAYERS_FLAG = flag(CommandFlag.builder("--remove-players")
            .addAlias("-r")
            .build());

    @Inject
    RegenCommand(
            @NotNull MVCommandManager commandManager,
            @NotNull WorldManager worldManager,
            @NotNull PlayerWorldTeleporter playerWorldTeleporter,
            @NotNull WorldTickDeferrer worldTickDeferrer) {
        super(commandManager);
        this.worldManager = worldManager;
        this.playerWorldTeleporter = playerWorldTeleporter;
        this.worldTickDeferrer = worldTickDeferrer;
    }

    @CommandAlias("mvregen")
    @Subcommand("regen")
    @CommandPermission("multiverse.core.regen")
    @CommandCompletion("@mvworlds:scope=loaded @flags:groupName=mvregencommand")
    @Syntax("<world> [--seed [seed] --reset-world-config --reset-gamerules --reset-world-border --remove-players]")
    @Description("{@@mv-core.regen.description}")
    void onRegenCommand(
            MVCommandIssuer issuer,

            @Syntax("<world>")
            @Description("{@@mv-core.regen.world.description}")
            LoadedMultiverseWorld world,

            @Optional
            @Syntax("[--seed [seed] --reset-world-config --reset-gamerules --reset-world-border --remove-players]")
            @Description("{@@mv-core.regen.other.description}")
            String[] flags) {
        ParsedCommandFlags parsedFlags = parseFlags(flags);

        this.commandManager.getCommandQueueManager().addToQueue(CommandQueuePayload
                .issuer(issuer)
                .action(() -> runRegenCommand(issuer, world, parsedFlags))
                .prompt(Message.of(MVCorei18n.REGEN_PROMPT, "",
                        replace("{world}").with(world.getName()))));
    }

    private void runRegenCommand(MVCommandIssuer issuer, LoadedMultiverseWorld world, ParsedCommandFlags parsedFlags) {
        issuer.sendInfo(MVCorei18n.REGEN_REGENERATING, "{world}", world.getName());
        List<Player> worldPlayers = world.getPlayers().getOrElse(Collections.emptyList());

        var future = parsedFlags.hasFlag(REMOVE_PLAYERS_FLAG)
                ? playerWorldTeleporter.removeFromWorld(world)
                : Async.completedFuture(Collections.emptyList());

        // todo: using future will hide stacktrace
        future.thenRun(() -> worldTickDeferrer.deferWorldTick(() ->
                doWorldRegening(issuer, world, parsedFlags, worldPlayers)));
    }

    private void doWorldRegening(
            MVCommandIssuer issuer,
            LoadedMultiverseWorld world,
            ParsedCommandFlags parsedFlags,
            List<Player> worldPlayers) {
        //todo: Change biome on regen
        RegenWorldOptions regenWorldOptions = RegenWorldOptions.world(world)
                .randomSeed(parsedFlags.hasFlag(SEED_FLAG))
                .seed(parsedFlags.flagValue(SEED_FLAG))
                .keepWorldConfig(!parsedFlags.hasFlag(RESET_WORLD_CONFIG_FLAG))
                .keepGameRule(!parsedFlags.hasFlag(RESET_GAMERULES_FLAG))
                .keepWorldBorder(!parsedFlags.hasFlag(RESET_WORLD_BORDER_FLAG));

        worldManager.regenWorld(regenWorldOptions).onSuccess(newWorld -> {
            Logging.fine("World regen success: " + newWorld);
            issuer.sendInfo(MVCorei18n.REGEN_SUCCESS, "{world}", newWorld.getName());
            if (parsedFlags.hasFlag(REMOVE_PLAYERS_FLAG)) {
                playerWorldTeleporter.teleportPlayersToWorld(worldPlayers, newWorld);
            }
        }).onFailure(failure -> {
            Logging.warning("World regen failure: " + failure);
            issuer.sendError(failure.getFailureMessage());
        });
    }
}
