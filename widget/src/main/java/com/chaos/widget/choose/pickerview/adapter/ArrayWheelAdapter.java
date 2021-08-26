package com.chaos.widget.choose.pickerview.adapter;

import com.chaos.widget.choose.wheelview.adapter.WheelAdapter;

import java.util.List;

/**
 * @decs: ArrayWheelAdapter
 * @author: 郑少鹏
 * @date: 2019/6/5 14:12
 */
public class ArrayWheelAdapter<T> implements WheelAdapter<Object> {
    /**
     * 条目
     */
    private final List<T> items;

    /**
     * Constructor
     *
     * @param items 条目集
     */
    public ArrayWheelAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public Object getItem(int index) {
        if ((index >= 0) && (index < items.size())) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }
}
