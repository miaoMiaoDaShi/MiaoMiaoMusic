package xxp.xxp.music.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public class ToastUtils {
    private static Context sContext;
    private static Toast sToast;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void show(int resId) {
        show(sContext.getString(resId));
    }

    public static void show(String text) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
