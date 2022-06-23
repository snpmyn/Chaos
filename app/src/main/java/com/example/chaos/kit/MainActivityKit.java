package com.example.chaos.kit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.pgyer.kit.PgyerKit;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.view.ViewUtils;
import com.chaos.widget.other.adapter.decoration.HorizontalDividerDecoration;
import com.chaos.widget.other.adapter.decoration.VerticalDividerDecoration;
import com.chaos.widget.other.listview.MeasuredListView;
import com.chaos.widget.snackbar.SnackbarKit;
import com.chaos.widget.transition.kit.TransitionKit;
import com.example.chaos.R;
import com.example.chaos.adapter.MainActivityListViewAdapter;
import com.example.chaos.adapter.MainActivityRecyclerViewAdapter;
import com.example.chaos.bean.MainActivityModule;
import com.google.android.material.button.MaterialButton;
import com.pgyer.pgyersdk.callback.CheckoutCallBack;
import com.pgyer.pgyersdk.model.CheckSoftModel;

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
    public void display(AppCompatActivity appCompatActivity, @NonNull MeasuredListView measuredListView, @NonNull RecyclerView recyclerView, MaterialButton materialButton) {
        // 数据
        List<MainActivityModule> mainActivityModuleList = new ArrayList<>(2);
        mainActivityModuleList.add(new MainActivityModule(1, "组件", "组件 注释"));
        mainActivityModuleList.add(new MainActivityModule(2, "待定", "待定 注释"));
        // MeasuredListView
        measuredListView.setOnItemClickListener((parent, view, position, id) -> execute(appCompatActivity, view, mainActivityModuleList.get(position)));
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
        mainActivityListViewAdapter.setOnItemChildViewClickListener((position, item, childView) -> SnackbarKit.snackbarCreateByCharSequenceAndShow(childView, (mainActivityListViewAdapter.itemIsVisible(measuredListView, position) ? "可见" : "不可见") + " || " + item.getModuleAnnotation(), false));
        MainActivityRecyclerViewAdapter mainActivityRecyclerViewAdapter = new MainActivityRecyclerViewAdapter(appCompatActivity);
        mainActivityRecyclerViewAdapter.setOnItemClickListener((view, position) -> execute(appCompatActivity, view, mainActivityModuleList.get(position)));
        mainActivityRecyclerViewAdapter.setOnItemChildViewClickListener((position, item, childView) -> SnackbarKit.snackbarCreateByCharSequenceWithActionByCharSequenceAndShow(childView, (mainActivityListViewAdapter.itemIsVisible(measuredListView, position) ? "可见" : "不可见") + " || " + item.getModuleAnnotation(), false, appCompatActivity.getString(R.string.ok), (view, snackbar) -> snackbar.dismiss()));
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
     * @param view               视图
     * @param mainActivityModule 主页模块
     */
    private void execute(AppCompatActivity appCompatActivity, View view, @NonNull MainActivityModule mainActivityModule) {
        switch (mainActivityModule.getModuleId()) {
            case 1:
                TransitionKit.getInstance().jumpWithTransition(appCompatActivity, view, new Intent(appCompatActivity, WidgetActivity.class));
                break;
            case 2:
                SnackbarKit.snackbarCreateByResIdAndShow(appCompatActivity.getWindow().getDecorView(), R.string.notRealizeYet, false);
                break;
            default:
                break;
        }
    }

    /**
     * 检测版本更新
     *
     * @param appCompatActivity 活动
     */
    public void checkVersionUpdate(AppCompatActivity appCompatActivity) {
        PgyerKit.checkVersionUpdate(appCompatActivity, new CheckoutCallBack() {
            @Override
            public void onNewVersionExist(CheckSoftModel checkSoftModel) {
                ToastKit.showShort(checkSoftModel.getForceUpdateVersionNo());
            }

            @Override
            public void onNonentityVersionExist(String s) {
                ToastKit.showShort(s);
            }

            @Override
            public void onFail(String s) {
                ToastKit.showShort(s);
            }
        });
    }
}
