package com.chaos.fairy.widget.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.chaos.fairy.R;
import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.widget.adapter.BaseGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc 水平 GridView
 */
public class HorizontalGridView<T extends IGrid> extends LinearLayout {
    private List<T> list;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private OnItemClickListener<T> onItemClickListener;
    private OnItemChildViewClickListener<T> onItemChildViewClickListener;
    /**
     * ViewPage 页数
     */
    private int pagerCount = 0;
    /**
     * 每页最多显示菜单数（超出另一页显示）
     */
    private int mMaxShowCount = 10;
    /**
     * 是否显示圆点
     */
    private boolean showDot = true;
    /**
     * 是否循环
     */
    private boolean areLoop = false;
    /**
     * 圆点集
     */
    private final ArrayList<ImageView> dotList = new ArrayList<>();
    /**
     * 指示器数组
     */
    private int[] indicatorInts = new int[]{R.drawable.gray_dot, R.drawable.purple_500_dot};

    public HorizontalGridView(Context context) {
        this(context, null);
    }

    public HorizontalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setListener();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.horizontal_gridview, this);
        viewPager = view.findViewById(R.id.vp_container);
        linearLayout = view.findViewById(R.id.ll_point_container);
    }

    private void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = viewPagerAdapter.toRealPosition(position);
                for (int i = 0; i < dotList.size(); i++) {
                    dotList.get(realPosition).setImageResource(indicatorInts[1]);
                    if (realPosition != i) {
                        dotList.get(i).setImageResource(indicatorInts[0]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置每页最多数量
     */
    public void setMaxCountEveryPage(int maxShowCount) {
        this.mMaxShowCount = maxShowCount;
    }

    /**
     * 显示圆点
     */
    public void showDot(boolean showDot) {
        this.showDot = showDot;
    }

    /**
     * 设置指示器
     */
    public void setIndicator(int[] indicatorInts) {
        this.indicatorInts = indicatorInts;
    }

    /**
     * 设置循环
     *
     * @param areLoop 是否循环
     */
    public void loop(boolean areLoop) {
        this.areLoop = areLoop;
    }

    public void setData(List<T> list) {
        this.list = list;
        if (list != null) {
            initPagerData();
        }
    }

    private void initPagerData() {
        // ViewPager 页数
        pagerCount = (int) Math.ceil(list.size() * 1.0 / mMaxShowCount);
        viewPagerAdapter = new ViewPagerAdapter(viewPager, new MyViewHolder(), areLoop);
        viewPager.setAdapter(viewPagerAdapter);
        setDot();
    }

    /**
     * 设置圆点
     */
    private void setDot() {
        // ViewPager 页数
        int pageCount = (int) Math.ceil(list.size() * 1.0 / mMaxShowCount);
        // 大于 1 页显示圆点
        if (showDot && pageCount > 1) {
            linearLayout.removeAllViews();
            for (int i = 0; i < pageCount; i++) {
                ImageView dotView = new ImageView(getContext());
                dotView.setPadding(6, 0, 6, 0);
                if (i == 0) {
                    dotView.setImageResource(indicatorInts[1]);
                } else {
                    dotView.setImageResource(indicatorInts[0]);
                }
                final int position = i;
                dotView.setOnClickListener(v -> {
                    try {
                        viewPager.setCurrentItem(position, false);
                    } catch (IllegalStateException e) {
                        LogUtils.exception(e);
                    }
                });
                dotList.add(dotView);
                linearLayout.addView(dotView);
            }
        }
    }

    /**
     * 设置条目点击监听
     *
     * @param onItemClickListener 条目点击监听
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置条目子视图点击监听
     *
     * @param onItemChildViewClickListener 条目子视图点击监听
     */
    public void setOnItemChildViewClickListener(OnItemChildViewClickListener<T> onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    private class MyViewHolder implements IViewHolder {
        /**
         * 创建内容视图
         *
         * @param position 位置
         * @return 内容视图
         */
        @Override
        public View createContentView(int position) {
            return initGridView(position);
        }

        /**
         * 获取页面数量
         *
         * @return 页面数量
         */
        @Override
        public int getPagerCount() {
            return pagerCount;
        }
    }

    protected View initGridView(int currentPageIndex) {
        MeasuredGridView measuredGridView = new MeasuredGridView(getContext());
        measuredGridView.setVerticalSpacing(20);
        measuredGridView.setSelector(android.R.color.transparent);
        // 设置 5 列
        measuredGridView.setNumColumns(5);
        try {
            final List<T> list = this.list.subList(currentPageIndex * mMaxShowCount, (currentPageIndex == (pagerCount - 1)) ?
                    this.list.size() : ((currentPageIndex + 1) * mMaxShowCount));
            BaseGridViewAdapter<T> baseGridViewAdapter = new GridViewAdapter<>(getContext());
            baseGridViewAdapter.show(measuredGridView, list);
            // 条目点击监听
            measuredGridView.setOnItemClickListener((parent, view, position, id) -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(list.get(position), position);
                }
            });
            // 条目子视图点击监听
            baseGridViewAdapter.setOnItemChildViewClickListener((position, item, childView) -> onItemChildViewClickListener.onItemChildViewClick(item, position));
        } catch (Exception e) {
            LogUtils.exception(e);
            return null;
        }
        return measuredGridView;
    }
}
