package com.chaos.widget.adapttemplate.kit;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.adapttemplate.adapter.MenuAdapter;
import com.chaos.widget.adapttemplate.bean.MenuBean;
import com.chaos.widget.recyclerview.configure.RecyclerViewConfigure;
import com.chaos.widget.recyclerview.controller.RecyclerViewDisplayController;
import com.chaos.widget.recyclerview.listener.OnRecyclerViewOnItemClickListener;

import java.util.List;

/**
 * Created on 2021/12/7
 *
 * @author zsp
 * @desc 菜单适配器配套元件
 */
public class MenuAdapterKit {
    /**
     * 展示
     *
     * @param appCompatActivity       活动
     * @param recyclerView            控件
     * @param menuBeanList            数据集合
     * @param spanCount               跨距数
     * @param spacing                 间距
     * @param totalMargin             总外边距
     * @param menuAdapterKitInterface 菜单适配器配套元件接口
     */
    public void display(AppCompatActivity appCompatActivity, RecyclerView recyclerView, List<MenuBean> menuBeanList, int spanCount, int spacing, int totalMargin, MenuAdapterKitInterface menuAdapterKitInterface) {
        // 控件
        RecyclerViewConfigure recyclerViewConfigure = new RecyclerViewConfigure(appCompatActivity, recyclerView);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerViewConfigure.gridLayout(spanCount, spacing, true, true, false);
        }
        // 适配器
        MenuAdapter menuAdapter = new MenuAdapter(appCompatActivity, spanCount, totalMargin);
        menuAdapter.setMenuData(menuBeanList);
        menuAdapter.setOnRecyclerViewOnItemClickListener(new OnRecyclerViewOnItemClickListener() {
            @Override
            public <T> void onItemClick(View view, int position, T t) {
                MenuBean menuBean = (MenuBean) t;
                if (null != menuAdapterKitInterface) {
                    menuAdapterKitInterface.onItemClick(menuBean);
                }
            }
        });
        // 展示
        RecyclerViewDisplayController.display(recyclerView, menuAdapter);
    }

    /**
     * 菜单适配器配套元件接口
     */
    public interface MenuAdapterKitInterface {
        /**
         * 条目点击
         *
         * @param menuBean 菜单
         */
        void onItemClick(MenuBean menuBean);
    }
}
