package net.mckitsu.message.event.nonpublictype;

import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.myinterface.ChatProcessor;
import net.mckitsu.message.userconfig.UserConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class TeamChat implements ChatProcessor {


    @Override
    public void onChat(String message, char prefix, UUID sender, UserConfig userConfig, LocaleConfig localeConfig) {
        Player p = Bukkit.getPlayer(sender);
        if(p == null) {return;}
        p.sendMessage(localeConfig.getPrefixConfig(prefix));
    }
}
