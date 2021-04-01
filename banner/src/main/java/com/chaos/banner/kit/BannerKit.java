package com.chaos.banner.kit;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.chaos.banner.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created on 2019/8/2.
 *
 * @author 郑少鹏
 * @desc BannerKit
 */
public class BannerKit {
    /**
     * 轮播
     *
     * @param banner    控件
     * @param integers  数据
     * @param cls       变压器
     * @param delayTime 延时
     */
    public void banner(@NonNull Banner banner, List<Integer> integers, Class<? extends ViewPager.PageTransformer> cls, int delayTime) {
        // 图加载器
        banner.setImageLoader(new GlideImageLoader());
        // 图集
        banner.setImages(integers);
        // 动效
        banner.setBannerAnimation(cls);
        // 时间
        banner.setDelayTime(delayTime);
        // 开始（最后调）
        banner.start();
    }
}
