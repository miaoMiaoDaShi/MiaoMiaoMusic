package xxp.xxp.music.executor;

import android.app.Activity;
import android.text.TextUtils;

import java.io.File;

import xxp.xxp.music.http.HttpCallback;
import xxp.xxp.music.http.HttpClient;
import xxp.xxp.music.model.DownloadInfo;
import xxp.xxp.music.model.Music;
import xxp.xxp.music.model.OnlineMusic;
import xxp.xxp.music.utils.FileUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public abstract class PlayOnlineMusic extends PlayMusic {
    private OnlineMusic mOnlineMusic;

    public PlayOnlineMusic(Activity activity, OnlineMusic onlineMusic) {
        super(activity, 3);
        mOnlineMusic = onlineMusic;
    }

    @Override
    protected void getPlayInfo() {
        String artist = mOnlineMusic.getArtist_name();
        String title = mOnlineMusic.getTitle();

        // 下载歌词
        String lrcFileName = FileUtils.getLrcFileName(artist, title);
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists() && !TextUtils.isEmpty(mOnlineMusic.getLrclink())) {
            downloadLrc(mOnlineMusic.getLrclink(), lrcFileName);
        } else {
            mCounter++;
        }

        // 下载封面
        String albumFileName = FileUtils.getAlbumFileName(artist, title);
        File albumFile = new File(FileUtils.getAlbumDir(), albumFileName);
        String picUrl = mOnlineMusic.getPic_big();
        if (TextUtils.isEmpty(picUrl)) {
            picUrl = mOnlineMusic.getPic_small();
        }
        if (!albumFile.exists() && !TextUtils.isEmpty(picUrl)) {
            downloadAlbum(picUrl, albumFileName);
        } else {
            mCounter++;
        }

        music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(title);
        music.setArtist(artist);
        music.setAlbum(mOnlineMusic.getAlbum_title());

        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(mOnlineMusic.getSong_id(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                music.setPath(response.getBitrate().getFile_link());
                music.setDuration(response.getBitrate().getFile_duration() * 1000);
                checkCounter();
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }

    private void downloadLrc(String url, String fileName) {
        HttpClient.downloadFile(url, FileUtils.getLrcDir(), fileName, new HttpCallback<File>() {
            @Override
            public void onSuccess(File file) {
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void onFinish() {
                checkCounter();
            }
        });
    }

    private void downloadAlbum(String picUrl, String fileName) {
        HttpClient.downloadFile(picUrl, FileUtils.getAlbumDir(), fileName, new HttpCallback<File>() {
            @Override
            public void onSuccess(File file) {
                music.setCoverPath(file.getPath());
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void onFinish() {
                checkCounter();
            }
        });
    }
}
