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
import com.chaos.widget.adapttemplate.bean.ModuleBean;
import com.chaos.widget.recyclerview.listener.OnRecyclerViewOnItemClickListener;
import com.chaos.widget.textview.TextViewKit;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/3/29
 *
 * @author zsp
 * @desc 模块适配器
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {
    private final Context context;
    private final int totalMargin;
    private final int spanCount;
    private List<ModuleBean> moduleBeans;
    private OnRecyclerViewOnItemClickListener onRecyclerViewOnItemClickListener;

    /**
     * constructor
     *
     * @param context     上下文
     * @param totalMargin 总外边距
     * @param spanCount   间隔数
     */
    public ModuleAdapter(Context context, int totalMargin, int spanCount) {
        this.context = context;
        this.totalMargin = totalMargin;
        this.spanCount = spanCount;
        this.moduleBeans = new ArrayList<>();
    }

    public void setFunctionData(List<ModuleBean> moduleBeans) {
        this.moduleBeans = moduleBeans;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) view.getTag();
                onRecyclerViewOnItemClickListener.onItemClick(v, position, moduleBeans.get(position));
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ModuleBean moduleBean = moduleBeans.get(position);
        // 模块
        TextViewKit.setDrawable(context, holder.functionItemTvFunction, moduleBean.getFunctionIconResId(), 2, 20);
        holder.functionItemTvFunction.setText(moduleBean.getFunctionName());
    }

    @Override
    public int getItemCount() {
        if (ListUtils.listIsNotEmpty(moduleBeans)) {
            return moduleBeans.size();
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