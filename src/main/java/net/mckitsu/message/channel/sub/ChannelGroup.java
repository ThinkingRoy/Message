package net.mckitsu.message.channel.sub;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChannelGroup {
    private final Map<Integer, ChannelSubGroup> channelSubGroupMap;

    public ChannelGroup() {
        this.channelSubGroupMap = new HashMap<>();
    }

    public Map<Integer, ChannelSubGroup> getChannelSubGroupMap() {
        return this.channelSubGroupMap;
    }

    public boolean isExist(int channel) {
        return this.channelSubGroupMap.get(channel) != null;
    }

    public ChannelSubGroup getChannelSubGroup(UUID uuid) {

        for(Map.Entry<Integer, ChannelSubGroup> entry : channelSubGroupMap.entrySet()){
            ChannelSubGroup s = entry.getValue();
            if(s == null)
                continue;

            if(s.isExist(uuid))
                return s;
        }
        return null;
    }

    public void remove(UUID player) {
        ChannelSubGroup channelSubGroup = this.getChannelSubGroup(player);
        channelSubGroup.remove(player);
    }

}
