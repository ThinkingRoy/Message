package net.mckitsu.message.event.joinandleave;

import net.mckitsu.message.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class OnJoinAndLeave implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        event.setJoinMessage("");
        Message.getPlugin().joinChannel(uuid);
    }

    @EventHandler
    public void OnLeaveEvent(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        event.setQuitMessage("");
        Message.getPlugin().leaveChannel(uuid);
    }

}
