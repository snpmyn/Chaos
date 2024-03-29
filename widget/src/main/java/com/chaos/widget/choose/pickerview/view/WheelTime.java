package com.chaos.widget.choose.pickerview.view;

import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.widget.R;
import com.chaos.widget.choose.pickerview.adapter.ArrayWheelAdapter;
import com.chaos.widget.choose.pickerview.adapter.NumericWheelAdapter;
import com.chaos.widget.choose.pickerview.listener.ISelectTimeCallback;
import com.chaos.widget.choose.pickerview.util.ChinaDate;
import com.chaos.widget.choose.pickerview.util.LunarCalendar;
import com.chaos.widget.choose.wheelview.view.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import value.WidgetMagic;

/**
 * @decs: WheelTime
 * @author: 郑少鹏
 * @date: 2018/4/3 17:48
 */
public class WheelTime {
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private final int gravity;
    private final boolean[] type;
    private final int textSize;
    private View view;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHours;
    private WheelView wvMinutes;
    private WheelView wvSeconds;
    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    /**
     * 表 31 天
     */
    private int endDay = DEFAULT_END_DAY;
    private int currentYear;
    /**
     * 文本 / 分割线色
     */
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;
    private float lineSpacingMultiplier;
    private WheelView.DividerType dividerType;
    private boolean areLunarCalendar = false;
    private ISelectTimeCallback iSelectTimeCallback;

    WheelTime(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }

    boolean areLunarMode() {
        return areLunarCalendar;
    }

