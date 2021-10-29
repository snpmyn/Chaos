package com.chaos.widget.other.grid;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chaos.util.java.log.LogUtils;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc ViewPager 适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final ViewPager viewPager;
    private final IViewHolder iViewHolder;
    private final boolean canLoop;

    ViewPagerAdapter(ViewPager viewPager, IViewHolder iViewHolder, boolean canLoop) {
        this.viewPager = viewPager;
        this.iViewHolder = iViewHolder;
        this.canLoop = canLoop;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = iViewHolder.createContentView(realPosition);
        container.addView(view);
        return view;
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param object    The same object that was returned by
     *                  {@link #instantiateItem(ViewGroup, int)}.
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     *
     * @param container The containing View which is displaying this adapter's
     *                  page views.
     */
    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = getFirstItem();
        } else if (position == (getCount() - 1)) {
            position = getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
            LogUtils.exception(e);
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return canLoop ? getRealCount() * 300 : getRealCount();
    }

    private int getRealCount() {
        return iViewHolder.getPagerCount();
    }

    private int getFirstItem() {
        return canLoop ? getRealCount() : 0;
    }

    private int getLastItem() {
        return (getRealCount() - 1);
    }

    int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }
}

