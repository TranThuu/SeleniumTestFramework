package api.config;

import java.util.ResourceBundle;

public class ConfigManager {
    private static ResourceBundle bundle = ResourceBundle.getBundle("apiConfig");

    public static String get(String key){
        return bundle.getString(key);
    }

}