    void setAreLunarMode(boolean areLunarCalendar) {
        this.areLunarCalendar = areLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    void setPicker(int year, final int month, int day, int h, int m, int s) {
        if (areLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    /**
     * 农历
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @param h     时
     * @param m     分
     * @param s     秒
     */
    private void setLunar(int year, final int month, int day, boolean areLeap, int h, int m, int s) {
        // 年
        wvYear = view.findViewById(R.id.year);
        // "年"显示数据
        wvYear.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getYears(startYear, endYear)));
        // 添文本
        wvYear.setLabel("");
        // 初始化显示数据
        wvYear.setCurrentItem(year - startYear);
        wvYear.setGravity(gravity);
        // 月
        wvMonth = view.findViewById(R.id.month);
        wvMonth.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getMonths(year)));
        wvMonth.setLabel("");
        wvMonth.setCurrentItem(month);
        wvMonth.setGravity(gravity);
        // 日
        wvDay = view.findViewById(R.id.day);
        // 判大小月及闰年否，来确定"日"数据
        if (ChinaDate.leapMonth(year) == 0) {
            wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wvDay.setLabel("");
        wvDay.setCurrentItem(day - 1);
        wvDay.setGravity(gravity);
        wvHours = view.findViewById(R.id.hour);
        wvHours.setWheelAdapter(new NumericWheelAdapter(0, 23));
        // 添文本
        /*wvHours.setLabel(context.getString(R.string.pickerview_hours));*/
        wvHours.setCurrentItem(h);
        wvHours.setGravity(gravity);
        wvMinutes = view.findViewById(R.id.min);
        wvMinutes.setWheelAdapter(new NumericWheelAdapter(0, 59));
        // 添文本
        /*wvMinutes.setLabel(context.getString(R.string.pickerview_minutes));*/
        wvMinutes.setCurrentItem(m);
        wvMinutes.setGravity(gravity);
        wvSeconds = view.findViewById(R.id.second);
        wvSeconds.setWheelAdapter(new NumericWheelAdapter(0, 59));
        // 添文本
        /*wvSeconds.setLabel(context.getString(R.string.pickerview_minutes));*/
        wvSeconds.setCurrentItem(m);
        wvSeconds.setGravity(gravity);
        // 添"年"监听
        wvYear.setOnItemSelectedListener(index -> {
            int yearNum = (index + startYear);
            // 判闰年否，来确定月和日选择
            wvMonth.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getMonths(yearNum)));
            if ((ChinaDate.leapMonth(yearNum) != 0) && (wvMonth.getCurrentItem() > (ChinaDate.leapMonth(yearNum) - 1))) {
                wvMonth.setCurrentItem(wvMonth.getCurrentItem() + 1);
            } else {
                wvMonth.setCurrentItem(wvMonth.getCurrentItem());
            }
            int maxItem;
            if ((ChinaDate.leapMonth(yearNum) != 0) && (wvMonth.getCurrentItem() > (ChinaDate.leapMonth(yearNum) - 1))) {
                if (wvMonth.getCurrentItem() == ChinaDate.leapMonth(yearNum) + 1) {
                    wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))));
                    maxItem = ChinaDate.leapDays(yearNum);
                } else {
                    wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem()))));
                    maxItem = ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem());
                }
            } else {
                wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem() + 1))));
                maxItem = ChinaDate.monthDays(yearNum, wvMonth.getCurrentItem() + 1);
            }
            if (wvDay.getCurrentItem() > (maxItem - 1)) {
                wvDay.setCurrentItem(maxItem - 1);
            }
            if (null != iSelectTimeCallback) {
                iSelectTimeCallback.onTimeSelectChanged();
            }
        });
        // 添"月"监听
        wvMonth.setOnItemSelectedListener(index -> {
            int yearNum = (wvYear.getCurrentItem() + startYear);
            int maxItem;
            if ((ChinaDate.leapMonth(yearNum) != 0) && (index > (ChinaDate.leapMonth(yearNum) - 1))) {
                if (wvMonth.getCurrentItem() == (ChinaDate.leapMonth(yearNum) + 1)) {
                    wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))));
                    maxItem = ChinaDate.leapDays(yearNum);
                } else {
                    wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, index))));
                    maxItem = ChinaDate.monthDays(yearNum, index);
                }
            } else {
                wvDay.setWheelAdapter(new ArrayWheelAdapter<>(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, index + 1))));
                maxItem = ChinaDate.monthDays(yearNum, index + 1);
            }
            if (wvDay.getCurrentItem() > (maxItem - 1)) {
                wvDay.setCurrentItem(maxItem - 1);
            }
            if (null != iSelectTimeCallback) {
                iSelectTimeCallback.onTimeSelectChanged();
            }
        });
        setChangedListener(wvDay);
        setChangedListener(wvHours);
        setChangedListener(wvMinutes);
        setChangedListener(wvSeconds);
        if (type.length != WidgetMagic.INT_SIX) {
            throw new RuntimeException("type[] length is not 6");
        }
        wvYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wvMonth.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wvDay.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wvHours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wvMinutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wvSeconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 公历
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @param h     时
     * @param m     分
     * @param s     秒
     */
    private void setSolar(int year, final int month, int day, int h, int m, int s) {
        // 添大小月月份并转 List(方便后判)
        String[] monthsBig = {"1", "3", "5", "7", "8", "10", "12"};
        String[] monthsLittle = {"4", "6", "9", "11"};
        final List<String> monthBigList = Arrays.asList(monthsBig);
        final List<String> monthLittleList = Arrays.asList(monthsLittle);
        currentYear = year;
        // 年
        wvYear = view.findViewById(R.id.year);
        // "年"显示数据
        wvYear.setWheelAdapter(new NumericWheelAdapter(startYear, endYear));
        // 初始化显示数据
        wvYear.setCurrentItem(year - startYear);
        wvYear.setGravity(gravity);
        // 月
        wvMonth = view.findViewById(R.id.month);
        if (startYear == endYear) {
            // 开始年等终止年
            wvMonth.setWheelAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wvMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            // 起始日期月份控制
            wvMonth.setWheelAdapter(new NumericWheelAdapter(startMonth, 12));
            wvMonth.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            // 终止日期月份控制
            wvMonth.setWheelAdapter(new NumericWheelAdapter(1, endMonth));
            wvMonth.setCurrentItem(month);
        } else {
            wvMonth.setWheelAdapter(new NumericWheelAdapter(1, 12));
            wvMonth.setCurrentItem(month);
        }
        wvMonth.setGravity(gravity);
        // 日
        wvDay = view.findViewById(R.id.day);
        if ((startYear == endYear) && (startMonth == endMonth)) {
            if (monthBigList.contains(String.valueOf(month + 1))) {
                if (endDay > WidgetMagic.INT_THIRTY_ONE) {
                    endDay = 31;
                }
                wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (monthLittleList.contains(String.valueOf(month + 1))) {
                if (endDay > WidgetMagic.INT_THIRTY) {
                    endDay = 30;
                }
                wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                boolean flagLeft = ((year % 4 == 0) && (year % 100 != 0));
                boolean flagRight = (year % 400 == 0);
                boolean flag = (flagLeft || flagRight);
                if (flag) {
                    if (endDay > WidgetMagic.INT_TWENTY_NINE) {
                        endDay = 29;
                    }
                } else {
                    if (endDay > WidgetMagic.INT_TWENTY_EIGHT) {
                        endDay = 28;
                    }
                }
                wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, endDay));
            }
            wvDay.setCurrentItem(day - startDay);
        } else if ((year == startYear) && (month + 1 == startMonth)) {
            // 起始日期天数控制
            if (monthBigList.contains(String.valueOf(month + 1))) {
                wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (monthLittleList.contains(String.valueOf(month + 1))) {
                wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                boolean flagLeft = ((year % 4 == 0) && (year % 100 != 0));
                boolean flagRight = (year % 400 == 0);
                boolean flag = (flagLeft || flagRight);
                if (flag) {
                    wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, 29));
                } else {
                    wvDay.setWheelAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wvDay.setCurrentItem(day - startDay);
        } else {
            if ((year == endYear) && ((month + 1) == endMonth)) {
                // 终止日期天数控制
                if (monthBigList.contains(String.valueOf(month + 1))) {
                    if (endDay > WidgetMagic.INT_THIRTY_ONE) {
                        endDay = 31;
                    }
                    wvDay.setWheelAdapter(new NumericWheelAdapter(1, endDay));
                } else if (monthLittleList.contains(String.valueOf(month + 1))) {
                    if (endDay > WidgetMagic.INT_THIRTY) {
                        endDay = 30;
                    }
                    wvDay.setWheelAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    // 闰年
                    boolean flagLeft = ((year % 4 == 0) && (year % 100 != 0));
                    boolean flagRight = (year % 400 == 0);
                    boolean flag = (flagLeft || flagRight);
                    if (flag) {
                        if (endDay > WidgetMagic.INT_TWENTY_NINE) {
                            endDay = 29;
                        }
                    } else {
                        if (endDay > WidgetMagic.INT_TWENTY_EIGHT) {
                            endDay = 28;
                        }
                    }
                    wvDay.setWheelAdapter(new NumericWheelAdapter(1, endDay));
                }
            } else {
                // 判大小月及闰年否，来确定"日"数据
                if (monthBigList.contains(String.valueOf(month + 1))) {
                    wvDay.setWheelAdapter(new NumericWheelAdapter(1, 31));
                } else if (monthLittleList.contains(String.valueOf(month + 1))) {
                    wvDay.setWheelAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    // 闰年
                    boolean flag = (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
                    if (flag) {
                        wvDay.setWheelAdapter(new NumericWheelAdapter(1, 29));
                    } else {
                        wvDay.setWheelAdapter(new NumericWheelAdapter(1, 28));
                    }
                }
            }
            wvDay.setCurrentItem(day - 1);
        }
        wvDay.setGravity(gravity);
        // 时
        wvHours = view.findViewById(R.id.hour);
        wvHours.setWheelAdapter(new NumericWheelAdapter(0, 23));
        wvHours.setCurrentItem(h);
        wvHours.setGravity(gravity);
        // 分
        wvMinutes = view.findViewById(R.id.min);
        wvMinutes.setWheelAdapter(new NumericWheelAdapter(0, 59));
        wvMinutes.setCurrentItem(m);
        wvMinutes.setGravity(gravity);
        // 秒
        wvSeconds = view.findViewById(R.id.second);
        wvSeconds.setWheelAdapter(new NumericWheelAdapter(0, 59));
        wvSeconds.setCurrentItem(s);
        wvSeconds.setGravity(gravity);
        // 添"年"监听
        wvYear.setOnItemSelectedListener(index -> {
            int yearNum = (index + startYear);
            currentYear = yearNum;
            // 记上次 Item 位
            int currentMonthItem = wvMonth.getCurrentItem();
            // 判大小月及闰年否，来确定"日"数据
            if (startYear == endYear) {
                // 新设月
                wvMonth.setWheelAdapter(new NumericWheelAdapter(startMonth, endMonth));
                if (currentMonthItem > (wvMonth.getWheelAdapter().getItemsCount() - 1)) {
                    currentMonthItem = (wvMonth.getWheelAdapter().getItemsCount() - 1);
                    wvMonth.setCurrentItem(currentMonthItem);
                }
                int monthNum = (currentMonthItem + startMonth);
                if (startMonth == endMonth) {
                    // 重设日
                    setReDay(yearNum, monthNum, startDay, endDay, monthBigList, monthLittleList);
                } else if (monthNum == startMonth) {
                    // 重设日
                    setReDay(yearNum, monthNum, startDay, 31, monthBigList, monthLittleList);
                } else if (monthNum == endMonth) {
                    setReDay(yearNum, monthNum, 1, endDay, monthBigList, monthLittleList);
                } else {
                    // 重设日
                    setReDay(yearNum, monthNum, 1, 31, monthBigList, monthLittleList);
                }
            } else if (yearNum == startYear) {
                // 等开始年
                // 重设月
                wvMonth.setWheelAdapter(new NumericWheelAdapter(startMonth, 12));
                if (currentMonthItem > (wvMonth.getWheelAdapter().getItemsCount() - 1)) {
                    currentMonthItem = (wvMonth.getWheelAdapter().getItemsCount() - 1);
                    wvMonth.setCurrentItem(currentMonthItem);
                }
                int month1 = (currentMonthItem + startMonth);
                if (month1 == startMonth) {
                    // 重设日
                    setReDay(yearNum, month1, startDay, 31, monthBigList, monthLittleList);
                } else {
                    // 重设日
                    setReDay(yearNum, month1, 1, 31, monthBigList, monthLittleList);
                }
            } else if (yearNum == endYear) {
                // 重设月
                wvMonth.setWheelAdapter(new NumericWheelAdapter(1, endMonth));
                if (currentMonthItem > (wvMonth.getWheelAdapter().getItemsCount() - 1)) {
                    currentMonthItem = (wvMonth.getWheelAdapter().getItemsCount() - 1);
                    wvMonth.setCurrentItem(currentMonthItem);
                }
                int monthNum = (currentMonthItem + 1);
                if (monthNum == endMonth) {
                    // 重设日
                    setReDay(yearNum, monthNum, 1, endDay, monthBigList, monthLittleList);
                } else {
                    // 重设日
                    setReDay(yearNum, monthNum, 1, 31, monthBigList, monthLittleList);
                }
            } else {
                // 重设月
                wvMonth.setWheelAdapter(new NumericWheelAdapter(1, 12));
                // 重设日
                setReDay(yearNum, wvMonth.getCurrentItem() + 1, 1, 31, monthBigList, monthLittleList);
            }
            if (null != iSelectTimeCallback) {
                iSelectTimeCallback.onTimeSelectChanged();
            }
        });
        // 添"月"监听
        wvMonth.setOnItemSelectedListener(index -> {
            int monthNum = (index + 1);
            if (startYear == endYear) {
                monthNum = (monthNum + startMonth - 1);
                if (startMonth == endMonth) {
                    // 重设日
                    setReDay(currentYear, monthNum, startDay, endDay, monthBigList, monthLittleList);
                } else if (startMonth == monthNum) {
                    // 重设日
                    setReDay(currentYear, monthNum, startDay, 31, monthBigList, monthLittleList);
                } else if (endMonth == monthNum) {
                    setReDay(currentYear, monthNum, 1, endDay, monthBigList, monthLittleList);
                } else {
                    setReDay(currentYear, monthNum, 1, 31, monthBigList, monthLittleList);
                }
            } else if (currentYear == startYear) {
                monthNum = (monthNum + startMonth - 1);
                if (monthNum == startMonth) {
                    // 重设日
                    setReDay(currentYear, monthNum, startDay, 31, monthBigList, monthLittleList);
                } else {
                    // 重设日
                    setReDay(currentYear, monthNum, 1, 31, monthBigList, monthLittleList);
                }
            } else if (currentYear == endYear) {
                if (monthNum == endMonth) {
                    // 重设日
                    setReDay(currentYear, wvMonth.getCurrentItem() + 1, 1, endDay, monthBigList, monthLittleList);
                } else {
                    setReDay(currentYear, wvMonth.getCurrentItem() + 1, 1, 31, monthBigList, monthLittleList);
                }
            } else {
                // 重设日
                setReDay(currentYear, monthNum, 1, 31, monthBigList, monthLittleList);
            }
            if (null != iSelectTimeCallback) {
                iSelectTimeCallback.onTimeSelectChanged();
            }
        });
        setChangedListener(wvDay);
        setChangedListener(wvHours);
        setChangedListener(wvMinutes);
        setChangedListener(wvSeconds);
        if (type.length != WidgetMagic.INT_SIX) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wvYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wvMonth.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wvDay.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wvHours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wvMinutes.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wvSeconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    private void setChangedListener(WheelView wheelView) {
        if (null != iSelectTimeCallback) {
            wheelView.setOnItemSelectedListener(index -> iSelectTimeCallback.onTimeSelectChanged());
        }
    }

    private void setReDay(int yearNum, int monthNum, int dStart, int dEnd, @NonNull List<String> monthBigList, List<String> monthLittleList) {
        int currentItem = wvDay.getCurrentItem();
        // int maxItem;
        if (monthBigList.contains(String.valueOf(monthNum))) {
            if (dEnd > WidgetMagic.INT_THIRTY_ONE) {
                dEnd = 31;
            }
            wvDay.setWheelAdapter(new NumericWheelAdapter(dStart, dEnd));
            // maxItem = endD;
        } else if (monthLittleList.contains(String.valueOf(monthNum))) {
            if (dEnd > WidgetMagic.INT_THIRTY) {
                dEnd = 30;
            }
            wvDay.setWheelAdapter(new NumericWheelAdapter(dStart, dEnd));
            // maxItem = endD;
        } else {
            boolean flag = (((yearNum % 4 == 0) && (yearNum % 100 != 0)) || (yearNum % 400 == 0));
            if (flag) {
                if (dEnd > WidgetMagic.INT_TWENTY_NINE) {
                    dEnd = 29;
                }
                // maxItem = endD;
            } else {
                if (dEnd > WidgetMagic.INT_TWENTY_EIGHT) {
                    dEnd = 28;
                }
                // maxItem = endD;
            }
            wvDay.setWheelAdapter(new NumericWheelAdapter(dStart, dEnd));
        }
        if (currentItem > (wvDay.getWheelAdapter().getItemsCount() - 1)) {
            currentItem = (wvDay.getWheelAdapter().getItemsCount() - 1);
            wvDay.setCurrentItem(currentItem);
        }
    }

    private void setContentTextSize() {
        wvDay.setTextSize(textSize);
        wvMonth.setTextSize(textSize);
        wvYear.setTextSize(textSize);
        wvHours.setTextSize(textSize);
        wvMinutes.setTextSize(textSize);
        wvSeconds.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wvDay.setTextColorOut(textColorOut);
        wvMonth.setTextColorOut(textColorOut);
        wvYear.setTextColorOut(textColorOut);
        wvHours.setTextColorOut(textColorOut);
        wvMinutes.setTextColorOut(textColorOut);
        wvSeconds.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wvDay.setTextColorCenter(textColorCenter);
        wvMonth.setTextColorCenter(textColorCenter);
        wvYear.setTextColorCenter(textColorCenter);
        wvHours.setTextColorCenter(textColorCenter);
        wvMinutes.setTextColorCenter(textColorCenter);
        wvSeconds.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wvDay.setDividerColor(dividerColor);
        wvMonth.setDividerColor(dividerColor);
        wvYear.setDividerColor(dividerColor);
        wvHours.setDividerColor(dividerColor);
        wvMinutes.setDividerColor(dividerColor);
        wvSeconds.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wvDay.setDividerType(dividerType);
        wvMonth.setDividerType(dividerType);
        wvYear.setDividerType(dividerType);
        wvHours.setDividerType(dividerType);
        wvMinutes.setDividerType(dividerType);
        wvSeconds.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wvDay.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvMonth.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvYear.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvHours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvMinutes.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvSeconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    void setLabels(String labelYear, String labelMonth, String labelDay, String labelHour, String labelMinute, String labelSecond) {
        if (areLunarCalendar) {
            return;
        }
        if (null != labelYear) {
            wvYear.setLabel(labelYear);
        } else {
            wvYear.setLabel(view.getContext().getString(R.string.year));
        }
        if (null != labelMonth) {
            wvMonth.setLabel(labelMonth);
        } else {
            wvMonth.setLabel(view.getContext().getString(R.string.month));
        }
        if (null != labelDay) {
            wvDay.setLabel(labelDay);
        } else {
            wvDay.setLabel(view.getContext().getString(R.string.day));
        }
        if (null != labelHour) {
            wvHours.setLabel(labelHour);
        } else {
            wvHours.setLabel(view.getContext().getString(R.string.hours));
        }
        if (null != labelMinute) {
            wvMinutes.setLabel(labelMinute);
        } else {
            wvMinutes.setLabel(view.getContext().getString(R.string.minutes));
        }
        if (null != labelSecond) {
            wvSeconds.setLabel(labelSecond);
        } else {
            wvSeconds.setLabel(view.getContext().getString(R.string.seconds));
        }
    }

    void setxOffsetOfText(int xOffsetYear, int xOffsetMonth, int xOffsetDay, int xOffsetHour, int xOffsetMinute, int xOffsetSecond) {
        wvDay.setxOffsetOfText(xOffsetYear);
        wvMonth.setxOffsetOfText(xOffsetMonth);
        wvYear.setxOffsetOfText(xOffsetDay);
        wvHours.setxOffsetOfText(xOffsetHour);
        wvMinutes.setxOffsetOfText(xOffsetMinute);
        wvSeconds.setxOffsetOfText(xOffsetSecond);
    }

    /**
     * 循环滚动
     *
     * @param cyclic 循环否
     */
    void setCyclic(boolean cyclic) {
        wvYear.setCyclic(cyclic);
        wvMonth.setCyclic(cyclic);
        wvDay.setCyclic(cyclic);
        wvHours.setCyclic(cyclic);
        wvMinutes.setCyclic(cyclic);
        wvSeconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (areLunarCalendar) {
            // 农历返对应公历时间
            return getLunarTime();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (currentYear == startYear) {
            if ((wvMonth.getCurrentItem() + startMonth) == startMonth) {
                stringBuilder.append((wvYear.getCurrentItem() + startYear)).append("-")
                        .append((wvMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wvDay.getCurrentItem() + startDay)).append(" ")
                        .append(wvHours.getCurrentItem()).append(":")
                        .append(wvMinutes.getCurrentItem()).append(":")
                        .append(wvSeconds.getCurrentItem());
            } else {
                stringBuilder.append((wvYear.getCurrentItem() + startYear)).append("-")
                        .append((wvMonth.getCurrentItem() + startMonth)).append("-")
                        .append((wvDay.getCurrentItem() + 1)).append(" ")
                        .append(wvHours.getCurrentItem()).append(":")
                        .append(wvMinutes.getCurrentItem()).append(":")
                        .append(wvSeconds.getCurrentItem());
            }
        } else {
            stringBuilder.append((wvYear.getCurrentItem() + startYear)).append("-")
                    .append((wvMonth.getCurrentItem() + 1)).append("-")
                    .append((wvDay.getCurrentItem() + 1)).append(" ")
                    .append(wvHours.getCurrentItem()).append(":")
                    .append(wvMinutes.getCurrentItem()).append(":")
                    .append(wvSeconds.getCurrentItem());
        }
        return stringBuilder.toString();
    }

    /**
     * 农历返对应公历时间
     *
     * @return 公历时间
     */
    @NonNull
    private String getLunarTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int year = (wvYear.getCurrentItem() + startYear);
        int month;
        boolean areLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = (wvMonth.getCurrentItem() + 1);
        } else {
            if (((wvMonth.getCurrentItem() + 1) - ChinaDate.leapMonth(year)) <= 0) {
                month = (wvMonth.getCurrentItem() + 1);
            } else if (((wvMonth.getCurrentItem() + 1) - ChinaDate.leapMonth(year)) == 1) {
                month = wvMonth.getCurrentItem();
                areLeapMonth = true;
            } else {
                month = wvMonth.getCurrentItem();
            }
        }
        int day = (wvDay.getCurrentItem() + 1);
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, areLeapMonth);
        stringBuilder.append(solar[0]).append("-")
                .append(solar[1]).append("-")
                .append(solar[2]).append(" ")
                .append(wvHours.getCurrentItem()).append(":")
                .append(wvMinutes.getCurrentItem()).append(":")
                .append(wvSeconds.getCurrentItem());
        return stringBuilder.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    void setRangDate(Calendar startDate, Calendar endDate) {
        if ((null == startDate) && (null != endDate)) {
            int year = endDate.get(Calendar.YEAR);
            int month = (endDate.get(Calendar.MONTH) + 1);
            int day = endDate.get(Calendar.DAY_OF_MONTH);
            if (year > startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
            } else if (year == startYear) {
                if (month > startMonth) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;
                } else if (month == startMonth) {
                    if (day > startDay) {
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;
                    }
                }
            }
        } else if ((null != startDate) && (null == endDate)) {
            int year = startDate.get(Calendar.YEAR);
            int month = (startDate.get(Calendar.MONTH) + 1);
            int day = startDate.get(Calendar.DAY_OF_MONTH);
            if (year < endYear) {
                this.startMonth = month;
                this.startDay = day;
                this.startYear = year;
            } else if (year == endYear) {
                if (month < endMonth) {
                    this.startMonth = month;
                    this.startDay = day;
                    this.startYear = year;
                } else if (month == endMonth) {
                    if (day < endDay) {
                        this.startMonth = month;
                        this.startDay = day;
                        this.startYear = year;
                    }
                }
            }
        } else if (null != startDate) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * 间距倍数（1.0 - 4.0f）
     *
     * @param lineSpacingMultiplier 间距倍数
     */
    void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 分割线色
     *
     * @param dividerColor 分割线色
     */
    void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 分割线类型
     *
     * @param dividerType 分割线类型
     */
    void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 分割线间文本色
     *
     * @param textColorCenter 分割线间文本色
     */
    void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 分割线外文本色
     *
     * @param textColorOut 分割线外文本色
     */
    void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * 仅显中间选中项 Label
     *
     * @param areCenterLabel 仅显中间选中项 Label
     */
    void setAreCenterLabel(boolean areCenterLabel) {
        this.wvDay.setAreCenterLabel(areCenterLabel);
        this.wvMonth.setAreCenterLabel(areCenterLabel);
        this.wvYear.setAreCenterLabel(areCenterLabel);
        this.wvHours.setAreCenterLabel(areCenterLabel);
        this.wvMinutes.setAreCenterLabel(areCenterLabel);
        this.wvSeconds.setAreCenterLabel(areCenterLabel);
    }

    void setSelectChangeCallback(ISelectTimeCallback iSelectTimeCallback) {
        this.iSelectTimeCallback = iSelectTimeCallback;
    }
}
