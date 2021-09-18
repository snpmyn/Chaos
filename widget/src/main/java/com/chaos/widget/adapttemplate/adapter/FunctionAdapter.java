package com.chaos.widget.adapttemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.util.java.list.ListUtils;
import com.chaos.util.java.screen.ScreenUtils;
import com.chaos.widget.R;
import com.chaos.widget.adapttemplate.bean.FunctionBean;
import com.chaos.widget.recyclerview.listener.OnRecyclerViewOnItemClickListener;
import com.chaos.widget.textview.TextViewKit;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/3/29
 *
 * @author zsp
 * @desc 功能适配器
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private final Context context;
    private final int totalMargin;
    private final int spanCount;
    private List<FunctionBean> functionBeans;
    private OnRecyclerViewOnItemClickListener onRecyclerViewOnItemClickListener;

    /**
     * constructor
     *
     * @param context     上下文
     * @param totalMargin 总外边距
     * @param spanCount   间隔数
     */
    public FunctionAdapter(Context context, int totalMargin, int spanCount) {
        this.context = context;
        this.totalMargin = totalMargin;
        this.spanCount = spanCount;
        this.functionBeans = new ArrayList<>();
    }

    public void setMoreFunctionData(List<FunctionBean> functionBeans) {
        this.functionBeans = functionBeans;
    }

    public void setOnRecyclerViewOnItemClickListener(OnRecyclerViewOnItemClickListener onRecyclerViewOnItemLongClickListener) {
        this.onRecyclerViewOnItemClickListener = onRecyclerViewOnItemLongClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.function_item, viewGroup, false);
        // 宽高等同
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ((ScreenUtils.screenWidth(context) - totalMargin) / spanCount);
        view.setLayoutParams(layoutParams);
        // 点击监听
        view.setOnClickListener(v -> {
            int position = (Integer) view.getTag();
            onRecyclerViewOnItemClickListener.onItemClick(v, position, functionBeans.get(position));
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        FunctionBean functionBean = functionBeans.get(position);
        // 功能
        TextViewKit.setDrawable(context, holder.functionItemTvFunction, functionBean.getFunctionIconResId(), 2, 20);
        holder.functionItemTvFunction.setText(context.getString(functionBean.getFunctionNameResId()));
    }

    @Override
    public int getItemCount() {
        if (ListUtils.listIsNotEmpty(functionBeans)) {
            return functionBeans.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView functionItemTvFunction;

        private ViewHolder(@NonNull View view) {
            super(view);
            functionItemTvFunction = view.findViewById(R.id.functionItemTvFunction);
        }
    }
}