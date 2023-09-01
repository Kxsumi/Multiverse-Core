package com.onarandombox.MultiverseCore.utils.result;

import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;
import com.onarandombox.MultiverseCore.utils.MVCorei18n;

public interface SuccessReason extends MessageKeyProvider {
    default MessageKey getMessageKey() {
        return MVCorei18n.GENERIC_SUCCESS.getMessageKey();
    }
}
