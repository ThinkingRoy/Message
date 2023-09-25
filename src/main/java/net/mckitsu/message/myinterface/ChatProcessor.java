package net.mckitsu.message.myinterface;

import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.userconfig.UserConfig;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ChatProcessor {

    void onChat(String message, char prefix, UUID sender, UserConfig userConfig, LocaleConfig localeConfig);

}
