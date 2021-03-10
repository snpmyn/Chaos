package com.chaos.mobsms.sms.kit;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created on 2019/4/17.
 *
 * @author 郑少鹏
 * @desc MobSMS 配套元件
 */
public class MobSmsKit {
    /**
     * DCL（Double Check Lock）式单例
     */
    private static MobSmsKit dclInstance;

    /**
     * constructor
     */
    private MobSmsKit() {

    }

    /**
     * DCL（Double Check Lock）获单例
     * <p>
     * 网上建议和使用最多。
     * <p>
     * 优点：
     * 构造函数 private 修饰（外部无法访）。
     * 用即调 getInstanceByDcl() 时才初始化。
     * static 关键字修饰（静态变量，存于内存，仅一份数据）。
     * synchronized 线程安全（多线程单例唯一性）。
     * 两次判空避多次同步。
     * <p>
     * 缺点：
     * {@link #dclInstance}、{@link #MobSmsKit()}、getInstanceByDcl() 因 jvm 允乱序执行。该三句代码顺序不定，或现 DCL 失效。
     * 步骤一：A 线程执行 getInstanceByDcl() 时还没执行构造方法 {@link #MobSmsKit()}。
     * 步骤二：此时 B 线程调 getInstanceByDcl()，因 A 已执行 getInstanceByDcl()，故 {@link #dclInstance} 不为空就直获。
     * 步骤三：因 B 直获，而真实情况 A 线程构造方法还未执行，故 {@link #dclInstance} 为空。
     * <p>
     * 解决：
     * 此况概率较小。但为解决，Java1.6 加 volatile 关键字避 DCL 方式失效。虽 volatile 消耗一些性能，但为 DCL 最佳写法。
     * 虽 volatile 使 DCL 方式完美，但没 volatile 关键字写法满足大部分情况。除非高并发运行或 Java1.6 前。
     *
     * @return 单例
     */
    public static MobSmsKit getInstanceByDcl() {
        if (dclInstance == null) {
            synchronized (MobSmsKit.class) {
                if (dclInstance == null) {
                    dclInstance = new MobSmsKit();
                }
            }
        }
        return dclInstance;
    }

    /**
     * 读通讯录问权限
     * <p>
     * 读通信录提用户则添下代码且于其它代码调前，否无用。无此需求不加。
     * 加代码后申请权限成功，系统再弹对话框提用户继用该权限否。
     *
     * @param askPermissionOnReadContact 读通讯录问权限否
     */
    @Deprecated
    public void setAskPermissionOnReadContact(boolean askPermissionOnReadContact) {
        SMSSDK.setAskPermisionOnReadContact(askPermissionOnReadContact);
    }

    /**
     * 注册回调接口
     * <p>
     * {@link SMSSDK#registerEventHandler(EventHandler)} 须配套 {@link SMSSDK#unregisterAllEventHandler()} 用，否或内存泄漏。
     * 向 {@link SMSSDK} 中注册一事件接收器，{@link SMSSDK} 允注册任意数量接收器，所有接收器都会于事件被触发时收到消息。
     *
     * @param eventHandler EventHandler
     */
    public void registerEventHandler(EventHandler eventHandler) {
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 注销回调接口
     * <p>
     * {@link SMSSDK#registerEventHandler(EventHandler)} 须配套 {@link SMSSDK#unregisterAllEventHandler()} 用，否或内存泄漏。
     *
     * @param eventHandler EventHandler
     */
    public void unregisterEventHandler(EventHandler eventHandler) {
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 获短信目前支持国家列表
     * <p>
     * 监听返回。
     */
    public void getSupportedCountries() {
        SMSSDK.getSupportedCountries();
    }

    /**
     * 获验证码
     * <p>
     * 短信 SDK 不支持世界上所有国家短信验证服务，用短信验证码功能前调 {@link MobSmsKit#getSupportedCountries()} 获当前 SDK 所支持国家列表和号码匹配规则。
     * {@link SMSSDK#getVerificationCode(String, String)} 向服务器请求发送验证码，监听返回，需传递国家代号和接验证码手机号码，{@link MobSmsKit#getSupportedCountries()} 获支持该服务国家代码。
     * {@link MobSmsKit#submitVerificationCode(String, String, String)} 向服务器提交所接短信验证码，验证成功通 {@link EventHandler} 返国家代码和电话号码。
     * 请求 {@link SMSSDK#getVerificationCode(String, String)} 间隔不小 60 秒，否服务端返"操作过于频繁"错误。
     *
     * @param countryAreaCode      国家区号 中国大陆传 86
     * @param cellPhoneNumber      手机号
     * @param templateNumber       模板编号 Mob 后台申请自定模板（无需传 null）
     * @param onSendMessageHandler OnSendMessageHandler 发短信前自执行一操作据手机号判需发短信否
     */
    public void getVerificationCode(String countryAreaCode, String cellPhoneNumber, String templateNumber, OnSendMessageHandler onSendMessageHandler) {
        SMSSDK.getVerificationCode(countryAreaCode, cellPhoneNumber, templateNumber, onSendMessageHandler);
    }

    /**
     * 提验证码
     *
     * @param countryAreaCode    国家区号
     * @param cellPhoneNumber    手机号
     * @param authenticationCode 验证码
     */
    public void submitVerificationCode(String countryAreaCode, String cellPhoneNumber, String authenticationCode) {
        SMSSDK.submitVerificationCode(countryAreaCode, cellPhoneNumber, authenticationCode);
    }
}
