package com.chaos.bmob.errorcode;

import android.content.Context;
import android.text.TextUtils;

import com.chaos.bmob.R;
import com.chaos.util.java.toast.ToastKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/2/8
 *
 * @author zsp
 * @desc Bmob 错误码
 * Android SDK 错误码都以 9 开头。
 */
public class BmobErrorCode {
    private static final Map<Integer, String> ERROR_CODE_MESSAGE_MAP = new HashMap<>(19);

    static {
        ERROR_CODE_MESSAGE_MAP.put(9001, "Application Id为空");
        ERROR_CODE_MESSAGE_MAP.put(9002, "解析返回数据出错");
        ERROR_CODE_MESSAGE_MAP.put(9003, "上传文件出错");
        ERROR_CODE_MESSAGE_MAP.put(9004, "文件上传失败");
        ERROR_CODE_MESSAGE_MAP.put(9005, "批量操作只支持最多50条");
        ERROR_CODE_MESSAGE_MAP.put(9006, "objectId为空");
        ERROR_CODE_MESSAGE_MAP.put(9007, "文件大小超过10M");
        ERROR_CODE_MESSAGE_MAP.put(9008, "上传文件不存在");
        ERROR_CODE_MESSAGE_MAP.put(9009, "无缓存数据");
        ERROR_CODE_MESSAGE_MAP.put(9010, "网络超时");
        ERROR_CODE_MESSAGE_MAP.put(9011, "BmobUser类不支持批量操作");
        ERROR_CODE_MESSAGE_MAP.put(9012, "上下文为空");
        ERROR_CODE_MESSAGE_MAP.put(9013, "BmobObject(数据表名称)格式不正确");
        ERROR_CODE_MESSAGE_MAP.put(9014, "第三方账号授权失败");
        ERROR_CODE_MESSAGE_MAP.put(9015, "其它错误均返回此code");
        ERROR_CODE_MESSAGE_MAP.put(9016, "无网络连接");
        ERROR_CODE_MESSAGE_MAP.put(9017, "第三方登录相关错误");
        ERROR_CODE_MESSAGE_MAP.put(9018, "参数不能为空");
        ERROR_CODE_MESSAGE_MAP.put(9019, "手机号码、邮箱地址、验证码格式不正确");
    }

    /**
     * 错误消息显示
     *
     * @param context   上下文
     * @param errorCode 错误码
     */
    public static void errorMessageShow(Context context, int errorCode) {
        String errorMessage = ERROR_CODE_MESSAGE_MAP.get(errorCode);
        ToastKit.showShort(TextUtils.isEmpty(errorMessage) ? context.getString(R.string.bmobUnknownError) : errorMessage);
    }
}
