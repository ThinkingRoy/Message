package net.mckitsu.message.channel.sub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ChannelSubGroup {
    private final Map<UUID, Objects> subGroupMap;

    public ChannelSubGroup(){
        this.subGroupMap = new HashMap<>();
    }

    public void add(UUID player) {
        this.subGroupMap.put(player, null);
    }

    public void remove(UUID player) {
        this.subGroupMap.remove(player);
    }

    public boolean isExist(UUID player) {
        return this.subGroupMap.containsKey(player);
    }

    public boolean isEmpty() {
        return this.subGroupMap.isEmpty();
    }

    public Map<UUID, Objects> getSubGroupMap() {
        return this.subGroupMap;
    }


    public void broadcastMessage(String message, List<UUID> ignoreList) {

        for(UUID uuid : this.subGroupMap.keySet()) {
            if(ignoreList == null) {
                Player p = Bukkit.getPlayer(uuid);

                if(p == null) {return;}

                p.sendMessage(message);
            }else {
                for (UUID ignore : ignoreList) {

                    if (!uuid.equals(ignore)) {
                        Player p = Bukkit.getPlayer(uuid);

                        if(p == null) {return;}

                        p.sendMessage(message);
                    }

                }
            }

        }
    }
}
