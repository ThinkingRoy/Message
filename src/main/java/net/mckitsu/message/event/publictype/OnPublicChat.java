package net.mckitsu.message.event.publictype;

import net.mckitsu.message.channel.Channel;
import net.mckitsu.message.channel.sub.ChannelSubGroup;
import net.mckitsu.message.event.MessageBuilder;
import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.myinterface.ChatProcessor;
import net.mckitsu.message.userconfig.UserConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OnPublicChat implements ChatProcessor {

    Channel channel;


    MessageBuilder builder = new MessageBuilder();


    public OnPublicChat(Channel channel){
        this.channel = channel;
    }

    @Override
    public void onChat(String message, char prefix, UUID sender, UserConfig userConfig, LocaleConfig localeConfig) {
        try {
            Player p = Bukkit.getPlayer(sender);
            if(p == null) {return;}

            List<UUID> ignoreList = userConfig.getIgnoreList();
            Integer subChannel = userConfig.getChannelType().getSubChannel(prefix);
            if(subChannel == null) {
                p.sendMessage(localeConfig.getSubChannelIsNull());
                return;
            }
            StringBuilder msg = new StringBuilder(message);
            msg.deleteCharAt(0);
            String broadcast = builder.buildPublicMessage(p, localeConfig, msg.toString(), prefix, subChannel);
            if(broadcast == null) {
                p.sendMessage(localeConfig.getContactWithAdmin());
                return;
            }
            channel.broadcast(sender, prefix, broadcast, ignoreList);

        }catch (NullPointerException e) {
            Player p = Bukkit.getPlayer(sender);
            if(p == null) {return;}
            p.sendMessage(localeConfig.getNotJoinChannelYet());
        }
    }
}
