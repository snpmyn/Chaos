package com.chaos.widget.choose.pickerview.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import value.WidgetMagic;

/**
 * @decs: ChinaDate
 * @author: 郑少鹏
 * @date: 2018/4/3 17:14
 */
public class ChinaDate {
    private final static String[] N_STRING = new String[]{"", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
    /**
     * lunarInfo 数组值计算原理
     * <p>
     * 0x 表十六进制，后五位是十六进制数。
     * 例: 1980 年数据 0x095b0。
     * <p>
     * 二进制: 0000 1001 0101 1011 0000。
     * 1 - 4: 表当年有无闰年，有则为闰月月份；无为 0。
     * 5 - 16: 为除闰月外正常月份是大 / 小月，1 为 30 天，0 为 29 天。
     * <p>
     * 注意: 1 到 12 月对应 16 到第 5 位。
     * 17 - 20: 表闰月是大 / 小月，仅当存在闰月情况下有意义。
     */
    final private static long[] LUNAR_INFO = new long[]{
            //1900-1909
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            //1910-1919
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
            //1920-1929
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            //1930-1939
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            //1940-1949
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
            //1950-1959
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5b0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0,
            //1960-1969
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            //1970-1979
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6,
            //1980-1989
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
            //1990-1999
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
            //2000-2009
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
            //2010-2019
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            //2020-2029
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            //2030-2039
            0x05aa0, 0x076a3, 0x096d0, 0x04afb, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            //2040-2049
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
            //2050-2059
            0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
            //2060-2069
            0x0a2e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
            //2070-2079
            0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
            //2080-2089
            0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x054e4, 0x0d160,
            //2090-2099
            0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252,
            //2100
            0x0d520};
    final private static int[] YEAR_20 = new int[]{1, 4, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1};
    final private static int[] YEAR_19 = new int[]{0, 3, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0};
    final private static int[] YEAR_2000 = new int[]{0, 3, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1};
    private final static String[] GAN = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private final static String[] ZHI = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private final static String[] ANIMALS = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    /**
     * 传回农历
     *
     * @param y 年总天数
     * @return 农历
     */
    private static int lYearDays(int y) {
        int i, sum = 348;
        for (i = WidgetMagic.INT_ZERO_X_EIGHT_ZERO_ZERO_ZERO; i > WidgetMagic.INT_ZERO_X_EIGHT; i >>= 1) {
            if ((LUNAR_INFO[y - 1900] & i) != 0) {
                sum += 1;
            }
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历
     *
     * @param y 年闰月天数
     * @return 农历
     */
    public static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((LUNAR_INFO[y - WidgetMagic.INT_ONE_THOUSAND_NINE_HUNDRED] & WidgetMagic.INT_ZERO_X_ONE_ZERO_ZERO_ZERO_ZERO) != 0) {
                return 30;
            } else {
                return 29;
            }
        } else {
            return 0;
        }
    }

    /**
     * 传回农历
     *
     * @param y 年闰哪月（1 - 12），没闰传回 0
     * @return 农历
     */
    public static int leapMonth(int y) {
        return (int) (LUNAR_INFO[y - 1900] & 0xf);
    }

    /**
     * 传回农历 y
     *
     * @param y y 年 m 月总天数
     * @param m y 年 m 月总天数
     * @return 农历
     */
    public static int monthDays(int y, int m) {
        if ((LUNAR_INFO[y - WidgetMagic.INT_ONE_THOUSAND_NINE_HUNDRED] & (WidgetMagic.INT_ZERO_X_ONE_ZERO_ZERO_ZERO_ZERO >> m)) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 传回农历
     *
     * @param y 年生肖
     * @return 农历
     */
    private static String animalsYear(int y) {
        return ANIMALS[(y - 4) % 12];
    }

    /**
     * 传入
     *
     * @param num 月日 offset，传回干支，0 甲子
     * @return 干支
     */
    @NonNull
    @Contract(pure = true)
    private static String circle(int num) {
        return (GAN[num % 10] + ZHI[num % 12]);
    }

    /**
     * 传入 offset 传回干支
     *
     * @param y 0 甲子
     * @return 干支
     */
    @NonNull
    @Contract(pure = true)
    private static String cyclical(int y) {
        int num = (y - 1900 + 36);
        return (circle(num));
    }

    /**
     * 传回 y 年 m 月 d 日对应农历（.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6）
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @return y 年 m 月 d 日对应农历
     */
    @NonNull
    private static long[] calElement(int y, int m, int d) {
        long[] longDate = new long[7];
        int i, temp = 0, leap;
        Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();
        Date objDate = new GregorianCalendar(y, m - 1, d).getTime();
        long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
        longDate[5] = (offset + 40);
        longDate[4] = 14;
        for (i = WidgetMagic.INT_ONE_THOUSAND_NINE_HUNDRED; (i < WidgetMagic.INT_TWO_THOUSAND_FIFTY) && (offset > 0); i++) {
            temp = lYearDays(i);
            offset -= temp;
            longDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            longDate[4] -= 12;
        }
        longDate[0] = i;
        longDate[3] = (i - 1864);
        // 闰哪月
        leap = leapMonth(i);
        longDate[6] = 0;
        for (i = 1; (i < WidgetMagic.INT_THIRTEEN) && (offset > 0); i++) {
            // 闰月
            if ((leap > 0) && (i == (leap + 1)) && (longDate[6] == 0)) {
                --i;
                longDate[6] = 1;
                temp = leapDays((int) longDate[0]);
            } else {
                temp = monthDays((int) longDate[0], i);
            }
            // 解除闰月
            if ((longDate[6] == 1) && (i == (leap + 1))) {
                longDate[6] = 0;
            }
            offset -= temp;
            if (longDate[6] == 0) {
                longDate[4]++;
            }
        }
        if ((offset == 0) && (leap > 0) && (i == (leap + 1))) {
            if (longDate[WidgetMagic.INT_SIX] == 1) {
                longDate[6] = 0;
            } else {
                longDate[6] = 1;
                --i;
                --longDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --longDate[4];
        }
        longDate[1] = i;
        longDate[2] = (offset + 1);
        return longDate;
    }

    private static String getChinaDate(int day) {
        String a = "";
        if (day == WidgetMagic.INT_TEN) {
            return "初十";
        }
        if (day == WidgetMagic.INT_TWENTY) {
            return "二十";
        }
        if (day == WidgetMagic.INT_THIRTY) {
            return "三十";
        }
        int two = (day) / 10;
        if (two == 0) {
            a = "初";
        }
        if (two == 1) {
            a = "十";
        }
        if (two == WidgetMagic.INT_TWO) {
            a = "廿";
        }
        if (two == WidgetMagic.INT_FOUR) {
            a = "三";
        }
        int one = day % 10;
        switch (one) {
            case 1:
                a += "一";
                break;
            case 2:
                a += "二";
                break;
            case 3:
                a += "三";
                break;
            case 4:
                a += "四";
                break;
            case 5:
                a += "五";
                break;
            case 6:
                a += "六";
                break;
            case 7:
                a += "七";
                break;
            case 8:
                a += "八";
                break;
            case 9:
                a += "九";
                break;
            default:
                break;
        }
        return a;
    }

    @NonNull
    public static String today() {
        Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        long[] l = calElement(year, month, date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日 EEEEE", Locale.US);
        return simpleDateFormat.format(today.getTime()) +
                " 农历" +
                cyclical(year) +
                '(' +
                animalsYear(year) +
                ")年" +
                N_STRING[(int) l[1]] +
                "月" +
                getChinaDate((int) (l[2]));
    }

    @NonNull
    public static String oneDay(int year, int month, int day) {
        long[] l = calElement(year, month, day);
        return " 农历" +
                cyclical(year) +
                '(' +
                animalsYear(year) +
                ")年" +
                N_STRING[(int) l[1]] +
                "月" +
                getChinaDate((int) (l[2]));
    }

    /**
     * 甲子年
     *
     * @param lunarYear 农历年份
     * @return String of GanZhi: 甲子年
     * 甲乙丙丁戊己庚辛壬癸
     * 子丑寅卯辰巳无为申酉戌亥
     */
    @NonNull
    @Contract(pure = true)
    private static String getLunarYearText(int lunarYear) {
        return (GAN[(lunarYear - 4) % 10] + ZHI[(lunarYear - 4) % 12] + "年");
    }

    @NonNull
    public static ArrayList<String> getYears(int startYear, int endYear) {
        ArrayList<String> years = new ArrayList<>();
        for (int i = startYear; i < endYear; i++) {
            years.add(String.format(Locale.US, "%s(%d)", getLunarYearText(i), i));
        }
        return years;
    }

    /**
     * year 年所有月
     *
     * @param year 年
     * @return 月列表
     */
    @NonNull
    public static ArrayList<String> getMonths(int year) {
        ArrayList<String> baseMonths = new ArrayList<>();
        for (int i = 1; i < N_STRING.length; i++) {
            baseMonths.add(N_STRING[i] + "月");
        }
        if (leapMonth(year) != 0) {
            baseMonths.add(leapMonth(year), "闰" + N_STRING[leapMonth(year)] + "月");
        }
        return baseMonths;
    }

    /**
     * 每月农历显名
     *
     * @param maxDay 天
     * @return 名称列表
     */
    @NonNull
    public static ArrayList<String> getLunarDays(int maxDay) {
        ArrayList<String> days = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {
            days.add(getChinaDate(i));
        }
        return days;
    }

    /**
     * 传出农历 .year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
     *
     * @param y 年
     * @param m 月
     * @return 传出农历
     */
    @NonNull
    private long[] lunar(int y, int m) {
        long[] longDate = new long[7];
        int i, temp = 0, leap;
        Date baseDate = new GregorianCalendar(1900 + 1900, 1, 31).getTime();
        Date objDate = new GregorianCalendar(y + 1900, m, 1).getTime();
        long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
        if (y < WidgetMagic.INT_TWO_THOUSAND) {
            offset += YEAR_19[m - 1];
        }
        if (y > WidgetMagic.INT_TWO_THOUSAND) {
            offset += YEAR_20[m - 1];
        }
        if (y == WidgetMagic.INT_TWO_THOUSAND) {
            offset += YEAR_2000[m - 1];
        }
        longDate[5] = (offset + 40);
        longDate[4] = 14;
        for (i = WidgetMagic.INT_ONE_THOUSAND_NINE_HUNDRED; (i < WidgetMagic.INT_TWO_THOUSAND_FIFTY) && (offset > 0); i++) {
            temp = lYearDays(i);
            offset -= temp;
            longDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            longDate[4] -= 12;
        }
        longDate[0] = i;
        longDate[3] = (i - 1864);
        // 闰哪月
        leap = leapMonth(i);
        longDate[6] = 0;
        for (i = 1; (i < WidgetMagic.INT_THIRTEEN) && (offset > 0); i++) {
            // 闰月
            if ((leap > 0) && (i == (leap + 1)) && (longDate[6] == 0)) {
                --i;
                longDate[6] = 1;
                temp = leapDays((int) longDate[0]);
            } else {
                temp = monthDays((int) longDate[0], i);
            }
            // 解除闰月
            if ((longDate[6] == 1) && (i == (leap + 1))) {
                longDate[6] = 0;
            }
            offset -= temp;
            if (longDate[6] == 0) {
                longDate[4]++;
            }
        }
        if ((offset == 0) && (leap > 0) && (i == (leap + 1))) {
            if (longDate[WidgetMagic.INT_SIX] == 1) {
                longDate[6] = 0;
            } else {
                longDate[6] = 1;
                --i;
                --longDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --longDate[4];
        }
        longDate[1] = i;
        longDate[2] = (offset + 1);
        return longDate;
    }
}