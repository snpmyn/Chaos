package com.chaos.widget.other.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.chaos.widget.other.adapter.helper.AdapterHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc GridView 适配器基类
 */
public abstract class BaseGridViewAdapter<T> extends BaseAdapter {
    private List<T> list;
    protected LayoutInflater layoutInflater;
    private AdapterHelper.OnItemChildViewClickListener<T> onItemChildViewClickListener;

    public BaseGridViewAdapter(@NotNull Context context) {
        this.list = new ArrayList<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (null == list) ? 0 : list.size();
    }

    @Override
    public T getItem(int i) {
        return (null == list || list.isEmpty()) ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 显示
     *
     * @param gridView 控件
     * @param list     数据
     */
    public void show(@NotNull GridView gridView, List<T> list) {
        this.list = list;
        if (null == gridView.getAdapter()) {
            gridView.setAdapter(this);
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 条目是否可见
     *
     * @param listView 列表控件
     * @param position 位置
     * @return 条目是否可见
     */
    public boolean itemIsVisible(@NotNull ListView listView, int position) {
        return position >= listView.getFirstVisiblePosition() && position <= listView.getLastVisiblePosition();
    }

    /**
     * 设置条目子视图点击监听
     * <p>
     * Provide for outer use.
     *
     * @param onItemChildViewClickListener 条目子视图点击监听
     */
    public void setOnItemChildViewClickListener(AdapterHelper.OnItemChildViewClickListener<T> onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    /**
     * 设置条目子视图点击监听
     * <p>
     * Be used in {@link BaseListViewAdapter#getView(int, View, ViewGroup)}.
     *
     * @param position  位置
     * @param item      条目
     * @param childView 子视图
     */
    protected void setOnItemChildViewClickListener(int position, T item, View childView) {
        AdapterHelper.setOnItemChildViewClickListener(position, item, childView, onItemChildViewClickListener);
    }
}
