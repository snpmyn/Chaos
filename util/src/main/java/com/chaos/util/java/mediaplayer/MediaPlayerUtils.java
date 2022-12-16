package com.chaos.util.java.mediaplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import com.chaos.util.java.log.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created on 2019/2/28.
 *
 * @author 郑少鹏
 * @desc MediaPlayerUtils
 */
public class MediaPlayerUtils {
    /**
     * 播放 Raw 资源
     *
     * @param context 上下文
     * @param resId   资源 ID
     */
    public static void playRawResource(@NonNull Context context, int resId) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = context.getResources().openRawResourceFd(resId);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    /**
     * 播放路径资源
     *
     * @param path 路径
     */
    public static void playStringResource(String path) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            LogUtils.exception(e);
        }
    }

    /**
     * 播放 Assets 资源
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    private void playAssetsResource(@NotNull Context context, String fileName) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Timber.e(e);
        }
    }
}
