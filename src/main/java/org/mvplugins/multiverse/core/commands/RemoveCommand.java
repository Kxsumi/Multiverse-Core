package org.mvplugins.multiverse.core.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.dumptruckman.minecraft.util.Logging;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jvnet.hk2.annotations.Service;

import org.mvplugins.multiverse.core.commandtools.MVCommandIssuer;
import org.mvplugins.multiverse.core.commandtools.MVCommandManager;
import org.mvplugins.multiverse.core.commandtools.MultiverseCommand;
import org.mvplugins.multiverse.core.utils.MVCorei18n;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.WorldManager;

@Service
@CommandAlias("mv")
class RemoveCommand extends MultiverseCommand {

    private final WorldManager worldManager;

    @Inject
    RemoveCommand(@NotNull MVCommandManager commandManager, @NotNull WorldManager worldManager) {
        super(commandManager);
        this.worldManager = worldManager;
    }

    @Subcommand("remove")
    @CommandPermission("multiverse.core.remove")
    @CommandCompletion("@mvworlds:scope=both")
    @Syntax("<world>")
    @Description("{@@mv-core.remove.description}")
    void onRemoveCommand(
            MVCommandIssuer issuer,

            @Single
            @Conditions("mvworlds:scope=both")
            @Syntax("<world>")
            @Description("{@@mv-core.remove.world.description}")
            MultiverseWorld world) {
        worldManager.removeWorld(world)
                .onSuccess(removedWorldName -> {
                    Logging.fine("World remove success: " + removedWorldName);
                    issuer.sendInfo(MVCorei18n.REMOVE_SUCCESS, "{world}", removedWorldName);
                }).onFailure(failure -> {
                    Logging.fine("World remove failure: " + failure);
                    issuer.sendError(failure.getFailureMessage());
                });
    }
}
