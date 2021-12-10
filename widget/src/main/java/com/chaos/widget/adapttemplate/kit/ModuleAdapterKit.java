package com.chaos.widget.adapttemplate.kit;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.adapttemplate.adapter.ModuleAdapter;
import com.chaos.widget.adapttemplate.bean.ModuleBean;
import com.chaos.widget.recyclerview.configure.RecyclerViewConfigure;
import com.chaos.widget.recyclerview.controller.RecyclerViewDisplayController;
import com.chaos.widget.recyclerview.listener.OnRecyclerViewOnItemClickListener;

import java.util.List;

/**
 * Created on 2021/12/7
 *
 * @author zsp
 * @desc 模块适配器配套元件
 */
public class ModuleAdapterKit {
    /**
     * 展示
     *
     * @param appCompatActivity         活动
     * @param recyclerView              控件
     * @param moduleBeanList            数据集合
     * @param spanCount                 跨距数
     * @param spacing                   间距
     * @param totalMargin               总外边距
     * @param moduleAdapterKitInterface 模块适配器配套元件接口
     */
    public void display(AppCompatActivity appCompatActivity, RecyclerView recyclerView, List<ModuleBean> moduleBeanList, int spanCount, int spacing, int totalMargin, ModuleAdapterKitInterface moduleAdapterKitInterface) {
        // 控件
        RecyclerViewConfigure recyclerViewConfigure = new RecyclerViewConfigure(appCompatActivity, recyclerView);
        recyclerViewConfigure.gridLayout(spanCount, spacing, true, true, false);
        // 适配器
        ModuleAdapter moduleAdapter = new ModuleAdapter(appCompatActivity, totalMargin, spanCount);
        moduleAdapter.setModuleData(moduleBeanList);
        moduleAdapter.setOnRecyclerViewOnItemClickListener(new OnRecyclerViewOnItemClickListener() {
            @Override
            public <T> void onItemClick(View view, int position, T t) {
                ModuleBean moduleBean = (ModuleBean) t;
                if (null != moduleAdapterKitInterface) {
                    moduleAdapterKitInterface.onItemClick(moduleBean);
                }
            }
        });
        // 展示
        RecyclerViewDisplayController.display(recyclerView, moduleAdapter);
    }

    /**
     * 模块适配器配套元件接口
     */
    public interface ModuleAdapterKitInterface {
        /**
         * 条目点击
         *
         * @param moduleBean 模块
         */
        void onItemClick(ModuleBean moduleBean);
    }
}
