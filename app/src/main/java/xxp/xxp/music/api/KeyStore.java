package xxp.xxp.music.api;

import java.lang.reflect.Field;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */
public class KeyStore {
    public static final String BUGLY_APP_ID = "BUGLY_APP_ID";

    public static String getKey(String keyName) {
        try {
            String className = KeyStore.class.getPackage().getName() + ".Keys";
            Class apiKey = Class.forName(className);
            Field field = apiKey.getField(keyName);
            field.setAccessible(true);
            return (String) field.get(null);
        } catch (Exception ignored) {
        }
        return "";
    }
}
