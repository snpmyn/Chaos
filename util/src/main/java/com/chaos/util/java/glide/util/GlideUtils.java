package com.chaos.util.java.glide.util;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created on 2017/11/17.
 *
 * @author 郑少鹏
 * @desc GlideUtils
 * context 类型影 Glide 加载优化，Glide 监视 activity 生命周期并 activity 销毁时自取等待请求。Application context 优化失效。
 * 不同 Glide v3，Glide v4 不默交叉淡入或其它过渡效果。每请求须手动应用过渡。
 * override(width, height) 重写宽高后或致获图模糊。
 */
public class GlideUtils {
    /**
     * 加载
     *
     * @param context   上下文
     * @param path      路径
     * @param imageView 控件
     */
    public static void loadByObject(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    /**
     * 加载
     *
     * @param context   上下文
     * @param path      路径
     * @param radius    半径
     * @param imageView 控件
     */
    public static void loadByObjectRoundedCorners(Context context, Object path, int radius, ImageView imageView) {
        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new RoundedCorners(radius))).into(imageView);
    }

    /**
     * 加载
     *
     * @param context        上下文
     * @param path           路径
     * @param transformation 转型
     * @param imageView      控件
     */
    public static void loadByObjectTransformation(Context context, Object path, Transformation<Bitmap> transformation, ImageView imageView) {
        Glide.with(context).load(path).transform(transformation).into(imageView);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param rId              int 资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColor(FragmentActivity fragmentActivity, int rId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(rId)
                .apply(options)
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String 资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColor(FragmentActivity fragmentActivity, String strId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param rId              int 资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColorCircleCrop(FragmentActivity fragmentActivity, int rId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(rId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String 资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColorCircleCrop(FragmentActivity fragmentActivity, String strId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param head             int 资源符
     * @param cId              颜色占位符
     * @param radius           半径
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColorRoundedCorners(FragmentActivity fragmentActivity, int head, int cId, int radius, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(head)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String 资源符
     * @param cId              颜色占位符
     * @param radius           半径
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColorRoundedCorners(FragmentActivity fragmentActivity, String strId, int cId, int radius, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                // sizeMultiplier
                .thumbnail(0.25F)
                .transition(withCrossFade())
                .into(iv);
    }
}