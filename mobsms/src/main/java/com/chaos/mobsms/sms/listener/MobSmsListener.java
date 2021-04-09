package com.chaos.mobsms.sms.listener;

/**
 * Created on 2021/4/9
 *
 * @author zsp
 * @desc MobSMS 监听
 */
public interface MobSmsListener {
    /**
     * 智能验证
     */
    void smartVerification();

    /**
     * 提验证码事件
     */
    void eventSubmitVerificationCode();

    /**
     * 获验证码事件
     */
    void eventGetVerificationCode();

    /**
     * 结果错误
     */
    void resultError();
}
