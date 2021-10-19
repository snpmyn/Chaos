package com.chaos.widget.banner.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.chaos.widget.R;
import com.chaos.widget.banner.holder.BannerHolderCreator;
import com.chaos.widget.banner.holder.Holder;
import com.chaos.widget.banner.view.BannerLoopViewPager;

import java.util.List;

import timber.log.Timber;

/**
 * @desc: 轮播页适配器
 * @author: zsp
 * @date: 2021/10/15 5:09 下午
 */
public class BannerPageAdapter<T> extends PagerAdapter {
    private final SparseArrayCompat<View> sparseArrayCompat = new SparseArrayCompat<>();
    /**
     * 数据
     */
    protected List<T> list;
    protected BannerHolderCreator<Holder<T>> bannerHolderCreator;
    /**
     * 可循环
     */
    private boolean canLoop = true;
    private BannerLoopViewPager bannerLoopViewPager;

    public BannerPageAdapter(BannerHolderCreator<Holder<T>> bannerHolderCreator, List<T> list) {
        this.bannerHolderCreator = bannerHolderCreator;
        this.list = list;
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }

    @Override
    public int getCount() {
        return (canLoop ? getRealCount() * 300 : getRealCount());
    }

    public int getRealCount() {
        return ((null == list) ? 0 : list.size());
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = getView(realPosition, null, container);
        container.addView(view);
        sparseArrayCompat.put(realPosition, view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
        sparseArrayCompat.remove(toRealPosition(position));
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        int position = bannerLoopViewPager.getCurrentItem();
        if (position == 0) {
            position = bannerLoopViewPager.getFirstItem();
        } else if (position == (getCount() - 1)) {
            position = bannerLoopViewPager.getLastItem();
        }
        try {
            bannerLoopViewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
            Timber.e(e);
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setBannerLoopViewPager(BannerLoopViewPager bannerLoopViewPager) {
        this.bannerLoopViewPager = bannerLoopViewPager;
    }

    public View getView(int position, View view, ViewGroup container) {
        Holder<T> holder;
        if (null == view) {
            holder = bannerHolderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.WidgetBannerItemTag, holder);
        } else {
            holder = (Holder) view.getTag(R.id.WidgetBannerItemTag);
        }
        if ((null != list) && !list.isEmpty()) {
            holder.updateUi(container.getContext(), position, list.get(position));
        }
        return view;
    }
}
