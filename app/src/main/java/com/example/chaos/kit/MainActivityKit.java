package com.example.chaos.kit;

import android.content.pm.PackageManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.util.java.intent.IntentJump;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.view.ViewUtils;
import com.chaos.widget.other.adapter.decoration.HorizontalDividerDecoration;
import com.chaos.widget.other.adapter.decoration.VerticalDividerDecoration;
import com.chaos.widget.other.listview.MeasuredListView;
import com.example.chaos.R;
import com.example.chaos.adapter.MainActivityListViewAdapter;
import com.example.chaos.adapter.MainActivityRecyclerViewAdapter;
import com.example.chaos.bean.MainActivityModule;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import widget.WidgetActivity;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc 主页配套元件
 */
public class MainActivityKit {
    /**
     * 请求权限
     *
     * @param appCompatActivity 活动
     */
    public void requestPermissions(AppCompatActivity appCompatActivity) {
        if (ContextCompat.checkSelfPermission(appCompatActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0x01);
        }
    }

    /**
     * 展示
     *
     * @param appCompatActivity 活动
     * @param measuredListView  MeasuredListView
     * @param recyclerView      RecyclerView
     * @param materialButton    按钮
     */
    public void display(AppCompatActivity appCompatActivity, @NotNull MeasuredListView measuredListView, @NotNull RecyclerView recyclerView, MaterialButton materialButton) {
        // 数据
        List<MainActivityModule> mainActivityModuleList = new ArrayList<>(2);
        mainActivityModuleList.add(new MainActivityModule(1, "组件", "组件 注释"));
        mainActivityModuleList.add(new MainActivityModule(2, "待定", "待定 注释"));
        // MeasuredListView
        measuredListView.setOnItemClickListener((parent, view, position, id) -> execute(appCompatActivity, mainActivityModuleList.get(position)));
        // RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        recyclerView.addItemDecoration(new HorizontalDividerDecoration
                .Builder(appCompatActivity)
                .colorResId(R.color.colorPrimary)
                .size(appCompatActivity.getResources().getDimensionPixelSize(R.dimen.px1))
                .margin(12)
                .margin(12, 12)
                .marginDimen(R.dimen.dp_12)
                .marginDimen(R.dimen.dp_12, R.dimen.dp_12)
                .marginProvider(new HorizontalDividerDecoration.MarginProvider() {
                    @Override
                    public int dividerLeftMargin(int position, RecyclerView parent) {
                        return 36;
                    }

                    @Override
                    public int dividerRightMargin(int position, RecyclerView parent) {
                        return 36;
                    }
                })
                .showLastDivider()
                .build());
        recyclerView.addItemDecoration(new VerticalDividerDecoration
                .Builder(appCompatActivity)
                .colorResId(R.color.colorPrimary)
                .size(appCompatActivity.getResources().getDimensionPixelSize(R.dimen.px1))
                .margin(12)
                .margin(12, 12)
                .marginDimen(R.dimen.dp_12)
                .marginDimen(R.dimen.dp_12, R.dimen.dp_12)
                .marginProvider(new VerticalDividerDecoration.MarginProvider() {
                    @Override
                    public int dividerTopMargin(int position, RecyclerView parent) {
                        return 0;
                    }

                    @Override
                    public int dividerBottomMargin(int position, RecyclerView parent) {
                        return 0;
                    }
                })
                .showLastDivider()
                .build());
        // 适配器
        MainActivityListViewAdapter mainActivityListViewAdapter = new MainActivityListViewAdapter(appCompatActivity);
        mainActivityListViewAdapter.setOnItemChildViewClickListener((position, item, childView) -> ToastKit.showLong((mainActivityListViewAdapter.itemIsVisible(measuredListView, position) ? "可见" : "不可见") + " || " + item.getModuleAnnotation()));
        MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(appCompatActivity);
        mainActivityRecyclerViewAdapter.setOnItemClickListener((view, position) -> execute(appCompatActivity, mainActivityModuleList.get(position)));
        mainActivityRecyclerViewAdapter.setOnItemChildViewClickListener((position, item, childView) -> ToastKit.showLong((mainActivityListViewAdapter.itemIsVisible(measuredListView, position) ? "可见" : "不可见") + " || " + item.getModuleAnnotation()));
        // 显示
        if (recyclerView.getVisibility() == View.VISIBLE) {
            ViewUtils.hideView(recyclerView, View.GONE);
            ViewUtils.showView(measuredListView);
            mainActivityListViewAdapter.show(measuredListView, mainActivityModuleList);
            materialButton.setText(R.string.useListView);
        } else {
            ViewUtils.hideView(measuredListView, View.GONE);
            ViewUtils.showView(recyclerView);
            mainActivityRecyclerViewAdapter.show(recyclerView, mainActivityModuleList);
            materialButton.setText(R.string.useRecyclerView);
        }
    }

    /**
     * 执行
     *
     * @param appCompatActivity  活动
     * @param mainActivityModule 主页模块
     */
    private void execute(AppCompatActivity appCompatActivity, @NotNull MainActivityModule mainActivityModule) {
        switch (mainActivityModule.getModuleId()) {
            case 1:
                IntentJump.getInstance().jump(null, appCompatActivity, false, WidgetActivity.class);
                break;
            case 2:
                ToastKit.showShort("待定");
                break;
            default:
                break;
        }
    }
}
