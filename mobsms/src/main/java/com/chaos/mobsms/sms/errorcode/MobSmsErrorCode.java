package com.chaos.mobsms.sms.errorcode;

import android.content.Context;
import android.text.TextUtils;

import com.chaos.mobsms.R;
import com.chaos.util.java.toast.ToastKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/1/7
 *
 * @author zsp
 * @desc MobSMS 错误码
 * SMSSDK for Android API 回调中，result = {@link cn.smssdk.SMSSDK#RESULT_ERROR} 时 data 类型为 Throwable。
 * Throwable 的 message 存放 json 数据，从中读取 status 错误码信息。
 * 一部分服务器返回；一部分 SDK 本地生成。
 */
public class MobSmsErrorCode {
    private static final Map<Integer, String> ERROR_CODE_MESSAGE_MAP = new HashMap<>();

    static {
        ERROR_CODE_MESSAGE_MAP.put(206, "MobSMS 模板无效");
        ERROR_CODE_MESSAGE_MAP.put(400, "MobSMS 无效请求");
        ERROR_CODE_MESSAGE_MAP.put(401, "MobSMS token为空");
        ERROR_CODE_MESSAGE_MAP.put(402, "MobSMS token不存在");
        ERROR_CODE_MESSAGE_MAP.put(403, "MobSMS CRC32为空");
        ERROR_CODE_MESSAGE_MAP.put(404, "MobSMS CRC32非法");
        ERROR_CODE_MESSAGE_MAP.put(405, "MobSMS AppKey为空");
        ERROR_CODE_MESSAGE_MAP.put(406, "MobSMS AppKey错误");
        ERROR_CODE_MESSAGE_MAP.put(407, "MobSMS 缺少数据");
        ERROR_CODE_MESSAGE_MAP.put(408, "MobSMS 无效参数");
        ERROR_CODE_MESSAGE_MAP.put(418, "MobSMS 内部接口调用失败");
        ERROR_CODE_MESSAGE_MAP.put(419, "MobSMS duid对应token不存在");
        ERROR_CODE_MESSAGE_MAP.put(420, "MobSMS duid不存在");
        ERROR_CODE_MESSAGE_MAP.put(450, "MobSMS 权限不足");
        ERROR_CODE_MESSAGE_MAP.put(451, "MobSMS User - Agent为空");
        ERROR_CODE_MESSAGE_MAP.put(452, "MobSMS User - Agent格式错误");
        ERROR_CODE_MESSAGE_MAP.put(453, "MobSMS 解密错误");
        ERROR_CODE_MESSAGE_MAP.put(454, "MobSMS 数据格式错误");
        ERROR_CODE_MESSAGE_MAP.put(455, "MobSMS 签名无效");
        ERROR_CODE_MESSAGE_MAP.put(456, "MobSMS 手机号码为空");
        ERROR_CODE_MESSAGE_MAP.put(457, "MobSMS 手机号码格式错误");
        ERROR_CODE_MESSAGE_MAP.put(458, "MobSMS 手机号码在黑名单中");
        ERROR_CODE_MESSAGE_MAP.put(459, "MobSMS 无appKey的控制数据");
        ERROR_CODE_MESSAGE_MAP.put(460, "MobSMS 无权限发送短信");
        ERROR_CODE_MESSAGE_MAP.put(461, "MobSMS 不支持该地区发送短信");
        ERROR_CODE_MESSAGE_MAP.put(462, "MobSMS 每分钟发送次数超限");
        ERROR_CODE_MESSAGE_MAP.put(463, "MobSMS 手机号码每天发送次数超限");
        ERROR_CODE_MESSAGE_MAP.put(464, "MobSMS 每台手机每天发送次数超限");
        ERROR_CODE_MESSAGE_MAP.put(465, "MobSMS 号码在App中每天发送短信次数超限");
        ERROR_CODE_MESSAGE_MAP.put(466, "MobSMS 校验的验证码为空");
        ERROR_CODE_MESSAGE_MAP.put(467, "MobSMS 校验验证码请求频繁");
        ERROR_CODE_MESSAGE_MAP.put(468, "MobSMS 校验的验证码错误");
        ERROR_CODE_MESSAGE_MAP.put(469, "MobSMS 未开启web发送短信");
        ERROR_CODE_MESSAGE_MAP.put(470, "MobSMS 账户余额不足");
        ERROR_CODE_MESSAGE_MAP.put(471, "MobSMS 请求IP错误");
        ERROR_CODE_MESSAGE_MAP.put(472, "MobSMS 客户端请求发送短信验证过于频繁");
        ERROR_CODE_MESSAGE_MAP.put(473, "MobSMS 服务端根据duid获取平台错误");
        ERROR_CODE_MESSAGE_MAP.put(474, "MobSMS 没有打开服务端验证开关");
        ERROR_CODE_MESSAGE_MAP.put(475, "MobSMS appKey的应用信息不存在");
        ERROR_CODE_MESSAGE_MAP.put(476, "MobSMS 当前appKey发送短信数量超过限额");
        ERROR_CODE_MESSAGE_MAP.put(477, "MobSMS 当前手机号发送短信数量超过限额");
        ERROR_CODE_MESSAGE_MAP.put(478, "MobSMS 当前手机号在当前应用内发送超过限额");
        ERROR_CODE_MESSAGE_MAP.put(481, "MobSMS 验证接口调用错误");
        ERROR_CODE_MESSAGE_MAP.put(482, "MobSMS 传输中数据丢失");
        ERROR_CODE_MESSAGE_MAP.put(483, "MobSMS AppSecret错误");
        ERROR_CODE_MESSAGE_MAP.put(484, "MobSMS 模板不存在");
        ERROR_CODE_MESSAGE_MAP.put(489, "MobSMS MD5码错误");
        ERROR_CODE_MESSAGE_MAP.put(500, "MobSMS 服务器内部错误");
        ERROR_CODE_MESSAGE_MAP.put(501, "MobSMS 501 Not Implemented");
        ERROR_CODE_MESSAGE_MAP.put(502, "MobSMS 502 Bad Gateway");
        ERROR_CODE_MESSAGE_MAP.put(503, "MobSMS 503 Service Unavailable");
        ERROR_CODE_MESSAGE_MAP.put(504, "MobSMS 504 Gateway Timeout");
        ERROR_CODE_MESSAGE_MAP.put(505, "MobSMS 505 HTTP Version Not Supported");
        ERROR_CODE_MESSAGE_MAP.put(506, "MobSMS 506 Variant Also Negotiates(RFC 2295)");
        ERROR_CODE_MESSAGE_MAP.put(507, "MobSMS 507 Insufficient Storage (WebDAV; RFC 4918)");
        ERROR_CODE_MESSAGE_MAP.put(508, "MobSMS 508 Loop Detected (WebDAV; RFC 5842)");
        ERROR_CODE_MESSAGE_MAP.put(510, "MobSMS 510 Not Extended (RFC 2774)");
        ERROR_CODE_MESSAGE_MAP.put(511, "MobSMS 511 Network Authentication Required(RFC 6585)");
        // 本地错误码
        ERROR_CODE_MESSAGE_MAP.put(600, "MobSMS API使用受限制");
        ERROR_CODE_MESSAGE_MAP.put(601, "MobSMS 短信发送受限");
        ERROR_CODE_MESSAGE_MAP.put(602, "MobSMS 无法发送此地区短信");
        ERROR_CODE_MESSAGE_MAP.put(603, "MobSMS 请填写正确手机号码");
        ERROR_CODE_MESSAGE_MAP.put(604, "MobSMS 当前服务暂不支持此国家");
        ERROR_CODE_MESSAGE_MAP.put(605, "MobSMS 没有权限连接服务端");
        ERROR_CODE_MESSAGE_MAP.put(606, "MobSMS 无权访问该接口");
        ERROR_CODE_MESSAGE_MAP.put(607, "MobSMS Request header错误：Content - Length错误");
        ERROR_CODE_MESSAGE_MAP.put(608, "MobSMS Request header错误：AppKey为空");
        ERROR_CODE_MESSAGE_MAP.put(609, "MobSMS Request header错误：Sign为空");
        ERROR_CODE_MESSAGE_MAP.put(610, "MobSMS Request header错误：UserAgent为空");
        ERROR_CODE_MESSAGE_MAP.put(611, "MobSMS AppSecret为空");
        ERROR_CODE_MESSAGE_MAP.put(612, "MobSMS 未授权MobTech隐私协议");
        ERROR_CODE_MESSAGE_MAP.put(613, "MobSMS 该功能在当前地区不可用");
        ERROR_CODE_MESSAGE_MAP.put(614, "MobSMS 未接受服务协议, 功能不可用");
        ERROR_CODE_MESSAGE_MAP.put(615, "MobSMS 网络异常，请稍候再试");
    }

    /**
     * 错误消息显示
     *
     * @param context   上下文
     * @param errorCode 错误码
     */
    public static void errorMessageShow(Context context, int errorCode) {
        String errorMessage = ERROR_CODE_MESSAGE_MAP.get(errorCode);
        ToastKit.showShort(TextUtils.isEmpty(errorMessage) ? context.getString(R.string.mobSmsUnknownError) : errorMessage);
    }
}

