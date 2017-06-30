package xxp.xxp.music.executor;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public interface IExecutor<T> {
    void execute();

    void onPrepare();

    void onExecuteSuccess(T t);

    void onExecuteFail(Exception e);
}
