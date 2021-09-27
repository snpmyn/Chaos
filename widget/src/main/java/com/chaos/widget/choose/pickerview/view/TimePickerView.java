package com.chaos.widget.choose.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.widget.R;
import com.chaos.widget.choose.pickerview.configure.PickerOptions;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;
import value.WidgetMagic;

/**
 * @decs: 时间选择器
 * @author: 郑少鹏
 * @date: 2018/4/3 17:35
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "bill_cancel";
    /**
     * 自定义控件
     */
    private WheelTime wheelTime;

    public TimePickerView(@NonNull PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.pickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        if (null == pickerOptions.customListener) {
            LayoutInflater.from(context).inflate(R.layout.picker_view_time, contentContainer);
            // 顶标
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rlTop = (RelativeLayout) findViewById(R.id.rlTop);
            // 确定 / 取消
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);
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
            // 文字大小
            btnSubmit.setTextSize(pickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(pickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(pickerOptions.textSizeTitle);
        } else {
            pickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(pickerOptions.layoutResId, contentContainer));
        }
        // 时间转轮（自定）
        LinearLayout timePicker = (LinearLayout) findViewById(R.id.timePicker);
        timePicker.setBackgroundColor(pickerOptions.bgColorWheel);
        initWheelTime(context, timePicker);
    }

    private void initWheelTime(Context context, LinearLayout timePickerView) {
        wheelTime = new WheelTime(timePickerView, pickerOptions.type, pickerOptions.textGravity, pickerOptions.textSizeContent);
        if (null != pickerOptions.onTimeSelectChangeListener) {
            wheelTime.setSelectChangeCallback(() -> {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    pickerOptions.onTimeSelectChangeListener.onTimeSelectChanged(date);
                } catch (ParseException e) {
                    Timber.e(e);
                }
            });
        }
        wheelTime.setAreLunarMode(pickerOptions.areLunarCalendar);
        if ((pickerOptions.startYear != 0) && (pickerOptions.endYear != 0) && (pickerOptions.startYear <= pickerOptions.endYear)) {
            setRange();
        }
        // 手设时间范围限制
        if ((null != pickerOptions.startDate) && (null != pickerOptions.endDate)) {
            if (pickerOptions.startDate.getTimeInMillis() > pickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            } else {
                setRangDate();
            }
        } else if (null != pickerOptions.startDate) {
            if (pickerOptions.startDate.get(Calendar.YEAR) < WidgetMagic.INT_ONE_THOUSAND_NINE_HUNDRED) {
                throw new IllegalArgumentException("The startDate can not as early as 1900.");
            } else {
                setRangDate();
            }
        } else if (null != pickerOptions.endDate) {
            if (pickerOptions.endDate.get(Calendar.YEAR) > WidgetMagic.INT_TWO_THOUSAND) {
                throw new IllegalArgumentException("The endDate should not be later than 2100.");
            } else {
                setRangDate();
            }
        } else {
            // 没设时间范围限制则用默范围
            setRangDate();
        }
        setTime();
        wheelTime.setLabels(pickerOptions.labelYear, pickerOptions.labelMonth, pickerOptions.labelDay, pickerOptions.labelHour, pickerOptions.labelMinute, pickerOptions.labelSecond);
        wheelTime.setxOffsetOfText(pickerOptions.xOffsetYear, pickerOptions.xOffsetMonth, pickerOptions.xOffsetDay, pickerOptions.xOffsetHour, pickerOptions.xOffsetMinute, pickerOptions.xOffsetSecond);
        setOutSideCancelable(pickerOptions.cancelable);
        wheelTime.setCyclic(pickerOptions.cyclic);
        // 自定
        wheelTime.setDividerColor(ContextCompat.getColor(context, R.color.gray));
        wheelTime.setDividerType(pickerOptions.dividerType);
        wheelTime.setLineSpacingMultiplier(pickerOptions.lineSpacingMultiplier);
        // 自定
        wheelTime.setTextColorOut(ContextCompat.getColor(context, R.color.fontHint));
        // 自定
        wheelTime.setTextColorCenter(ContextCompat.getColor(context, R.color.fontInput));
        wheelTime.setAreCenterLabel(pickerOptions.areCenterLabel);
    }

    /**
     * 默时
     */
    public void setDate(Calendar date) {
        pickerOptions.date = date;
        setTime();
    }

    /**
     * 可选时间范围（setTime 前调有效）
     */
    private void setRange() {
        wheelTime.setStartYear(pickerOptions.startYear);
        wheelTime.setEndYear(pickerOptions.endYear);
    }

    /**
     * 可选择时间范围（setTime 前调有效）
     */
    private void setRangDate() {
        wheelTime.setRangDate(pickerOptions.startDate, pickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        // 手设时间范围
        if ((null != pickerOptions.startDate) && (null != pickerOptions.endDate)) {
            // 默时未设或所设默时越界，则设默选时为开始时
            if ((null == pickerOptions.date) || (pickerOptions.date.getTimeInMillis() < pickerOptions.startDate.getTimeInMillis()) || (pickerOptions.date.getTimeInMillis() > pickerOptions.endDate.getTimeInMillis())) {
                pickerOptions.date = pickerOptions.startDate;
            }
        } else if (null != pickerOptions.startDate) {
            // 没设默选时则以开始时为默时
            pickerOptions.date = pickerOptions.startDate;
        } else if (null != pickerOptions.endDate) {
            pickerOptions.date = pickerOptions.endDate;
        }
    }

    /**
     * 选中时（默选当前时）
     */
    private void setTime() {
        int year, month, day, hour, minute, second;
        Calendar calendar = Calendar.getInstance();
        if (null == pickerOptions.date) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            second = calendar.get(Calendar.SECOND);
        } else {
            year = pickerOptions.date.get(Calendar.YEAR);
            month = pickerOptions.date.get(Calendar.MONTH);
            day = pickerOptions.date.get(Calendar.DAY_OF_MONTH);
            hour = pickerOptions.date.get(Calendar.HOUR_OF_DAY);
            minute = pickerOptions.date.get(Calendar.MINUTE);
            second = pickerOptions.date.get(Calendar.SECOND);
        }
        wheelTime.setPicker(year, month, day, hour, minute, second);
    }

    @Override
    public void onClick(@NonNull View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    private void returnData() {
        if (null != pickerOptions.onTimeSelectListener) {
            try {
                Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                pickerOptions.onTimeSelectListener.onTimeSelect(date, clickView);
            } catch (ParseException e) {
                Timber.e(e);
            }
        }
    }

    /**
     * 动设标题
     *
     * @param title 标题内容
     */
    public void setTitleText(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (null != tvTitle) {
            tvTitle.setText(title);
        }
    }

    public boolean setAreLunarCalendar() {
        return wheelTime.areLunarMode();
    }

    /**
     * 暂仅支持设 1900 - 2100 年
     *
     * @param lunar 农历开关
     */
    public void setLunarCalendar(boolean lunar) {
        try {
            int year, month, day, hour, minute, second;
            Calendar calendar = Calendar.getInstance();
            Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
            if (null != date) {
                calendar.setTime(date);
            }
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            second = calendar.get(Calendar.SECOND);
            wheelTime.setAreLunarMode(lunar);
            wheelTime.setLabels(pickerOptions.labelYear, pickerOptions.labelMonth, pickerOptions.labelDay, pickerOptions.labelHour, pickerOptions.labelMinute, pickerOptions.labelSecond);
            wheelTime.setPicker(year, month, day, hour, minute, second);
        } catch (ParseException e) {
            Timber.e(e);
        }
    }

    @Override
    public boolean areDialog() {
        return pickerOptions.areDialog;
    }
}
