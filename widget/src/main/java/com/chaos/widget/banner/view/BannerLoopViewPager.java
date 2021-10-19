package com.chaos.widget.banner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chaos.widget.banner.adapter.BannerPageAdapter;
import com.chaos.widget.banner.listener.OnItemClickListener;

import value.WidgetMagic;

/**
 * @desc: BannerLoopViewPager
 * @author: zsp
 * @date: 2021/10/16 11:02 下午
 */
public class BannerLoopViewPager extends ViewPager {
    private static final float SENS = 5;
    private OnPageChangeListener mOnPageChangeListener;
    private OnItemClickListener onItemClickListener;
    private BannerPageAdapter bannerPageAdapter;
    private final OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float previousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = bannerPageAdapter.toRealPosition(position);
            if (previousPosition != realPosition) {
                previousPosition = realPosition;
                if (null != mOnPageChangeListener) {
                    mOnPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (null != mOnPageChangeListener) {
                if (position != (bannerPageAdapter.getRealCount() - 1)) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > WidgetMagic.FLOAT_ZERO_DOT_FIVE) {
                        mOnPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOnPageChangeListener.onPageScrolled(position, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (null != mOnPageChangeListener) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };
    private boolean canScroll = true;
    private boolean canLoop = true;
    private float oldX = 0;

    public BannerLoopViewPager(Context context) {
        super(context);
        init();
    }

    public BannerLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setAdapter(PagerAdapter pagerAdapter, boolean canLoop) {
        bannerPageAdapter = (BannerPageAdapter) pagerAdapter;
        bannerPageAdapter.setCanLoop(canLoop);
        bannerPageAdapter.setBannerLoopViewPager(this);
        super.setAdapter(bannerPageAdapter);
        setCurrentItem(getFirstItem(), false);
    }

    public int getFirstItem() {
        return (canLoop ? bannerPageAdapter.getRealCount() : 0);
    }

    public int getLastItem() {
        return (bannerPageAdapter.getRealCount() - 1);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScroll) {
            if (null != onItemClickListener) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float newX = ev.getX();
                        if (Math.abs(oldX - newX) < SENS) {
                            onItemClickListener.onItemClick((getRealItem()));
                        }
                        oldX = 0;
                        break;
                    default:
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public BannerPageAdapter getAdapter() {
        return bannerPageAdapter;
    }

    public int getRealItem() {
        return ((null != bannerPageAdapter) ? bannerPageAdapter.toRealPosition(super.getCurrentItem()) : 0);
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    private void init() {
        super.addOnPageChangeListener(onPageChangeListener);
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (!canLoop) {
            setCurrentItem(getRealItem(), false);
        }
        if (null == bannerPageAdapter) {
            return;
        }
        bannerPageAdapter.setCanLoop(canLoop);
        bannerPageAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
