package com.chaos.widget.choose.pickerview.builder;

import android.content.Context;
import android.view.ViewGroup;

import com.chaos.widget.choose.pickerview.configure.PickerOptions;
import com.chaos.widget.choose.pickerview.listener.CustomListener;
import com.chaos.widget.choose.pickerview.listener.OnTimeSelectChangeListener;
import com.chaos.widget.choose.pickerview.listener.OnTimeSelectListener;
import com.chaos.widget.choose.pickerview.view.TimePickerView;
import com.chaos.widget.choose.wheelview.view.WheelView;

import java.util.Calendar;

/**
 * @decs: TimePickerBuilder
 * @author: 郑少鹏
 * @date: 2018/4/3 17:01
 */
public class TimePickerBuilder {
    /**
     * 配置类
     */
    private final PickerOptions pickerOptions;

    /**
     * Required
     *
     * @param context              上下文
     * @param onTimeSelectListener 监听
     */
    public TimePickerBuilder(Context context, OnTimeSelectListener onTimeSelectListener) {
        this.pickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_TIME);
        this.pickerOptions.context = context;
        this.pickerOptions.onTimeSelectListener = onTimeSelectListener;
    }

    /**
     * Option
     *
     * @param gravity 位
     * @return timePickerBuilder
     */
    public TimePickerBuilder setGravity(int gravity) {
        this.pickerOptions.textGravity = gravity;
        return this;
    }

    /**
     * new boolean[]{true, true, true, false, false, false}
     * control the "year","month","day","hours","minutes","seconds" display or hide
     * 分控 “年” “月” “日” “时” “分” “秒” 显隐
     *
     * @param type 布尔型数组（长需设 6）
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setType(boolean[] type) {
        pickerOptions.type = type;
        return this;
    }

    public TimePickerBuilder setSubmitText(String textContentConfirm) {
        pickerOptions.textContentConfirm = textContentConfirm;
        return this;
    }

    public TimePickerBuilder setAreDialog(boolean areDialog) {
        pickerOptions.areDialog = areDialog;
        return this;
    }

    public TimePickerBuilder setCancelText(String textContentCancel) {
        pickerOptions.textContentCancel = textContentCancel;
        return this;
    }

    public TimePickerBuilder setTitleText(String textContentTitle) {
        pickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public TimePickerBuilder setSubmitColor(int textColorConfirm) {
        pickerOptions.textColorConfirm = textColorConfirm;
        return this;
    }

    public TimePickerBuilder setCancelColor(int textColorCancel) {
        pickerOptions.textColorCancel = textColorCancel;
        return this;
    }

    /**
     * ViewGroup 类型容器
     *
     * @param decorView 选择器被添到此容器
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setDecorView(ViewGroup decorView) {
        pickerOptions.decorView = decorView;
        return this;
    }

    public TimePickerBuilder setBackgroundColor(int backgroundColorWheel) {
        pickerOptions.bgColorWheel = backgroundColorWheel;
        return this;
    }

    public TimePickerBuilder setTitleBackgroundColor(int backgroundColorTitle) {
        pickerOptions.bgColorTitle = backgroundColorTitle;
        return this;
    }

    public TimePickerBuilder setTitleColor(int textColorTitle) {
        pickerOptions.textColorTitle = textColorTitle;
        return this;
    }

    public TimePickerBuilder setSubCalSize(int textSizeSubmitCancel) {
        pickerOptions.textSizeSubmitCancel = textSizeSubmitCancel;
        return this;
    }

    public TimePickerBuilder setTitleSize(int textSizeTitle) {
        pickerOptions.textSizeTitle = textSizeTitle;
        return this;
    }

    public TimePickerBuilder setContentTextSize(int textSizeContent) {
        pickerOptions.textSizeContent = textSizeContent;
        return this;
    }

    /**
     * 系统 Calendar 月 0 - 11
     * 故调 Calendar 之 set 设时月范围亦 0 - 11
     *
     * @param date 日期
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setDate(Calendar date) {
        pickerOptions.date = date;
        return this;
    }

    public TimePickerBuilder setLayoutResId(int layoutResId, CustomListener customListener) {
        pickerOptions.layoutResId = layoutResId;
        pickerOptions.customListener = customListener;
        return this;
    }

    /**
     * 起始时
     * <p>
     * 系统 Calendar 月 0 - 11
     * 故调 Calendar 之 set 设时月范围亦 0 - 11
     *
     * @param startDate 开始日期
     * @param endDate   终止日期
     * @return timePickerBuilder
     */
    public TimePickerBuilder setRangDate(Calendar startDate, Calendar endDate) {
        pickerOptions.startDate = startDate;
        pickerOptions.endDate = endDate;
        return this;
    }

    /**
     * 间距倍数（1.0 - 4.0f）
     *
     * @param lineSpacingMultiplier 间距倍数
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        pickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * 分割线色
     *
     * @param dividerColor 分割线色
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setDividerColor(int dividerColor) {
        pickerOptions.dividerColor = dividerColor;
        return this;
    }

    /**
     * 分割线类型
     *
     * @param dividerType 分割线类型
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setDividerType(WheelView.DividerType dividerType) {
        pickerOptions.dividerType = dividerType;
        return this;
    }

    /**
     * 显外背景色（默灰）
     *
     * @param backgroundId 显外背景色
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setBackgroundId(int backgroundId) {
        pickerOptions.backgroundId = backgroundId;
        return this;
    }

    /**
     * 分割线间文本色
     *
     * @param textColorCenter 分割线间文本色
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setTextColorCenter(int textColorCenter) {
        pickerOptions.textColorCenter = textColorCenter;
        return this;
    }

    /**
     * 分割线外文本色
     *
     * @param textColorOut 分割线外文本色
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setTextColorOut(int textColorOut) {
        pickerOptions.textColorOut = textColorOut;
        return this;
    }

    public TimePickerBuilder setCyclic(boolean cyclic) {
        pickerOptions.cyclic = cyclic;
        return this;
    }

    public TimePickerBuilder setOutSideCancelable(boolean cancelable) {
        pickerOptions.cancelable = cancelable;
        return this;
    }

    public TimePickerBuilder setLunarCalendar(boolean lunarCalendar) {
        pickerOptions.areLunarCalendar = lunarCalendar;
        return this;
    }

    public TimePickerBuilder setLabel(String labelYear, String labelMonth, String labelDay, String labelHour, String labelMinute, String labelSecond) {
        pickerOptions.labelYear = labelYear;
        pickerOptions.labelMonth = labelMonth;
        pickerOptions.labelDay = labelDay;
        pickerOptions.labelHour = labelHour;
        pickerOptions.labelMinute = labelMinute;
        pickerOptions.labelSecond = labelSecond;
        return this;
    }

    /**
     * X 轴倾斜角度[ -90, 90°]
     *
     * @param xOffsetYear   年
     * @param xOffsetMonth  月
     * @param xOffsetDay    日
     * @param xOffsetHour   时
     * @param xOffsetMinute 分
     * @param xOffsetSecond 秒
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setxOffsetOfText(int xOffsetYear, int xOffsetMonth, int xOffsetDay, int xOffsetHour, int xOffsetMinute, int xOffsetSecond) {
        pickerOptions.xOffsetYear = xOffsetYear;
        pickerOptions.xOffsetMonth = xOffsetMonth;
        pickerOptions.xOffsetDay = xOffsetDay;
        pickerOptions.xOffsetHour = xOffsetHour;
        pickerOptions.xOffsetMinute = xOffsetMinute;
        pickerOptions.xOffsetSecond = xOffsetSecond;
        return this;
    }

    public TimePickerBuilder setAreCenterLabel(boolean areCenterLabel) {
        pickerOptions.areCenterLabel = areCenterLabel;
        return this;
    }

    /**
     * 设选变监听
     *
     * @param onTimeSelectChangeListener 切 Item 项滚动停止时实时回调监听
     * @return TimePickerBuilder
     */
    public TimePickerBuilder setTimeSelectChangeListener(OnTimeSelectChangeListener onTimeSelectChangeListener) {
        pickerOptions.onTimeSelectChangeListener = onTimeSelectChangeListener;
        return this;
    }

    public TimePickerView build() {
        return new TimePickerView(pickerOptions);
    }
}
