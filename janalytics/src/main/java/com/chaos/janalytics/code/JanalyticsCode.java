package com.chaos.janalytics.code;

import android.content.Context;

import com.chaos.janalytics.R;
import com.chaos.util.java.toast.ToastKit;

/**
 * Created on 2018/4/28.
 *
 * @author 郑少鹏
 * @desc 极光统计状态码
 */
public class JanalyticsCode {
    /**
     * 消息显示
     *
     * @param context 上下文
     * @param code    码
     */
    public static void messageShow(Context context, int code) {
        switch (code) {
            case 1001:
                ToastKit.showShort("accountId为关键参数，不可null或空字符串");
                break;
            case 1002:
                ToastKit.showShort("没绑accountID时调解绑接口");
                break;
            case 1003:
                ToastKit.showShort("10s内请求频率不可超30次");
                break;
            case 1004:
                ToastKit.showShort("accountId不可超255字符");
                break;
            case 1005:
                ToastKit.showShort("先调init()初始SDK");
                break;
            case 1101:
                ToastKit.showShort("具体原因详查");
                break;
            default:
                ToastKit.showShort(context.getString(R.string.janalyticsUnknownError));
                break;
        }
    }
}
