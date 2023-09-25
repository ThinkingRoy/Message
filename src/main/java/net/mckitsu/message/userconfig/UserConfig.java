package net.mckitsu.message.userconfig;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class UserConfig {

    @Getter
    private List<UUID> ignoreList;
    @Getter
    private ChannelType channelType;

    public UserConfig() {
        this.ignoreList = new ArrayList<>();
    }

    public void setIgnoreList(List<Object> list) {
        for (Object o : list){
            if(o instanceof String){
                String s = (String) o;
                UUID uuid = UUID.fromString(s);
                this.ignoreList.add(uuid);
            }
        }
    }

    public void addIgnoreList(UUID player) {
        this.removeIgnoreList(player);
        this.ignoreList.add(player);
    }

    public void removeIgnoreList(UUID player) {
        this.ignoreList.remove(player);
    }

    public void addChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public void removeChannelType(char prefix) {
        this.channelType.remove(prefix);
    }

}
