package com.chaos.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.chaos.utilone.glide.util.GlideUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * Created on 2019/8/2.
 *
 * @author 郑少鹏
 * @desc GlideImageLoader
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtils.loadByObject(context, path, imageView);
    }
}