package com.chaos.widget.banner.engine;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chaos.util.java.glide.util.GlideUtils;
import com.chaos.widget.banner.holder.Holder;

/**
 * Created on 2021/10/18
 *
 * @author zsp
 * @desc 轮播引擎
 */
public class BannerEngine implements Holder<Object> {
    private ImageView imageView;

    /**
     * 创视图
     *
     * @param context 上下文
     * @return 视图
     */
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    /**
     * 更新 UI
     *
     * @param context  上下文
     * @param position 位置
     * @param data     数据
     */
    @Override
    public void updateUi(Context context, int position, Object data) {
        GlideUtils.loadByObject(context, data, imageView);
    }
}
