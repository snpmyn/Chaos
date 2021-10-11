package com.chaos.widget.other.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.other.adapter.helper.AdapterHelper;
import com.chaos.widget.other.adapter.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-09-21
 *
 * @author zsp
 * @desc RecyclerView 适配器基类
 */
public abstract class BaseRecyclerViewAdapter<T, A extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<A> {
    protected LayoutInflater layoutInflater;
    private List<T> list;
    private OnItemClickListener onItemClickListener;
    private AdapterHelper.OnItemChildViewClickListener<T> onItemChildViewClickListener;

    public BaseRecyclerViewAdapter(@NotNull Context context) {
        this.list = new ArrayList<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected T getItem(int position) {
        return ((null == list) || list.isEmpty()) ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 显示
     *
     * @param recyclerView 控件
     * @param list         数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void show(RecyclerView recyclerView, List<T> list) {
        this.list = list;
        if (this.hasObservers()) {
            notifyDataSetChanged();
        } else {
            recyclerView.setAdapter(this);
        }
    }

    /**
     * 设置条目点击监听
     *
     * @param onItemClickListener 条目点击监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            if (null != onItemClickListener) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(itemView, getLayoutPosition());
            }
        }
    }
}
