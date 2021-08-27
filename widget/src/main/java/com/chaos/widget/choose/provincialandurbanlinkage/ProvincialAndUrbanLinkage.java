package com.chaos.widget.choose.provincialandurbanlinkage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.util.java.json.JsonTransform;
import com.chaos.util.java.thread.ThreadManager;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.choose.pickerview.builder.OptionsPickerBuilder;
import com.chaos.widget.choose.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created on 2018/4/3.
 *
 * @author xxx
 * @desc 省市区联动
 */
public class ProvincialAndUrbanLinkage {
    private final AppCompatActivity appCompatActivity;
    private OptionsPickerView optionsPickerView;
    private ArrayList<LocationBeanPicker> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    /**
     * 接口
     */
    public Location location;

    /**
     * constructor
     *
     * @param appCompatActivity 活动
     */
    public ProvincialAndUrbanLinkage(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    /**
     * 初始条件选择器
     */
    public void stepOptionsPickerView() {
        if (null != optionsPickerView) {
            return;
        }
        optionsPickerView = new OptionsPickerBuilder(appCompatActivity, (options1, options2, options3, v) -> {
            // 三级选中数据
            String first = options1Items.get(options1).getPickerViewText();
            String second = options2Items.get(options1).get(options2);
            String third = options3Items.get(options1).get(options2).get(options3);
            if (!"".equals(third)) {
                location.location(third);
            } else if (!"".equals(second)) {
                location.location(second);
            } else if (!"".equals(first)) {
                location.location(first);
            }
        }).setAreDialog(true).build();
    }

    /**
     * 设置接口
     *
     * @param location 定位
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 加载数据
     * <p>
     * PickerOptions 存自定
     * BasePickerView 存自定
     * OptionsPickerView 存自定
     */
    public void loadData() {
        ThreadManager.stepScheduledExecutorService().execute(() -> {
            // 子线程解省市区数据
            stepJsonData(appCompatActivity);
        });
    }

    /**
     * 初始 JSON 数据
     *
     * @param context 上下文
     */
    private void stepJsonData(Context context) {
        // assets 下 Json 文件仅参考（实可替）
        // 关键逻辑在循环体
        String jsonData = new JsonTransform().jsonTransformFromAssets(context, "linkage/province.json");
        // Gson 转实体
        ArrayList<LocationBeanPicker> jsonBean = parseData(jsonData);
        // 添省数据
        // 添 JavaBean 实体则实体类需实现 IPickerViewData 接口
        // PickerView 通 getPickerViewText 获字符并显
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {
            // 遍历省
            // 该省城市列表（第二级）
            ArrayList<String> cityList = new ArrayList<>();
            // 该省地区列表（第三极）
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                // 遍历该省城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                // 添城市
                cityList.add(cityName);
                // 该城市地区列表
                ArrayList<String> cityAreaList = new ArrayList<>();
                // 无地区数据建添空字符串
                // 防数据 null 致三选项长度不匹配造崩溃
                if ((null == jsonBean.get(i).getCityList().get(c).getArea()) || (jsonBean.get(i).getCityList().get(c).getArea().size() == 0)) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                // 添该省地区数据
                provinceAreaList.add(cityAreaList);
            }
            // 添城市数据
            options2Items.add(cityList);
            // 添地区数据
            options3Items.add(provinceAreaList);
        }
        appCompatActivity.runOnUiThread(this::showPickerView);
    }

    @NonNull
    private ArrayList<LocationBeanPicker> parseData(String result) {
        // Gson 解析
        ArrayList<LocationBeanPicker> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                LocationBeanPicker entity = gson.fromJson(data.optJSONObject(i).toString(), LocationBeanPicker.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            Timber.e(e);
            appCompatActivity.runOnUiThread(() -> ToastKit.showShort("加载地址数据失败"));
        }
        return detail;
    }

    /**
     * 选择器
     * <p>
     * 动画（缩放）仅需设 isDialog 为 true
     */
    private void showPickerView() {
        // 一级
        optionsPickerView.setPicker(options1Items);
        // 二级
        optionsPickerView.setPicker(options1Items, options2Items);
        // 三级
        optionsPickerView.setPicker(options1Items, options2Items, options3Items);
        optionsPickerView.show();
    }

    /**
     * 显示否
     *
     * @return 显示否
     */
    public boolean areShowing() {
        return optionsPickerView.areShowing();
    }
}
