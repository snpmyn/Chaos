package com.chaos.widget.other.adapter.helper;

import android.view.View;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2020-09-17
 *
 * @author zsp
 * @desc 适配器帮助类
 */
public class AdapterHelper {
    /**
     * 设置条目子视图点击监听
     *
     * @param position                     位置
     * @param item                         条目
     * @param childView                    子视图
     * @param onItemChildViewClickListener 条目子视图点击监听
     * @param <T>                          <T>
     */
    public static <T> void setOnItemChildViewClickListener(int position, T item, @NotNull View childView, OnItemChildViewClickListener<T> onItemChildViewClickListener) {
        childView.setOnClickListener(new ItemChildViewClick<>(position, item, onItemChildViewClickListener));
    }

    /**
     * 条目子视图点击监听
     *
     * @param <T> <T>
     */
    public interface OnItemChildViewClickListener<T> {
        /**
         * 条目子视图点击
         *
         * @param position  位置
         * @param item      条目
         * @param childView 子视图
         */
        void onItemChildViewClick(int position, T item, View childView);
    }

    /**
     * 条目子视图点击
     *
     * @param <T> <T>
     */
    public static class ItemChildViewClick<T> implements View.OnClickListener {
        private final int position;
        private final T item;
        private final OnItemChildViewClickListener<T> onItemChildViewClickListener;

        ItemChildViewClick(int position, T item, OnItemChildViewClickListener<T> onItemChildViewClickListener) {
            this.position = position;
            this.item = item;
            this.onItemChildViewClickListener = onItemChildViewClickListener;
        }

        @Override
        public void onClick(View view) {
            if (null == onItemChildViewClickListener) {
                return;
            }
            onItemChildViewClickListener.onItemChildViewClick(position, item, view);
        }
    }
}
