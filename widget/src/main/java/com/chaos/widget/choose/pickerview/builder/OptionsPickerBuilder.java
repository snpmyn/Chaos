package com.chaos.widget.choose.pickerview.builder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;

import com.chaos.widget.choose.pickerview.configure.PickerOptions;
import com.chaos.widget.choose.pickerview.listener.CustomListener;
import com.chaos.widget.choose.pickerview.listener.OnOptionsSelectChangeListener;
import com.chaos.widget.choose.pickerview.listener.OnOptionsSelectListener;
import com.chaos.widget.choose.pickerview.view.OptionsPickerView;
import com.chaos.widget.choose.wheelview.view.WheelView;

/**
 * @decs: OptionsPickerBuilder
 * @author: 郑少鹏
 * @date: 2018/4/3 16:58
 */
public class OptionsPickerBuilder {
    /**
     * 配置类
     */
    private final PickerOptions pickerOptions;

    /**
     * Required
     *
     * @param context                 上下文
     * @param onOptionsSelectListener 监听
     */
    public OptionsPickerBuilder(Context context, OnOptionsSelectListener onOptionsSelectListener) {
        this.pickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_OPTIONS);
        this.pickerOptions.context = context;
        this.pickerOptions.onOptionsSelectListener = onOptionsSelectListener;
    }

    /**
     * Option
     *
     * @param textContentConfirm textContentConfirm
     * @return optionsPickerBuilder
     */
    public OptionsPickerBuilder setSubmitText(String textContentConfirm) {
        pickerOptions.textContentConfirm = textContentConfirm;
        return this;
    }

    public OptionsPickerBuilder setCancelText(String textContentCancel) {
        pickerOptions.textContentCancel = textContentCancel;
        return this;
    }

    public OptionsPickerBuilder setTitleText(String textContentTitle) {
        pickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public OptionsPickerBuilder setAreDialog(boolean areDialog) {
        pickerOptions.areDialog = areDialog;
        return this;
    }

    public OptionsPickerBuilder setSubmitColor(int textColorConfirm) {
        pickerOptions.textColorConfirm = textColorConfirm;
        return this;
    }

    public OptionsPickerBuilder setCancelColor(int textColorCancel) {
        pickerOptions.textColorCancel = textColorCancel;
        return this;
    }

    /**
     * 显时外背景色（默灰）
     *
     * @param backgroundId color resId.
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setBackgroundId(int backgroundId) {
        pickerOptions.backgroundId = backgroundId;
        return this;
    }

    /**
     * ViewGroup 类型
     * PickerView 显示容器
     *
     * @param decorView Parent View
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setDecorView(ViewGroup decorView) {
        pickerOptions.decorView = decorView;
        return this;
    }

    public OptionsPickerBuilder setLayoutResId(int layoutResId, CustomListener customListener) {
        pickerOptions.layoutResId = layoutResId;
        pickerOptions.customListener = customListener;
        return this;
    }

    public OptionsPickerBuilder setBackgroundColor(int backgroundColorWheel) {
        pickerOptions.bgColorWheel = backgroundColorWheel;
        return this;
    }

    public OptionsPickerBuilder setTitleBackgroundColor(int backgroundColorTitle) {
        pickerOptions.bgColorTitle = backgroundColorTitle;
        return this;
    }

    public OptionsPickerBuilder setTitleColor(int textColorTitle) {
        pickerOptions.textColorTitle = textColorTitle;
        return this;
    }

    public OptionsPickerBuilder setSubCalSize(int textSizeSubmitCancel) {
        pickerOptions.textSizeSubmitCancel = textSizeSubmitCancel;
        return this;
    }

    public OptionsPickerBuilder setTitleSize(int textSizeTitle) {
        pickerOptions.textSizeTitle = textSizeTitle;
        return this;
    }

    public OptionsPickerBuilder setContentTextSize(int textSizeContent) {
        pickerOptions.textSizeContent = textSizeContent;
        return this;
    }

    public OptionsPickerBuilder setOutSideCancelable(boolean cancelable) {
        pickerOptions.cancelable = cancelable;
        return this;
    }

    public OptionsPickerBuilder setLabel(String label1, String label2, String label3) {
        pickerOptions.label1 = label1;
        pickerOptions.label2 = label2;
        pickerOptions.label3 = label3;
        return this;
    }

    /**
     * Item 间距倍数（控 Item 高间隔）
     *
     * @param lineSpacingMultiplier 浮点型（1.0 - 4.0f 有效）（超取极值）
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setLineSpacingMultiplier(float lineSpacingMultiplier) {
        pickerOptions.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    /**
     * Set item divider line type color.
     *
     * @param dividerColor color resId.
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setDividerColor(int dividerColor) {
        pickerOptions.dividerColor = dividerColor;
        return this;
    }

    /**
     * Set item divider line type.
     *
     * @param dividerType enum Type {@link WheelView.DividerType}
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setDividerType(WheelView.DividerType dividerType) {
        pickerOptions.dividerType = dividerType;
        return this;
    }

    /**
     * Set the textColor of selected item.
     *
     * @param textColorCenter color res.
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setTextColorCenter(int textColorCenter) {
        pickerOptions.textColorCenter = textColorCenter;
        return this;
    }

    /**
     * Set the textColor of outside item.
     *
     * @param textColorOut color resId.
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setTextColorOut(int textColorOut) {
        pickerOptions.textColorOut = textColorOut;
        return this;
    }

    public OptionsPickerBuilder setTypeface(Typeface font) {
        pickerOptions.font = font;
        return this;
    }

    public OptionsPickerBuilder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        pickerOptions.cyclic1 = cyclic1;
        pickerOptions.cyclic2 = cyclic2;
        pickerOptions.cyclic3 = cyclic3;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1) {
        pickerOptions.option1 = option1;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1, int option2) {
        pickerOptions.option1 = option1;
        pickerOptions.option2 = option2;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int option1, int option2, int option3) {
        pickerOptions.option1 = option1;
        pickerOptions.option2 = option2;
        pickerOptions.option3 = option3;
        return this;
    }

    public OptionsPickerBuilder setxOffsetOfText(int xOffsetOne, int xOffsetTwo, int xOffsetThree) {
        pickerOptions.xOffsetOne = xOffsetOne;
        pickerOptions.xOffsetTwo = xOffsetTwo;
        pickerOptions.xOffsetThree = xOffsetThree;
        return this;
    }

    public OptionsPickerBuilder setAreCenterLabel(boolean areCenterLabel) {
        pickerOptions.areCenterLabel = areCenterLabel;
        return this;
    }

    /**
     * 切选项时还原第一项
     *
     * @param areRestoreItem true 还原 / false 保上选项
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setAreRestoreItem(boolean areRestoreItem) {
        pickerOptions.areRestoreItem = areRestoreItem;
        return this;
    }

    /**
     * 设选变监听
     *
     * @param onOptionsSelectChangeListener 切 Item 项滚动停止时实时回调监听
     * @return OptionsPickerBuilder
     */
    public OptionsPickerBuilder setOptionsSelectChangeListener(OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        pickerOptions.onOptionsSelectChangeListener = onOptionsSelectChangeListener;
        return this;
    }

    public <T> OptionsPickerView<T> build() {
        return new OptionsPickerView<>(pickerOptions);
    }
}
