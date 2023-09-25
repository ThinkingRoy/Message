package net.mckitsu.message.userconfig;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Getter
public class ChannelType {

    private final Map<Character, Integer> channelType;

    public ChannelType() {this.channelType = new HashMap<>();}
    public void setChannelType(Map<String, Object> map){
        Map<Character, Integer> m = new HashMap<>();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            String s = entry.getKey();
            StringBuilder str = new StringBuilder(s);
            char prefix = str.charAt(0);
            Object o = entry.getValue();
            if(o instanceof Integer) {
                int channel = (int) o;
                this.channelType.put(prefix, channel);
            }
        }
    }
    public Integer getSubChannel (char prefix) {
        if(!this.isExist(prefix)) {return 0;}
        return this.channelType.get(prefix);
    }
    public boolean isExist(char prefix) {return this.channelType.get(prefix) != null;}

    public void remove(char prefix) {
        if(this.isExist(prefix)) {
            this.channelType.remove(prefix);
        }
    }
    public void add(char prefix, int channel) {
        this.channelType.put(prefix, channel);
    }
}
