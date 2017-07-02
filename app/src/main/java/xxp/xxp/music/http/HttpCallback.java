package xxp.xxp.music.http;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description : 网络回调
 */

public abstract class HttpCallback<T> {
    /**
     * 数据加载成功
     * @param t 数据
     */
    public abstract void onSuccess(T t);

    /**
     * 数据加载失败
     * @param e 异常
     */
    public abstract void onFail(Exception e);

    /**
     * 加载完成
     */
    public void onFinish() {
    }
}
