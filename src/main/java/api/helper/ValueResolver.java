package api.helper;

import utilities.RandomDataUtil;

import java.util.HashMap;
import java.util.Map;
public class ValueResolver {
    public static String resolveString(String value, boolean randomize){
        if("EMPTY".equalsIgnoreCase(value)) return "";
        if("MISSING".equalsIgnoreCase(value) || value == null) return null;
        return randomize ? RandomDataUtil.randomString(value) : value;
    }

    public static Double resolveDouble(String value){
        if("EMPTY".equalsIgnoreCase(value)) return 0.0;
        if("MISSING".equalsIgnoreCase(value) || value == null) return null;
        return Double.valueOf(value);
    }

    //Input format: ["Đoàn Giỏi", "Truyện"]
    public static String[] resolveArray(String value){
        if("EMPTY".equalsIgnoreCase(value)) return new String[]{};
        if("MISSING".equalsIgnoreCase(value) || value == null) return null;
        return value
                .replace("\n", "")
                .replace("\"","")
                .replace("[", "")
                .replace("]", "")
                .split(",");
    }

    public static Map<String, String> resolveHeader(String value){
        Map<String, String> result = new HashMap<>();
        // Check null
        if(value == null || value.trim().isEmpty())
            return result;

        String[] lines = value.split("\n");
        for(String line : lines){
            if(line.trim().isEmpty()) continue;

            String[] keyValuePair = line.split(":", 2);

            if(keyValuePair.length < 2) continue;

            result.put(keyValuePair[0].replace("\"", "").trim(), keyValuePair[1].replace("\"", "").trim());
        }
        return result;
    }
}
