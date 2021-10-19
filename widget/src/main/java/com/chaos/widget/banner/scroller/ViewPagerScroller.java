package com.chaos.widget.banner.scroller;

import android.content.Context;
import android.widget.Scroller;

/**
 * @desc: ViewPagerScroller
 * @author: zsp
 * @date: 2021/10/16 9:44 下午
 */
public class ViewPagerScroller extends Scroller {
    /**
     * 滑时
     * <p>
     * 值越大滑越慢
     * 滑太快致 3D 效果不明显
     */
    private int scrollDuration = 800;

    public ViewPagerScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
    }
}