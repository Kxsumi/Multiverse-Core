package com.onarandombox.MultiverseCore.worldnew.reasons;

import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;
import com.onarandombox.MultiverseCore.utils.MVCorei18n;
import com.onarandombox.MultiverseCore.utils.result.FailureReason;

/**
 * Result of a world removal operation.
 */
public enum RemoveFailureReason implements FailureReason {
    /**
     * The world does not exist.
     */
    WORLD_NON_EXISTENT(MVCorei18n.REMOVEWORLD_WORLDNONEXISTENT),

    /**
     * The world could not be unloaded.
     */
    UNLOAD_FAILED(MVCorei18n.GENERIC_FAILURE);

    private final MessageKeyProvider message;

    RemoveFailureReason(MessageKeyProvider message) {
        this.message = message;
    }

    @Override
    public MessageKey getMessageKey() {
        return message.getMessageKey();
    }
}
