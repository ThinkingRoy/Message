package net.mckitsu.message.event.nonpublictype;

import net.mckitsu.message.event.MessageBuilder;
import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.myinterface.ChatProcessor;
import net.mckitsu.message.userconfig.UserConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PrivateChat{

    MessageBuilder messageBuilder = new MessageBuilder();


    public void onPrivateChat(Player sender,Player recipient , String message, LocaleConfig localeConfig) {

        char prefix = '~';
        String forSender = messageBuilder.buildPrivateSenderMessage(recipient, localeConfig, message, prefix);
        String forRecipient = messageBuilder.buildPrivateRecipientMessage(sender, localeConfig, message, prefix);

        sender.sendMessage(forSender);
        if(recipient.isOnline()) {
            recipient.sendMessage(forRecipient);
        }else {
            sender.sendMessage("offline");
        }

    }
}
