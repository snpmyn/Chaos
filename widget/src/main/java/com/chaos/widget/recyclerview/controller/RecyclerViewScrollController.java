package com.chaos.widget.recyclerview.controller;

import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2018/6/28.
 *
 * @author 郑少鹏
 * @desc RecyclerViewScrollController
 */
public class RecyclerViewScrollController {
    private boolean move;
    private int index;

    /**
     * 平滑至目标位
     *
     * @param recyclerView 控件
     * @param position     位
     */
    public void smoothScrollToTargetPosition(@NonNull RecyclerView recyclerView, int position) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (null != manager) {
            // 滑至指定位
            // 记录（第三种情况用到）
            this.index = position;
            // 当前屏幕可见头项与末项
            int firstItem = manager.findFirstVisibleItemPosition();
            int lastItem = manager.findLastVisibleItemPosition();
            // 区分
            if (position <= firstItem) {
                // 所置顶项于当前显示头项前
                recyclerView.smoothScrollToPosition(position);
            } else if (position <= lastItem) {
                // 所置顶项已于屏幕上
                int top = recyclerView.getChildAt(position - firstItem).getTop();
                recyclerView.smoothScrollBy(0, top);
            } else {
                // 所置顶项于当前显示末项后
                recyclerView.smoothScrollToPosition(position);
                move = true;
            }
            // 滑动监听
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    // 此处第二次滚动
                    if (move) {
                        move = false;
                        // 所置顶项于当前屏幕位，mIndex 记录所置顶于 RecyclerView 中位
                        int n = (index - manager.findFirstVisibleItemPosition());
                        if ((0 <= n) && (n < recyclerView.getChildCount())) {
                            // 所置顶项顶距 RecyclerView 顶距离
                            int top = recyclerView.getChildAt(n).getTop();
                            // 最后移动
                            recyclerView.smoothScrollBy(0, top);
                        }
                    }
                }
            });
        }
    }

    /**
     * 通滑动模式平滑至目标位
     *
     * @param recyclerView   控件
     * @param targetPosition 目标位
     * @param scrollMode     滑动模式（默 SNAP_TO_ANY）
     *                       {@link LinearSmoothScroller#SNAP_TO_START}
     *                       子视图左侧或顶部对齐父视图左侧或顶部。
     *                       {@link LinearSmoothScroller#SNAP_TO_END}
     *                       子视图右侧或底部对齐父视图右侧面或底部。
     *                       {@link LinearSmoothScroller#SNAP_TO_ANY}
     *                       据子视图当前位与父布局关系定子视图从头到尾跟随否。
     *                       子视图实际于 RecyclerView 左侧，SNAP_TO_ANY 和 SNAP_TO_START 无差别。
     */
    public void smoothScrollToTargetPositionWithScrollMode(@NonNull RecyclerView recyclerView, int targetPosition, int scrollMode) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (null == linearLayoutManager) {
            return;
        }
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return scrollMode;
            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return linearLayoutManager.computeScrollVectorForPosition(targetPosition);
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        // 滑
        linearSmoothScroller.setTargetPosition(targetPosition);
        linearLayoutManager.startSmoothScroll(linearSmoothScroller);
    }

    /**
     * 条目滑至居中
     *
     * @param recyclerView 控件
     * @param position     位
     */
    public void itemScrollToCenter(@NonNull RecyclerView recyclerView, int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (null != linearLayoutManager) {
            int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
            int left = recyclerView.getChildAt(position - firstPosition).getLeft();
            int right = recyclerView.getChildAt(lastPosition - position).getLeft();
            recyclerView.scrollBy((left - right) / 2, 0);
        }
    }
}
