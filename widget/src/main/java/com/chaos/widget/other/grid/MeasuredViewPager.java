package com.chaos.widget.other.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.viewpager.widget.ViewPager;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc 测量版 ViewPager
 * 解决 {@link ViewPager} 与 {@link android.widget.ScrollView} 冲突。
 */
public class MeasuredViewPager extends ViewPager implements ViewTreeObserver.OnGlobalLayoutListener {
    public MeasuredViewPager(Context context) {
        super(context);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public MeasuredViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int measuredHeight = child.getMeasuredHeight();
            if (measuredHeight > size) {
                size = measuredHeight;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onGlobalLayout() {
        View view = (View) getParent();
        if (view.getHeight() < getHeight()) {
            requestLayout();
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }
}
