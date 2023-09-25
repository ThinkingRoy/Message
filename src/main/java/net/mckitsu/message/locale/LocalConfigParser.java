package net.mckitsu.message.locale;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class LocalConfigParser {
    public LocaleConfig parseLocaleConfig(File file) {

        LocaleConfig localeConfig = new LocaleConfig();

        try {
            String txt = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            List<String> list = new ArrayList<>();
            Map <String, String> result = new HashMap<>();

            int lastIndex = -1, cr, lf, resultIndex;

            while (lastIndex < txt.length()){

                cr = txt.indexOf("\n", lastIndex + 1);
                lf = txt.indexOf("\r", lastIndex + 1);

                if(lf == -1){
                    lf = txt.length();
                }
                if(cr == -1){
                    cr = txt.length();
                }

                resultIndex = Math.min(cr, lf);


                if((resultIndex - lastIndex) > 1)
                    list.add(txt.substring(lastIndex + 1, resultIndex));

                lastIndex = resultIndex;

                if(lastIndex >= txt.length())
                    break;

            }
            for (String s : list){
                String[] parts = s.split("=");

                if(parts.length == 2){
                    String key = parts[0];
                    String value = parts[1];
                    result.put(key, value);
                }
            }

            localeConfig.setLocaleConfig(result);

            return localeConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
