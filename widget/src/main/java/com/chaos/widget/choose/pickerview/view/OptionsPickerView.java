package com.chaos.widget.choose.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;
import com.chaos.widget.choose.pickerview.configure.PickerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * @decs: 条件选择器
 * @author: 郑少鹏
 * @date: 2018/4/3 17:34
 */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "bill_cancel";
    private WheelOptions<T> wheelOptions;

    public OptionsPickerView(@NonNull PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.pickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        if (null == pickerOptions.customListener) {
            LayoutInflater.from(context).inflate(pickerOptions.layoutResId, contentContainer);
            // 顶标
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rlTop = (RelativeLayout) findViewById(R.id.rlTop);
            // 确定 / 取消按钮
            MaterialButton btnSubmit = (MaterialButton) findViewById(R.id.btnSubmit);
            MaterialButton btnCancel = (MaterialButton) findViewById(R.id.btnCancel);
            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);
            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            // 文本
            btnSubmit.setText(TextUtils.isEmpty(pickerOptions.textContentConfirm) ? context.getResources().getString(R.string.ensure) : pickerOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(pickerOptions.textContentCancel) ? context.getResources().getString(R.string.cancel) : pickerOptions.textContentCancel);
            // 默空
            tvTitle.setText(TextUtils.isEmpty(pickerOptions.textContentTitle) ? "" : pickerOptions.textContentTitle);
            // 自定
            btnSubmit.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
            // 自定
            btnCancel.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
            // 自定
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.fontInput));
            // 自定
            rlTop.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            // 文本大小
            btnSubmit.setTextSize(pickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(pickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(pickerOptions.textSizeTitle);
        } else {
            pickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(pickerOptions.layoutResId, contentContainer));
        }
        // 滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionsPicker);
        optionsPicker.setBackgroundColor(pickerOptions.bgColorWheel);
        wheelOptions = new WheelOptions<>(optionsPicker, pickerOptions.areRestoreItem);
        if (null != pickerOptions.onOptionsSelectChangeListener) {
            wheelOptions.setOnOptionsSelectChangeListener(pickerOptions.onOptionsSelectChangeListener);
        }
        wheelOptions.setTextContentSize(pickerOptions.textSizeContent);
        wheelOptions.setLabels(pickerOptions.label1, pickerOptions.label2, pickerOptions.label3);
        wheelOptions.setxOffsetOfText(pickerOptions.xOffsetOne, pickerOptions.xOffsetTwo, pickerOptions.xOffsetThree);
        wheelOptions.setCyclic(pickerOptions.cyclic1, pickerOptions.cyclic2, pickerOptions.cyclic3);
        wheelOptions.setTypeface(pickerOptions.font);
        setOutSideCancelable(pickerOptions.cancelable);
        // 自定
        wheelOptions.setDividerColor(ContextCompat.getColor(context, R.color.gray));
        wheelOptions.setDividerType(pickerOptions.dividerType);
        wheelOptions.setLineSpacingMultiplier(pickerOptions.lineSpacingMultiplier);
        // 自定
        wheelOptions.setTextColorOut(ContextCompat.getColor(context, R.color.fontHint));
        // 自定
        wheelOptions.setTextColorCenter(ContextCompat.getColor(context, R.color.fontInput));
        wheelOptions.setAreCenterLabel(pickerOptions.areCenterLabel);
    }

    /**
     * 动设标题
     *
     * @param title 标题
     */
    public void setTitleText(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (null != tvTitle) {
            tvTitle.setText(title);
        }
    }

    /**
     * 默选中项
     *
     * @param option1 默选中项
     */
    public void setSelectOptions(int option1) {
        pickerOptions.option1 = option1;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2) {
        pickerOptions.option1 = option1;
        pickerOptions.option2 = option2;
        reSetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        pickerOptions.option1 = option1;
        pickerOptions.option2 = option2;
        pickerOptions.option3 = option3;
        reSetCurrentItems();
    }

    private void reSetCurrentItems() {
        if (null != wheelOptions) {
            wheelOptions.setCurrentItems(pickerOptions.option1, pickerOptions.option2, pickerOptions.option3);
        }
    }

    public void setPicker(List<T> optionsItems) {
        this.setPicker(optionsItems, null, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items) {
        this.setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }

    /**
     * 不联动调
     *
     * @param options1Items options1Items
     * @param options2Items options2Items
     * @param options3Items options3Items
     */
    public void setNnPicker(List<T> options1Items, List<T> options2Items, List<T> options3Items) {
        wheelOptions.setLinkage();
        wheelOptions.setNnPicker(options1Items, options2Items, options3Items);
        reSetCurrentItems();
    }

    @Override
    public void onClick(@NonNull View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    /**
     * 抽离接口回调方法
     */
    private void returnData() {
        if (null != pickerOptions.onOptionsSelectListener) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            pickerOptions.onOptionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], clickView);
        }
    }

    @Override
    public boolean areDialog() {
        return pickerOptions.areDialog;
    }
}
