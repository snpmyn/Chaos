package com.chaos.util.java.drawable;

import android.app.Application;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import timber.log.Timber;

/**
 * Created on 2019/6/21.
 *
 * @author 郑少鹏
 * @desc DrawableUtils
 */
public class DrawableUtils {
    /**
     * 据 URL 获 Drawable
     * <p>
     * Required thread execution.
     * Main thread cannot get graph.
     *
     * @param imageUrl  图片统一资源定位符
     * @param imageName 图片名
     * @return Drawable
     */
    public static Drawable getDrawableByUrl(String imageUrl, String imageName) {
        Drawable drawable = null;
        try {
            URL url = new URL(imageUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream;
            inputStream = urlConnection.getInputStream();
            drawable = Drawable.createFromStream(inputStream, imageName + ".jpg");
        } catch (IOException e) {
            Timber.e(e);
        }
        return drawable;
    }

    /**
     * 获取位图资源 ID
     *
     * @param application  应用
     * @param name         名称
     * @param defaultResId 默认资源 ID
     * @return 位图资源 ID
     */
    public static int getDrawableResIdByName(Application application, String name, int defaultResId) {
        try {
            int drawableResId = application.getResources().getIdentifier(name, "drawable", application.getPackageName());
            return ((drawableResId > 0) ? drawableResId : defaultResId);
        } catch (Exception e) {
            Timber.e(e);
            return defaultResId;
        }
    }
}
