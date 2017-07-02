package xxp.xxp.music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import xxp.xxp.music.constants.Actions;
import xxp.xxp.music.service.PlayService;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description : 耳机插拔、蓝牙耳机的断开   广播接收
 */

public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
