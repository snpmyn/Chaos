package com.chaos.pool.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaos.janalytics.kit.JanalyticsKit;
import com.chaos.util.java.datetime.CurrentTimeMillisClock;
/**
 * Created on 2021/10/21
 * @author zsp
 * @desc BasePoolTwoFragment
 */
public abstract class BasePoolTwoFragment extends Fragment {
    /**
     * 基础视图
     */
    protected View baseView;
    /**
     * 浏览时长
     */
    private long browseDuration;
    /**
     * 极光分析浏览事件参数
     * <p>
     * 详参 {@link JanalyticsKit#onBrowseEvent(Context, String, String, String, float, String, String)}。
     */
    private String[] janalyticsBrowseEventParams;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseView = inflater.inflate(layoutResId(), container, false);
        janalyticsBrowseEventParams = janalyticsBrowseEventParams();
        eventBusRegister();
        stepUi(baseView);
        return baseView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initConfiguration();
        initData();
        setListener();
        startLogic();
    }
    /**
     * 布局资源 ID
     * @return 布局资源 ID
     */
    protected abstract int layoutResId();
    /**
     * 极光分析浏览事件参数
     * <p>
     * browseId – 浏览内容 ID（自定）
     * browseName – 浏览内容名（标题等，非空）
     * browseType – 浏览内容类型（如热点、汽车、财经等）
     * extMapKey – 扩展参数键
     * extMapValue – 扩展参数值
     * @return 极光分析浏览事件参数
     */
    protected abstract String[] janalyticsBrowseEventParams();
    /**
     * EventBus 注册
     * <p>
     * onDestroyView 反注册。
     */
    protected abstract void eventBusRegister();
    /**
     * 初始控件
     * <p>
     * 此处仅设 Toolbar 标题、返回箭头等轻量 UI 操作。
     * @param view 视图
     */
    protected abstract void stepUi(View view);
    /**
     * 初始配置
     */
    protected abstract void initConfiguration();
    /**
     * 初始数据
     */
    protected abstract void initData();
    /**
     * 设置监听
     */
    protected abstract void setListener();
    /**
     * 开始逻辑
     */
    protected abstract void startLogic();
    /**
     * EventBus 反注册
     *
     * onCreateView 注册。
     */
    protected abstract void eventBusUnregister();
    @Override
    public void onStart() {
        super.onStart();
        browseDuration = CurrentTimeMillisClock.getInstance().now();
    }
    @Override
    public void onStop() {
        super.onStop();
        browseDuration = (CurrentTimeMillisClock.getInstance().now() - browseDuration);
        JanalyticsKit.onBrowseEvent(getContext(), janalyticsBrowseEventParams[0], janalyticsBrowseEventParams[1], janalyticsBrowseEventParams[2], browseDuration, janalyticsBrowseEventParams[3], janalyticsBrowseEventParams[4]);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBusUnregister();
    }
}

