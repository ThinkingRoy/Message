package net.mckitsu.message.event;

import net.mckitsu.message.channel.Channel;
import net.mckitsu.message.event.nonpublictype.GuildChat;
import net.mckitsu.message.event.nonpublictype.RegionChat;
import net.mckitsu.message.event.nonpublictype.TeamChat;
import net.mckitsu.message.event.publictype.OnPublicChat;
import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.myinterface.ChatProcessor;
import net.mckitsu.message.userconfig.UserConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class OnChatEvent implements Listener {
    Map<String, LocaleConfig> localeConfigMap;
    Map<Character, ChatProcessor> functionMap = new HashMap<>();
    Channel channel;
    Map<UUID, UserConfig> userConfigMap;
    Map<Character, Object> publicChatPrefix = new HashMap<>();
    public OnChatEvent(Map<String, LocaleConfig> localeConfigMap, Channel channel, Map<UUID, UserConfig> userConfigMap) {
        this.localeConfigMap = localeConfigMap;
        this.channel = channel;
        this.userConfigMap = userConfigMap;
        this.publicChatPrefix.put('#', null);
        this.publicChatPrefix.put('$', null);
        for (char cc : this.publicChatPrefix.keySet()) {
            this.functionMap.put(cc, new OnPublicChat(channel));
        }
        this.functionMap.put('*', new RegionChat());
        this.functionMap.put('&', new GuildChat());
        this.functionMap.put('%', new TeamChat());
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player p = e.getPlayer();
        String locale = p.getLocale();
        String rawMessage = e.getMessage();
        UUID sender = p.getUniqueId();
        UserConfig userConfig = userConfigMap.get(sender);

        StringBuilder str = new StringBuilder(rawMessage);
        LocaleConfig localeConfig = this.localeConfigMap.get(locale);

        char c = str.charAt(0);
        char prefix;
        if(localeConfig.getPrefixConfig(c) == null) {
            prefix = '*';
            ChatProcessor region = this.functionMap.get(prefix);
            region.onChat(rawMessage,prefix, sender, userConfig, localeConfig);
        }else {
            prefix = c;
            ChatProcessor processor = this.functionMap.get(prefix);
            processor.onChat(rawMessage,prefix, sender, userConfig, localeConfig);
        }
    }
}
