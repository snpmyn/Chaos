package com.chaos.widget.choose.provincialandurbanlinkage;

import com.chaos.widget.choose.wheelview.joggle.IPickerViewData;

import java.util.List;

/**
 * Created on 2018/4/3.
 *
 * @author xxx
 * @desc 位选实体类
 */
public class LocationBeanPicker implements IPickerViewData {
    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */
    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    /**
     * 实现 IPickerViewData 接口
     * <p>
     * 用于显 PickerView 上字符串
     * PickerView 通 IPickerViewData 获 getPickerViewText 并显
     *
     * @return String
     */
    @Override
    public String getPickerViewText() {
        return this.name;
    }

    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */
        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}

