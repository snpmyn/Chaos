package com.chaos.widget.choose.wheelview.joggle;

/**
 * Created on 2018/4/3.
 *
 * @author 郑少鹏
 * @desc IPickerViewData
 */
public interface IPickerViewData {
    /**
     * 实现 IPickerViewData 接口
     * <p>
     * 用于显 PickerView 上字符串
     * PickerView 通 IPickerViewData 获 getPickerViewText 并显
     *
     * @return String
     */
    String getPickerViewText();
}