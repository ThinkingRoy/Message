package net.mckitsu.message.userconfig;

import net.mckitsu.message.Message;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserConfigParser {

    public UserConfig parseUserConfig(File file) {
        UserConfig userConfig = new UserConfig();

        try {
            String s = Files.readString(file.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> topClassMap = yaml.load(s);

            Object o1 = topClassMap.get("ignoreList");

            Object o2 = topClassMap.get("channelTypeList");

            if(o1 instanceof List) {
                List<Object> list = (List<Object>) o1;
                userConfig.setIgnoreList(list);
            }

            if(o2 instanceof List){
                List<Object> list = (List<Object>) o2;
                ChannelType channelType = new ChannelType();
                for(Object o : list){
                    if(o instanceof Map){
                        Map<String, Object> map = (Map<String, Object>) o;
                        channelType.setChannelType(map);
                        userConfig.addChannelType(channelType);
                    }
                }
            }
            return userConfig;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
