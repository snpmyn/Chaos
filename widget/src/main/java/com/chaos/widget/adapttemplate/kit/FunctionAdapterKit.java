package com.chaos.widget.adapttemplate.kit;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.adapttemplate.adapter.FunctionAdapter;
import com.chaos.widget.adapttemplate.bean.FunctionBean;
import com.chaos.widget.recyclerview.configure.RecyclerViewConfigure;
import com.chaos.widget.recyclerview.controller.RecyclerViewDisplayController;
import com.chaos.widget.recyclerview.listener.OnRecyclerViewOnItemClickListener;

import java.util.List;

/**
 * Created on 2021/12/7
 *
 * @author zsp
 * @desc 功能适配器配套元件
 */
public class FunctionAdapterKit {
    /**
     * 展示
     *
     * @param appCompatActivity           活动
     * @param recyclerView                控件
     * @param functionBeanList            数据集合
     * @param spanCount                   跨距数
     * @param spacing                     间距
     * @param totalMargin                 总外边距
     * @param functionAdapterKitInterface 功能适配器配套元件接口
     */
    public void display(AppCompatActivity appCompatActivity, RecyclerView recyclerView, List<FunctionBean> functionBeanList, int spanCount, int spacing, int totalMargin, FunctionAdapterKitInterface functionAdapterKitInterface) {
        // 控件
        RecyclerViewConfigure recyclerViewConfigure = new RecyclerViewConfigure(appCompatActivity, recyclerView);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerViewConfigure.gridLayout(spanCount, spacing, true, true, false);
        }
        // 适配器
        FunctionAdapter functionAdapter = new FunctionAdapter(appCompatActivity, totalMargin, spanCount);
        functionAdapter.setFunctionData(functionBeanList);
        functionAdapter.setOnRecyclerViewOnItemClickListener(new OnRecyclerViewOnItemClickListener() {
            @Override
            public <T> void onItemClick(View view, int position, T t) {
                FunctionBean functionBean = (FunctionBean) t;
                if (null != functionAdapterKitInterface) {
                    functionAdapterKitInterface.onItemClick(functionBean);
                }
            }
        });
        // 展示
        RecyclerViewDisplayController.display(recyclerView, functionAdapter);
    }

    /**
     * 功能适配器配套元件接口
     */
    public interface FunctionAdapterKitInterface {
        /**
         * 条目点击
         *
         * @param functionBean 功能
         */
        void onItemClick(FunctionBean functionBean);
    }
}
