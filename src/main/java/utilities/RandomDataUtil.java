package utilities;

import java.util.UUID;

public class RandomDataUtil {
    public static String randomString(String prefix){
        return prefix + " " + UUID.randomUUID().toString().substring(0,8);
    }

    public static double randomPrice(){
        return Math.floor(Math.random()*10000000) +1;
    }
}
