package com.chaos.widget.banner.listener;

import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * @desc: 轮播页变监听
 * @author: zsp
 * @date: 2021/10/15 5:13 下午
 */
public class BannerPageChangeListener implements ViewPager.OnPageChangeListener {
    private final ArrayList<ImageView> imageViews;
    private final int[] ints;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public BannerPageChangeListener(ArrayList<ImageView> imageViews, int[] ints) {
        this.imageViews = imageViews;
        this.ints = ints;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (null != onPageChangeListener) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != onPageChangeListener) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int index) {
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(index).setImageResource(ints[1]);
            if (index != i) {
                imageViews.get(i).setImageResource(ints[0]);
            }
        }
        if (null != onPageChangeListener) {
            onPageChangeListener.onPageSelected(index);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
