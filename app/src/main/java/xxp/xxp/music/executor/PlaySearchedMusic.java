package xxp.xxp.music.executor;

import android.app.Activity;
import android.text.TextUtils;

import java.io.File;

import xxp.xxp.music.http.HttpCallback;
import xxp.xxp.music.http.HttpClient;
import xxp.xxp.music.model.DownloadInfo;
import xxp.xxp.music.model.Lrc;
import xxp.xxp.music.model.SearchMusic;
import xxp.xxp.music.model.Music;
import xxp.xxp.music.utils.FileUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public abstract class PlaySearchedMusic extends PlayMusic {
    private SearchMusic.Song mSong;

    public PlaySearchedMusic(Activity activity, SearchMusic.Song song) {
        super(activity, 2);
        mSong = song;
    }

    @Override
    protected void getPlayInfo() {
        String lrcFileName = FileUtils.getLrcFileName(mSong.getArtistname(), mSong.getSongname());
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists()) {
            downloadLrc(lrcFile.getPath());
        } else {
            mCounter++;
        }

        music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(mSong.getSongname());
        music.setArtist(mSong.getArtistname());

        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(mSong.getSongid(), new HttpCallback<DownloadInfo>() {
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

    private void downloadLrc(final String filePath) {
        HttpClient.getLrc(mSong.getSongid(), new HttpCallback<Lrc>() {
            @Override
            public void onSuccess(Lrc response) {
                if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                    return;
                }

                FileUtils.saveLrcFile(filePath, response.getLrcContent());
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
