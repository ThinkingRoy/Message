package net.mckitsu.message.locale;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LocaleConfig {


    private Map<String, String> localeConfig;

    public LocaleConfig(){
        this.localeConfig = new HashMap<>();
    }

    public void setLocaleConfig(Map<String, String> localeConfig) {
        this.localeConfig = localeConfig;
    }



    public String getPrefixConfig(Character prefix) {
        String s = String.valueOf(prefix);
        return this.localeConfig.get(s);
    }

    public String getPublicMessageFormat() {
        return this.localeConfig.get("messagePublicFormat");
    }

    public String getMessageNonPublicFormat() {return this.localeConfig.get("messageNonPublicFormat");}

    public String getSubChannelIsNull() {return this.localeConfig.get("subChannelIsNull");}

    public String getNotJoinChannelYet() {return this.localeConfig.get("notJoinChannelYet");}

    public String getContactWithAdmin() {return  this.localeConfig.get("contactWithAdmin");}

    public String getMessagePrivateSenderFormat() {return this.localeConfig.get("messagePrivateSenderFormat");}

    public String getMessagePrivateRecipientFormat() {return this.localeConfig.get("messagePrivateRecipientFormat");}


}
