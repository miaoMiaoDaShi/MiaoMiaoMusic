package xxp.xxp.music.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import xxp.xxp.music.application.AppCache;
import xxp.xxp.music.service.PlayService;
import xxp.xxp.music.utils.ToastUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description : 下载完成广播接收者
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        String title = AppCache.getDownloadList().get(id);
        if (!TextUtils.isEmpty(title)) {
            ToastUtils.show(context.getString(xxp.xxp.music.R.string.download_success, title));

            // 由于系统扫描音乐是异步执行，因此延迟刷新音乐列表
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanMusic();
                }
            }, 1000);
        }
    }

    private void scanMusic() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                PlayService playService = AppCache.getPlayService();
                if (playService != null) {
                    playService.updateMusicList();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                PlayService playService = AppCache.getPlayService();
                if (playService != null && playService.getOnPlayEventListener() != null) {
                    playService.getOnPlayEventListener().onMusicListUpdate();
                }
            }
        }.execute();
    }
}
