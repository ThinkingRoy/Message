package net.mckitsu.message.channel;

import lombok.Getter;
import net.mckitsu.message.Message;
import net.mckitsu.message.channel.sub.ChannelGroup;
import net.mckitsu.message.channel.sub.ChannelSubGroup;
import net.mckitsu.message.event.MessageBuilder;
import net.mckitsu.message.userconfig.ChannelType;
import net.mckitsu.message.userconfig.UserConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class Channel {

    private final Map<Character, ChannelGroup> channelGroupMap;

    public Channel(){
        this.channelGroupMap = new HashMap<>();
    }

    public boolean isExist(char prefix) {
        return this.channelGroupMap.get(prefix) != null;
    }

    public void broadcast(UUID uuid, char prefix, String message, List<UUID> ignoreList) {
        if(this.getChannelGroupMap().get(prefix) == null)
            return;
        if(this.getChannelGroupMap().get(prefix).getChannelSubGroup(uuid) == null)
            return;


        this.getChannelGroupMap().get(prefix).getChannelSubGroup(uuid).broadcastMessage(message, ignoreList);
    }

    public void join(Map<UUID, UserConfig> userConfigMap, UUID uuid) {
        UserConfig config = userConfigMap.get(uuid);

        if(config == null)
            return;

        ChannelType channelType = config.getChannelType();

        if(channelType == null)
            return;

        for (Map.Entry<Character, Integer> entry : channelType.getChannelType().entrySet()) {

            char prefix = entry.getKey();
            int subChannel = entry.getValue();

            if(this.channelGroupMap.get(prefix) == null) {
                ChannelGroup channelGroup = new ChannelGroup();
                ChannelSubGroup channelSubGroup = new ChannelSubGroup();
                channelSubGroup.add(uuid);
                channelGroup.getChannelSubGroupMap().put(subChannel, channelSubGroup);
                this.channelGroupMap.put(prefix, channelGroup);
            }else {
                ChannelGroup channelGroup = this.channelGroupMap.get(prefix);
                if(channelGroup.getChannelSubGroupMap().get(subChannel) == null) {
                    ChannelSubGroup newChannelSubGroup = new ChannelSubGroup();
                    newChannelSubGroup.add(uuid);
                    channelGroup.getChannelSubGroupMap().put(subChannel, newChannelSubGroup);
                }else {
                    ChannelSubGroup channelSubGroup = channelGroup.getChannelSubGroupMap().get(subChannel);
                    if(channelSubGroup.isEmpty() || !channelSubGroup.isExist(uuid)) {
                        channelSubGroup.add(uuid);
                    }
                }
            }
        }
    }

    public void leave(UUID uuid, char prefix) {
        ChannelGroup channelGroup = this.channelGroupMap.get(prefix);
        channelGroup.remove(uuid);
    }

}
