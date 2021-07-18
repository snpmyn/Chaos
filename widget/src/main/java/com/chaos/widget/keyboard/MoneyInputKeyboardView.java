package com.chaos.widget.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chaos.util.java.data.StringUtils;
import com.chaos.util.java.log.LogUtils;
import com.chaos.widget.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import value.WidgetMagic;

/**
 * Created on 2021/7/12
 *
 * @author zsp
 * @desc 金额输入键盘视图
 */
public class MoneyInputKeyboardView implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private final Context context;
    protected View rootView;
    protected TextView moneyInputKeyboardViewTvOne;
    protected TextView moneyInputKeyboardViewTvTwo;
    protected TextView moneyInputKeyboardViewTvThree;
    protected TextView moneyInputKeyboardViewTvFour;
    protected TextView moneyInputKeyboardViewTvFive;
    protected TextView moneyInputKeyboardViewTvSix;
    protected TextView moneyInputKeyboardViewTvSeven;
    protected TextView moneyInputKeyboardViewTvEight;
    protected TextView moneyInputKeyboardViewTvNine;
    protected TextView moneyInputKeyboardViewTvDot;
    protected TextView moneyInputKeyboardViewTvZero;
    protected TextView moneyInputKeyboardViewTvDoubleZero;
    protected LinearLayout moneyInputKeyboardViewLlDelete;
    protected LinearLayout moneyInputKeyboardViewLlComplete;
    private long currentTime;
    private int maxLeftNumber = 13;
    private int maxRightNumber = 2;
    private String currency = "001";
    private boolean filterZero = false;
    private StringBuffer stringBuffer = new StringBuffer();
    private OnKeyboardListener onKeyboardListener;

    public interface OnKeyboardListener {
        /**
         * 位数变化
         *
         * @param number 位数
         */
        void onNumberChange(String number);

        /**
         * 键盘取消
         *
         * @param formatNumber 格式化金额
         */
        void onKeyboardCancel(String formatNumber);

        /**
         * 键盘完成
         *
         * @param formatNumber 格式化金额
         */
        void onKeyboardComplete(String formatNumber);
    }

    public MoneyInputKeyboardView(Context context) {
        this.context = context;
        stepUi();
        setListener();
    }

    /**
     * 初始化视图
     */
    @SuppressLint("InflateParams")
    private void stepUi() {
        rootView = LayoutInflater.from(context).inflate(R.layout.money_input_keyboard_view, null);
        moneyInputKeyboardViewTvOne = rootView.findViewById(R.id.moneyInputKeyboardViewTvOne);
        moneyInputKeyboardViewTvTwo = rootView.findViewById(R.id.moneyInputKeyboardViewTvTwo);
        moneyInputKeyboardViewTvThree = rootView.findViewById(R.id.moneyInputKeyboardViewTvThree);
        moneyInputKeyboardViewTvFour = rootView.findViewById(R.id.moneyInputKeyboardViewTvFour);
        moneyInputKeyboardViewTvFive = rootView.findViewById(R.id.moneyInputKeyboardViewTvFive);
        moneyInputKeyboardViewTvSix = rootView.findViewById(R.id.moneyInputKeyboardViewTvSix);
        moneyInputKeyboardViewTvSeven = rootView.findViewById(R.id.moneyInputKeyboardViewTvSeven);
        moneyInputKeyboardViewTvEight = rootView.findViewById(R.id.moneyInputKeyboardViewTvEight);
        moneyInputKeyboardViewTvNine = rootView.findViewById(R.id.moneyInputKeyboardViewTvNine);
        moneyInputKeyboardViewTvDot = rootView.findViewById(R.id.moneyInputKeyboardViewTvDot);
        moneyInputKeyboardViewTvZero = rootView.findViewById(R.id.moneyInputKeyboardViewTvZero);
        moneyInputKeyboardViewTvDoubleZero = rootView.findViewById(R.id.moneyInputKeyboardViewTvDoubleZero);
        moneyInputKeyboardViewLlDelete = rootView.findViewById(R.id.moneyInputKeyboardViewLlDelete);
        moneyInputKeyboardViewLlComplete = rootView.findViewById(R.id.moneyInputKeyboardViewLlComplete);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        moneyInputKeyboardViewTvOne.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvTwo.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvThree.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvFour.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvFive.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvSix.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvSeven.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvEight.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvNine.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvDot.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvZero.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewTvDoubleZero.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewLlDelete.setOnClickListener(MoneyInputKeyboardView.this);
        moneyInputKeyboardViewLlDelete.setOnLongClickListener(v -> {
            parseActionType(KeyboardEnum.LONG_CLICK_TO_DELETE);
            return false;
        });
        moneyInputKeyboardViewLlComplete.setOnClickListener(MoneyInputKeyboardView.this);
    }

    @Override
    public void onClick(View view) {
        if ((System.currentTimeMillis() - currentTime) < WidgetMagic.INT_ONE_HUNDRED_FIFTY) {
            return;
        }
        currentTime = System.currentTimeMillis();
        if (view.getId() == R.id.moneyInputKeyboardViewTvOne) {
            parseActionType(KeyboardEnum.ONE);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvTwo) {
            parseActionType(KeyboardEnum.TWO);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvThree) {
            parseActionType(KeyboardEnum.THREE);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvFour) {
            parseActionType(KeyboardEnum.FOUR);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvFive) {
            parseActionType(KeyboardEnum.FIVE);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvSix) {
            parseActionType(KeyboardEnum.SIX);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvSeven) {
            parseActionType(KeyboardEnum.SEVEN);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvEight) {
            parseActionType(KeyboardEnum.EIGHT);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvNine) {
            parseActionType(KeyboardEnum.NINE);
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvDot) {
            if (maxRightNumber <= 0) {
                return;
            }
            String text = stringBuffer.toString();
            if (text.contains(WidgetMagic.STRING_DOT)) {
                return;
            }
            if (TextUtils.isEmpty(text)) {
                stringBuffer.append("0.");
                if (onKeyboardListener != null) {
                    onKeyboardListener.onNumberChange(stringBuffer.toString());
                }
            } else {
                parseActionType(KeyboardEnum.DOT);
            }
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvZero) {
            String string = stringBuffer.toString();
            if ((string.length() == 1) && string.equals(WidgetMagic.STRING_ZERO)) {
                LogUtils.d(TAG, "0");
            } else {
                parseActionType(KeyboardEnum.ZERO);
            }
        } else if (view.getId() == R.id.moneyInputKeyboardViewTvDoubleZero) {
            parseActionType(KeyboardEnum.DOUBLE_ZERO);
        } else if (view.getId() == R.id.moneyInputKeyboardViewLlDelete) {
            parseActionType(KeyboardEnum.DELETE);
        } else if (view.getId() == R.id.moneyInputKeyboardViewLlComplete) {
            parseActionType(KeyboardEnum.COMPLETE);
        }
    }

    /**
     * 设金额左边最大位数
     */
    public void setMoneyLeftMaxNumber(int maxLeftNumber) {
        this.maxLeftNumber = maxLeftNumber;
    }

    /**
     * 设金额右边最大位数
     */
    public void setMoneyRightMaxNumber(int maxRightNumber) {
        this.maxRightNumber = maxRightNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (StringUtils.isEmptyOrNull(currency)) {
            return;
        }
        this.currency = currency;
        maxRightNumber = 2;
    }

    /**
     * 长度合法
     *
     * @param content 内容
     * @return 长度合法否
     */
    private boolean legalLength(@NonNull String content) {
        boolean legal = true;
        String left;
        String right = "";
        int index = content.indexOf('.');
        if (index > 0) {
            left = content.substring(0, content.indexOf('.'));
            if (index < content.length() - 1) {
                right = content.substring(content.indexOf('.') + 1);
            }
        } else {
            left = content;
        }
        if (left.length() > maxLeftNumber) {
            char point = content.charAt(maxLeftNumber);
            if (point != WidgetMagic.CHAR_SMALL_DOT) {
                legal = false;
            } else if (right.length() > maxRightNumber) {
                legal = false;
            }
        } else if (right.length() > maxRightNumber) {
            legal = false;
        }
        return legal;
    }

    public OnKeyboardListener getOnKeyboardListener() {
        return onKeyboardListener;
    }

    public void setOnKeyboardListener(OnKeyboardListener onKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener;
    }

    public String getFormatNumber() {
        String str = stringBuffer.toString();
        if (filterZero) {
            if (StringUtils.isEmpty(str)) {
                return "";
            }
            return new BigDecimal(str).stripTrailingZeros().toPlainString();
        }
        if (!TextUtils.isEmpty(str)) {
            str = new BigDecimal(str).divide(new BigDecimal(100)).toPlainString();
        }
        return formatMoney(str, maxLeftNumber, maxRightNumber, filterZero);
    }

    public String getFormatAccount() {
        String str = stringBuffer.toString();
        return StringUtils.format(str);
    }

    /**
     * 获取输入金额
     *
     * @return 输入金额
     */
    public String getInputMoney() {
        String str;
        str = (filterZero ? StringUtils.subZeroAndDot(stringBuffer.toString()) : formatMoneyTwo(stringBuffer.toString(), maxLeftNumber, maxRightNumber, filterZero));
        clearBuffer();
        stringBuffer.append(StringUtils.replace(str, ",", ""));
        return stringBuffer.toString();
    }

    /**
     * 获取 12 位输入金额
     * <p>
     * 转为单位分
     *
     * @return 12 位输入金额
     */
    public String getInputMoneyWithTwelveLength() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(StringUtils.subZeroAndDot(stringBuffer.toString()));
        clearBuffer();
        stringBuffer.append(StringUtils.replace(stringBuilder.toString(), ",", ""));
        stringBuilder = new StringBuilder(stringBuffer.toString());
        while (stringBuilder.length() < WidgetMagic.INT_TWELVE) {
            stringBuilder.insert(0, "0");
        }
        return stringBuilder.toString();
    }

    public String getInputAccount() {
        return stringBuffer.toString();
    }

    /**
     * 设置输入金额
     *
     * @param money 金额
     */
    public void setInputMoney(String money) {
        clearBuffer();
        stringBuffer.append(money);
        onKeyboardListener.onNumberChange(getFormatNumber());
    }

    public void setInputAccount(String account) {
        clearBuffer();
        stringBuffer.append(account);
        onKeyboardListener.onNumberChange(account);
    }

    public View getKeyBoardView() {
        return rootView;
    }

    /**
     * 解析动作类型
     *
     * @param keyboardEnum 键盘枚举
     */
    private void parseActionType(@NonNull KeyboardEnum keyboardEnum) {
        if (keyboardEnum.getType() == KeyboardEnum.ActionEnum.ADD) {
            String num = (stringBuffer.toString() + keyboardEnum.getValue());
            if (Double.parseDouble(num) == 0) {
                // 0 不占位，避输多 0 致无法输入。
                num = "0";
            }
            if (legalLength(num)) {
                if (!WidgetMagic.STRING_ZERO.equals(num)) {
                    stringBuffer.append(keyboardEnum.getValue());
                }
                if (null != onKeyboardListener) {
                    onKeyboardListener.onNumberChange(stringBuffer.toString());
                }
            }
        } else if (keyboardEnum.getType() == KeyboardEnum.ActionEnum.DELETE) {
            if (stringBuffer.length() > 0) {
                stringBuffer = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
                if (null != onKeyboardListener) {
                    onKeyboardListener.onNumberChange(stringBuffer.toString());
                }
            }
        } else if (keyboardEnum.getType() == KeyboardEnum.ActionEnum.LONG_CLICK_TO_DELETE) {
            if (null != onKeyboardListener) {
                clear();
            }
        } else if (keyboardEnum.getType() == KeyboardEnum.ActionEnum.COMPLETE) {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyboardComplete(stringBuffer.toString());
            }
        }
    }

    public void setFilterZero(boolean filterZero) {
        this.filterZero = filterZero;
    }

    public void clear() {
        clearBuffer();
        onKeyboardListener.onNumberChange(stringBuffer.toString());
    }

    private void clearBuffer() {
        stringBuffer.delete(0, stringBuffer.length());
    }

    /**
     * 格式化金额
     *
     * @param money            金额
     * @param maxIntegers      最大整数位数
     * @param maxDecimalDigits 最大小数位数
     * @param filterZero       过滤零
     *                         true 格式化后去除金额尾 0；false 添尾 0 至小数最大位数。
     * @return 格式化后金额
     */
    private String formatMoney(String money, int maxIntegers, int maxDecimalDigits, boolean filterZero) {
        if (StringUtils.isEmpty(money)) {
            return "";
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(money);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            numberFormat.setMaximumIntegerDigits(maxIntegers);
            numberFormat.setMaximumFractionDigits(maxDecimalDigits);
            if (!filterZero) {
                numberFormat.setMinimumFractionDigits(maxDecimalDigits);
            }
            return numberFormat.format(bigDecimal);
        } catch (NumberFormatException exp) {
            return money;
        }
    }

    /**
     * 格式化金额
     *
     * @param money            金额
     * @param maxIntegers      最大整数位数
     * @param maxDecimalDigits 最大小数位数
     * @param filterZero       过滤零
     *                         true 格式化后去除金额尾 0；false 添尾 0 至小数最大位数。
     * @return 格式化后金额
     */
    private String formatMoneyTwo(String money, int maxIntegers, int maxDecimalDigits, boolean filterZero) {
        // 按元显示需除以 100
        if (TextUtils.isEmpty(money)) {
            money = "0";
        }
        money = new BigDecimal(money).divide(new BigDecimal(100)).toPlainString();
        return formatMoney(money, maxIntegers, maxDecimalDigits, filterZero);
    }
}

