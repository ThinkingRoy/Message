package net.mckitsu.message;

import net.mckitsu.message.OnCommand.CommandHandler;
import net.mckitsu.message.Reload.OnReload;
import net.mckitsu.message.channel.Channel;
import net.mckitsu.message.event.OnChatEvent;
import net.mckitsu.message.event.joinandleave.OnJoinAndLeave;
import net.mckitsu.message.locale.LocalConfigParser;
import net.mckitsu.message.locale.LocaleConfig;
import net.mckitsu.message.userconfig.ChannelType;
import net.mckitsu.message.userconfig.UserConfig;
import net.mckitsu.message.userconfig.UserConfigParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class Message extends JavaPlugin {
    public static Message pluginMessageStream;

    public static Message getPlugin(){return Message.pluginMessageStream;}

    FileLoader fileLoader = new FileLoader();
    LocalConfigParser localConfigParser = new LocalConfigParser();
    UserConfigParser userConfigParser = new UserConfigParser();
    Map<UUID, UserConfig> userConfigMap = new HashMap<>();
    Map<String, LocaleConfig> localeConfigMap = new HashMap<>();
    Channel channel = new Channel();

    @Override
    public void onEnable() {

        // Plugin startup logic
        this.channel = new Channel();
        Message.pluginMessageStream = this;
        loadFile();
        getServer().getPluginManager().registerEvents(new OnChatEvent(localeConfigMap, this.channel, userConfigMap), this);
        getServer().getPluginCommand("msgreload").setExecutor(new OnReload());
        getServer().getPluginCommand("channel").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new OnJoinAndLeave(), this);
        this.getLogger().info(this.userConfigMap.keySet().toString());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void addUserConfig(UUID uuid, char prefix, int subChannel) {

        UserConfig userConfig = new UserConfig();
        userConfig.getChannelType().add(prefix, subChannel);
        this.userConfigMap.put(uuid, userConfig);

    }

    public void joinViaCommand(UUID uuid, char prefix, int subChannel) {

        if (this.userConfigMap.get(uuid) == null)
            this.addUserConfig(uuid, prefix, subChannel);

        UserConfig userConfig = this.userConfigMap.get(uuid);
        userConfig.getChannelType().add(prefix, subChannel);
        this.joinChannel(uuid);
    }

    public void LeaveViaCommand(UUID uuid, char prefix) {
        this.channel.leave(uuid, prefix);
    }

    public void joinChannel(UUID uuid) {
        this.channel.join(this.userConfigMap, uuid);
    }

    public void leaveChannel(UUID uuid) {
        UserConfig userConfig = this.userConfigMap.get(uuid);
        ChannelType channelType = userConfig.getChannelType();

        for(char prefix : channelType.getChannelType().keySet()) {
            if (this.channel.getChannelGroupMap().get(prefix).getChannelSubGroup(uuid) != null) {
                channelType.remove(prefix);
                this.channel.leave(uuid, prefix);
            }
        }

    }

    public void loadFile(){
        this.getDataFolder().mkdirs();

        String directoryPath = this.getDataFolder().getPath();
        File directory = new File(directoryPath);

        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if(files == null){
                this.getLogger().info("files are null!");
            }else {
                for(File file : files){

                    if(fileLoader.exists(file)){
                        this.getLogger().info(file.getName() + "讀取成功");
                    }else {
                        this.getLogger().info("讀取失敗");
                    }

                    if(file.isDirectory() && file.getName().equalsIgnoreCase("configs")) {
                        File[] fileConfigs = file.listFiles();
                        if (fileConfigs == null){
                            this.getLogger().info("fileConfigs are null!");
                        }else {
                            for (File fileConfig : fileConfigs){
                                if(fileLoader.exists(fileConfig)){
                                    this.getLogger().info(file.getName() + "讀取成功");
                                }else {
                                    this.getLogger().info("讀取失敗");
                                }

                                if(fileConfig.isFile()){
                                    if(fileConfig.getName().equalsIgnoreCase("config.yml")) {

                                    }
                                }else if(fileConfig.isDirectory()){
                                    if(fileConfig.getName().equalsIgnoreCase("locales")){
                                        File[] fileLocales = fileConfig.listFiles();
                                        if (fileLocales == null){
                                            this.getLogger().info("fileConfigs are null!");
                                        }else {
                                            for(File fileLocale : fileLocales){
                                                if(fileLocale.isFile()){
                                                    String name = fileLocale.getName().replace(".txt", "");
                                                    LocaleConfig localeConfig = localConfigParser.parseLocaleConfig(fileLocale);
                                                    this.localeConfigMap.put(name, localeConfig);
                                                }
                                            }
                                        }

                                    }else if(fileConfig.getName().equalsIgnoreCase("users")){
                                        File[] filesUsers = fileConfig.listFiles();

                                        if (filesUsers == null){
                                            this.getLogger().info("fileUsers are null!");
                                        }else {
                                            for (File fileUser : filesUsers){
                                                if(fileUser.isFile()){
                                                    String name = fileUser.getName().replace(".yml", "");
                                                    UUID uuid = UUID.fromString(name);
                                                    UserConfig userConfig = userConfigParser.parseUserConfig(fileUser);
                                                    this.userConfigMap.put(uuid, userConfig);
                                                    this.getLogger().info(this.userConfigMap.keySet().toString());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
