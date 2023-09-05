package com.onarandombox.MultiverseCore.utils;

import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;
import com.onarandombox.MultiverseCore.utils.message.Message;
import com.onarandombox.MultiverseCore.utils.message.MessageReplacement;
import org.jetbrains.annotations.NotNull;

public enum MVCorei18n implements MessageKeyProvider {
    // config status
    CONFIG_SAVE_FAILED,
    CONFIG_NODE_NOTFOUND,

    // check command
    CHECK_CHECKING,

    // clone command
    CLONE_CLONING,
    CLONE_FAILED,
    CLONE_SUCCESS,

    // Coordinates command
    COORDINATES_INFO_TITLE,
    COORDINATES_INFO_WORLD,
    COORDINATES_INFO_ALIAS,
    COORDINATES_INFO_WORLDSCALE,
    COORDINATES_INFO_COORDINATES,
    COORDINATES_INFO_DIRECTION,

    // create command
    CREATE_PROPERTIES,
    CREATE_PROPERTIES_ENVIRONMENT,
    CREATE_PROPERTIES_SEED,
    CREATE_PROPERTIES_WORLDTYPE,
    CREATE_PROPERTIES_ADJUSTSPAWN,
    CREATE_PROPERTIES_GENERATOR,
    CREATE_PROPERTIES_STRUCTURES,
    CREATE_LOADING,

    // delete command
    DELETE_DELETING,
    DELETE_PROMPT,

    // Dumps command
    DUMPS_DESCRIPTION,
    DUMPS_URL_LIST,

    // gamerule command
    GAMERULE_FAILED,
    GAMERULE_SUCCESS_SINGLE,
    GAMERULE_SUCCESS_MULTIPLE,

    // import command
    IMPORT_IMPORTING,

    // load command
    LOAD_LOADING,

    // regen command
    REGEN_REGENERATING,
    REGEN_FAILED,
    REGEN_SUCCESS,
    REGEN_PROMPT,

    // reload command
    RELOAD_RELOADING,
    RELOAD_SUCCESS,

    // root MV command
    ROOT_TITLE,
    ROOT_HELP,

    // teleport command
    TELEPORT_SUCCESS,

    // unload command
    UNLOAD_UNLOADING,

    // debug command
    DEBUG_INFO_OFF,
    DEBUG_INFO_ON,

    // commands error
    COMMANDS_ERROR_PLAYERSONLY,
    COMMANDS_ERROR_MULTIVERSEWORLDONLY,

    // entry check
    ENTRYCHECK_BLACKLISTED,
    ENTRYCHECK_NOTENOUGHMONEY,
    ENTRYCHECK_CANNOTPAYENTRYFEE,
    ENTRYCHECK_EXCEEDPLAYERLIMIT,
    ENTRYCHECK_NOWORLDACCESS,

    // world manager result
    CREATEWORLD_CREATED,
    CREATEWORLD_INVALIDWORLDNAME,
    CREATEWORLD_WORLDEXISTFOLDER,
    CREATEWORLD_WORLDEXISTOFFLINE,
    CREATEWORLD_WORLDEXISTLOADED,
    CREATEWORLD_BUKKITCREATIONFAILED,

    DELETEWORLD_DELETED,
    DELETEWORLD_WORLDNONEXISTENT,
    DELETEWORLD_WORLDFOLDERNOTFOUND,
    DELETEWORLD_FAILEDTODELETEFOLDER,

    IMPORTWORLD_IMPORTED,
    IMPORTWORLD_INVALIDWORLDNAME,
    IMPORTWORLD_WORLDFOLDERINVALID,
    IMPORTWORLD_WORLDEXISTOFFLINE,
    IMPORTWORLD_WORLDEXISTLOADED,
    IMPORTWORLD_BUKKITCREATIONFAILED,

    LOADWORLD_LOADED,
    LOADWORLD_WORLDALREADYLOADING,
    LOADWORLD_WORLDNONEXISTENT,
    LOADWORLD_WORLDEXISTFOLDER,
    LOADWORLD_WORLDEXISTLOADED,
    LOADWORLD_BUKKITCREATIONFAILED,

    REMOVEWORLD_REMOVED,
    REMOVEWORLD_WORLDNONEXISTENT,

    UNLOADWORLD_UNLOADED,
    UNLOADWORLD_WORLDALREADYUNLOADING,
    UNLOADWORLD_WORLDNONEXISTENT,
    UNLOADWORLD_WORLDOFFLINE,
    UNLOADWORLD_BUKKITUNLOADFAILED,

    // generic
    GENERIC_SUCCESS,
    GENERIC_FAILURE

    ;

    private final MessageKey key = MessageKey.of("mv-core." + this.name().replace('_', '.').toLowerCase());

    @Override
    public MessageKey getMessageKey() {
        return this.key;
    }

    @NotNull
    public Message bundle(@NotNull String nonLocalizedMessage, @NotNull MessageReplacement... replacements) {
        return Message.of(this, nonLocalizedMessage, replacements);
    }
}
