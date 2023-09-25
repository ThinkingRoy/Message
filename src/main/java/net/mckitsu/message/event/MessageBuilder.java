package net.mckitsu.message.event;

import net.mckitsu.message.locale.LocaleConfig;
import org.bukkit.entity.Player;

public class MessageBuilder {


    public String buildPublicMessage(Player p, LocaleConfig localeConfig, String message, char prefix, int channel) {

        String format = localeConfig.getPublicMessageFormat();

        if(format == null)
            return null;

        String prefixFormat = localeConfig.getPrefixConfig(prefix);

        if(prefixFormat == null)
            return null;

        return String.format(format, prefixFormat, channel, p.getName(), message);

    }

    public String buildPrivateSenderMessage(Player recipient, LocaleConfig config, String message, char prefix) {

        String formatSender = config.getMessagePrivateSenderFormat();

        if(formatSender == null)
            return null;

        String prefixFormat = config.getPrefixConfig(prefix);

        if(prefixFormat == null)
            return null;

        return String.format(formatSender, prefixFormat, recipient, message);
    }

    public String buildPrivateRecipientMessage(Player sender, LocaleConfig config, String message, char prefix) {
        String formatRecipient = config.getMessagePrivateRecipientFormat();

        if(formatRecipient == null)
            return null;

        String prefixFormat = config.getPrefixConfig(prefix);

        if(prefixFormat == null)
            return null;

        return String.format(formatRecipient, prefixFormat, sender, message);
    }



}
