package com.chaos.widget.banner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.chaos.widget.R;
import com.chaos.widget.banner.adapter.BannerPageAdapter;
import com.chaos.widget.banner.holder.BannerHolderCreator;
import com.chaos.widget.banner.listener.BannerPageChangeListener;
import com.chaos.widget.banner.listener.OnItemClickListener;
import com.chaos.widget.banner.scroller.ViewPagerScroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @desc: ConvenientBanner
 * 无限循环、自动翻页、翻页特效。
 * @author: zsp
 * @date: 2021/10/16 9:43 下午
 */
public class ConvenientBanner<T> extends LinearLayout {
    private final ArrayList<ImageView> imageViews = new ArrayList<>();
    private List<T> list;
    private int[] ints;
    private BannerPageChangeListener bannerPageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private BannerLoopViewPager bannerViewPagerBlvp;
    private ViewPagerScroller viewPagerScroller;
    private ViewGroup bannerViewPagerLl;
    private long autoTurnTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean canLoop = true;
    private BannerSwitchTask bannerSwitchTask;

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_convenientBannerCanLoop, true);
        a.recycle();
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_convenientBannerCanLoop, true);
        a.recycle();
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_convenientBannerCanLoop, true);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_view_pager, this, true);
        bannerViewPagerBlvp = view.findViewById(R.id.bannerViewPagerBlvp);
        bannerViewPagerLl = view.findViewById(R.id.bannerViewPagerLl);
        stepViewPagerScroll();
        bannerSwitchTask = new BannerSwitchTask(this);
    }

    /**
     * 设页
     *
     * @param bannerHolderCreator 轮播持有者创建器
     * @param list                数据
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setPages(BannerHolderCreator bannerHolderCreator, List<T> list) {
        // 数据
        this.list = list;
        // 设适配器
        BannerPageAdapter bannerPageAdapter = new BannerPageAdapter(bannerHolderCreator, this.list);
        bannerViewPagerBlvp.setAdapter(bannerPageAdapter, canLoop);
        if (null != ints) {
            setPageIndicator(ints);
        }
        return this;
    }

    /**
     * 通知数据变化
     * <p>
     * 仅增数据建用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        if (null != bannerViewPagerBlvp.getAdapter()) {
            bannerViewPagerBlvp.getAdapter().notifyDataSetChanged();
        }
        if (null != ints) {
            setPageIndicator(ints);
        }
    }

    /**
     * 设指示器可见
     *
     * @param indicatorVisible 指示器可见
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setPointViewVisible(boolean indicatorVisible) {
        bannerViewPagerLl.setVisibility(indicatorVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设页指示器
     *
     * @param pageIndicatorIds 页指示器数据
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setPageIndicator(int[] pageIndicatorIds) {
        bannerViewPagerLl.removeAllViews();
        imageViews.clear();
        this.ints = pageIndicatorIds;
        if (null == list) {
            return this;
        }
        for (int count = 0; count < list.size(); count++) {
            // 指示器
            ImageView imageView = new ImageView(getContext());
            imageView.setPadding(5, 0, 5, 0);
            if (imageViews.isEmpty()) {
                imageView.setImageResource(pageIndicatorIds[1]);
            } else {
                imageView.setImageResource(pageIndicatorIds[0]);
            }
            imageViews.add(imageView);
            bannerViewPagerLl.addView(imageView);
        }
        bannerPageChangeListener = new BannerPageChangeListener(imageViews, pageIndicatorIds);
        bannerViewPagerBlvp.addOnPageChangeListener(bannerPageChangeListener);
        bannerPageChangeListener.onPageSelected(getCurrentItem());
        if (null != onPageChangeListener) {
            bannerPageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    /**
     * 设页指示器方向
     *
     * @param pageIndicatorAlign 页指示器方向
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setPageIndicatorAlign(PageIndicatorAlign pageIndicatorAlign) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bannerViewPagerLl.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, pageIndicatorAlign == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, pageIndicatorAlign == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, pageIndicatorAlign == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        bannerViewPagerLl.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 开翻
     *
     * @param autoTurnTime 自翻时
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> startTurning(long autoTurnTime) {
        // 正翻停止
        if (turning) {
            stopTurning();
        }
        // 可翻并开翻
        this.autoTurnTime = autoTurnTime;
        canTurn = true;
        turning = true;
        postDelayed(bannerSwitchTask, autoTurnTime);
        return this;
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(bannerSwitchTask);
    }

    /**
     * 自翻暂停
     */
    public void autoTurningPause() {
        this.stopTurning();
        canTurn = false;
    }

    /**
     * 自翻恢复
     */
    public void autoTurningResume() {
        canTurn = true;
        turning = true;
        postDelayed(bannerSwitchTask, autoTurnTime);
    }

    /**
     * 设 PageTransformer
     *
     * @param pageTransformer ViewPager.PageTransformer
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        bannerViewPagerBlvp.setPageTransformer(true, pageTransformer);
        return this;
    }

    /**
     * 初始 ViewPager 滑速
     */
    private void stepViewPagerScroll() {
        try {
            Field field;
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            viewPagerScroller = new ViewPagerScroller(bannerViewPagerBlvp.getContext());
            field.set(bannerViewPagerBlvp, viewPagerScroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Timber.e(e);
        }
    }

    /**
     * 设手动分页
     *
     * @param manualPageable 手动分页
     */
    public void setManualPageable(boolean manualPageable) {
        bannerViewPagerBlvp.setCanScroll(manualPageable);
    }

    /**
     * 分发触摸事件
     * <p>
     * 触碰控件时应停翻，离开时如前已开翻则重新开翻。
     *
     * @param ev MotionEvent
     * @return boolean
     */
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        int action = ev.getAction();
        if ((action == MotionEvent.ACTION_UP) || (action == MotionEvent.ACTION_CANCEL) || (action == MotionEvent.ACTION_OUTSIDE)) {
            // 开翻
            if (canTurn) {
                startTurning(autoTurnTime);
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停翻
            if (canTurn) {
                stopTurning();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获当前页下标
     *
     * @return 当前页下标
     */
    public int getCurrentItem() {
        if (null != bannerViewPagerBlvp) {
            return bannerViewPagerBlvp.getRealItem();
        }
        return -1;
    }

    /**
     * 设当前页下标
     *
     * @param bannerViewPagerBlvp BannerLoopViewPager
     * @param index               当前页下标
     */
    public void setCurrentTime(BannerLoopViewPager bannerViewPagerBlvp, int index) {
        if (null != bannerViewPagerBlvp) {
            bannerViewPagerBlvp.setCurrentItem(index);
        }
    }

    /**
     * Set Callback interface for responding to changing state of the selected page.
     *
     * @param onPageChangeListener Callback interface for responding to changing state of the selected page.
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        // 有默认监听器（即使用了默认翻页指示器）则把用户所设依附到默认上，否直设。
        if (null != bannerPageChangeListener) {
            bannerPageChangeListener.setOnPageChangeListener(onPageChangeListener);
        } else {
            bannerViewPagerBlvp.addOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    /**
     * 设条目点击监听
     *
     * @param onItemClickListener 条目点击监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (null == onItemClickListener) {
            bannerViewPagerBlvp.setOnItemClickListener(null);
            return;
        }
        bannerViewPagerBlvp.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 设滑时
     *
     * @param scrollDuration 滑时
     * @return ConvenientBanner<T>
     */
    public ConvenientBanner<T> setScrollDuration(int scrollDuration) {
        viewPagerScroller.setScrollDuration(scrollDuration);
        return this;
    }

    public ConvenientBanner<T> setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        bannerViewPagerBlvp.setCanLoop(canLoop);
        return this;
    }

    /**
     * 页指示器方向
     */
    public enum PageIndicatorAlign {
        /**
         * 父布局左对齐
         */
        ALIGN_PARENT_LEFT,
        /**
         * 父布局右对齐
         */
        ALIGN_PARENT_RIGHT,
        /**
         * 水平居中
         */
        CENTER_HORIZONTAL
    }

    static class BannerSwitchTask implements Runnable {
        private final WeakReference<ConvenientBanner> weakReference;

        BannerSwitchTask(ConvenientBanner convenientBanner) {
            this.weakReference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void run() {
            ConvenientBanner convenientBanner = weakReference.get();
            if (null != convenientBanner) {
                if ((null != convenientBanner.bannerViewPagerBlvp) && convenientBanner.turning) {
                    int page = (convenientBanner.bannerViewPagerBlvp.getCurrentItem() + 1);
                    convenientBanner.setCurrentTime(convenientBanner.bannerViewPagerBlvp, page);
                    convenientBanner.removeCallbacks(convenientBanner.bannerSwitchTask);
                    convenientBanner.postDelayed(convenientBanner.bannerSwitchTask, convenientBanner.autoTurnTime);
                }
            }
        }
    }
}
