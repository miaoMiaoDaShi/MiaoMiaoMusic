package xxp.xxp.music.executor;

import android.app.Activity;
import android.text.TextUtils;

import java.io.File;

import xxp.xxp.music.http.HttpCallback;
import xxp.xxp.music.http.HttpClient;
import xxp.xxp.music.model.DownloadInfo;
import xxp.xxp.music.model.Lrc;
import xxp.xxp.music.model.SearchMusic;
import xxp.xxp.music.utils.FileUtils;

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2017/6/16
 * Description :
 */

public abstract class DownloadSearchedMusic extends DownloadMusic {
    private SearchMusic.Song mSong;

    public DownloadSearchedMusic(Activity activity, SearchMusic.Song song) {
        super(activity);
        mSong = song;
    }

    @Override
    protected void download() {
        final String artist = mSong.getArtistname();
        final String title = mSong.getSongname();

        // 获取歌曲下载链接
        HttpClient.getMusicDownloadInfo(mSong.getSongid(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                downloadMusic(response.getBitrate().getFile_link(), artist, title);
                onExecuteSuccess(null);
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });

        // 下载歌词
        String lrcFileName = FileUtils.getLrcFileName(artist, title);
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists()) {
            downloadLrc(mSong.getSongid(), lrcFileName);
        }
    }

    private void downloadLrc(String songId, final String fileName) {
        HttpClient.getLrc(songId, new HttpCallback<Lrc>() {
            @Override
            public void onSuccess(Lrc response) {
                if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                    return;
                }

                String filePath = FileUtils.getLrcDir() + fileName;
                FileUtils.saveLrcFile(filePath, response.getLrcContent());
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }
}
