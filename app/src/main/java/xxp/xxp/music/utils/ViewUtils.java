package xxp.xxp.music.utils;

import android.view.View;

import xxp.xxp.music.enums.LoadStateEnum;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public class ViewUtils {
    public static void changeViewState(View loadSuccess, View loading, View loadFail, LoadStateEnum state) {
        switch (state) {
            case LOADING:
                loadSuccess.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loadFail.setVisibility(View.GONE);
                break;
            case LOAD_SUCCESS:
                loadSuccess.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                loadFail.setVisibility(View.GONE);
                break;
            case LOAD_FAIL:
                loadSuccess.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                loadFail.setVisibility(View.VISIBLE);
                break;
        }
    }
}
