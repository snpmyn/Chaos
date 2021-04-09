package com.chaos.janalytics.kit;

import android.content.Context;

import com.chaos.janalytics.listener.JanalyticsListener;
import com.chaos.janalytics.value.JanalyticsEnum;
import com.chaos.util.java.map.MapUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.analytics.android.api.Account;
import cn.jiguang.analytics.android.api.BrowseEvent;
import cn.jiguang.analytics.android.api.CalculateEvent;
import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.Currency;
import cn.jiguang.analytics.android.api.Event;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.LoginEvent;
import cn.jiguang.analytics.android.api.PurchaseEvent;
import cn.jiguang.analytics.android.api.RegisterEvent;

/**
 * Created on 2019/9/4.
 *
 * @author 郑少鹏
 * @desc JanalyticsKit
 */
public class JanalyticsKit {
    /**
     * 调试模式
     * <p>
     * true 打印更多日志。
     * 建于 init 前调。
     *
     * @param enable 调试开关
     */
    public static void setDebugMode(boolean enable) {
        JAnalyticsInterface.setDebugMode(enable);
    }

    /**
     * 初始
     * <p>
     * 建于 Application 之 onCreate 调。
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        JAnalyticsInterface.init(context);
    }

    /**
     * 开 CrashLog 日志上报
     *
     * @param context 应用上下文
     */
    public static void initCrashHandler(Context context) {
        JAnalyticsInterface.initCrashHandler(context);
    }

    /**
     * 关 CrashLog 日志上报
     *
     * @param context 应用上下文
     */
    public static void stopCrashHandler(Context context) {
        JAnalyticsInterface.stopCrashHandler(context);
    }

    /**
     * 动配 channel
     * <p>
     * 优先级较 AndroidManifest 高。
     *
     * @param context 应用上下文
     * @param channel 希望配的 channel（null 表仍用 AndroidManifest 配的 channel）
     */
    public static void setChannel(Context context, String channel) {
        JAnalyticsInterface.setChannel(context, channel);
    }

    /**
     * 页面启动
     * <p>
     * 页面（Activity 和 Fragment）相关生命周期内同 onPageEnd 成对调。
     * Activity 和 Fragment 不同况对生命周期影响详见说明。
     * <p>
     * android 4.0 及以上默上报 Activity 页面流。android 4.0 以下需调 onPageStart 和 onPageEnd上 报页面流。
     * 开发者自定 Activity 和 Fragment 同页面否。于相应法成对调 onPageStart 和 onPageEnd。
     * Activity 含多 Fragment，每 Fragment 都需作页面统计时，基于 Fragment 切换模式如下建议：
     * 1.replace 模式切 Fragment 于 onResume 和 onPause 生命周期成对调；
     * 2.ViewPager 含 Fragment 切换，需 Fragment 监听 setUserVisibleHint 接口，通返参成对调；
     * 3.show / hide 模式切 Fragment 需监听 onHiddenChanged 接口确认 Fragment 显否，且 onResume 亦需调 onPageStart（onPause 无需调 onPageEnd）。
     *
     * @param context  Activity上下文
     * @param pageName 页面名
     */
    public static void onPageStart(Context context, String pageName) {
        JAnalyticsInterface.onPageStart(context, pageName);
    }

    /**
     * 页面结束
     * <p>
     * 页面（Activity 和 Fragment）相关生命周期内同 onPageEnd 成对调。
     * Activity 和 Fragment 不同况对生命周期影响详见说明。
     * <p>
     * android 4.0 及以上默上报 Activity 页面流。android 4.0 以下需调 onPageStart 和 onPageEnd 上报页面流。
     * 开发者自定 Activity 和 Fragment 同页面否。于相应法成对调 onPageStart 和 onPageEnd。
     * Activity 含多 Fragment，每 Fragment 都需作页面统计时，基于 Fragment 切换模式如下建议：
     * 1.replace 模式切 Fragment 于 onResume 和 onPause 生命周期成对调；
     * 2.ViewPager 含 Fragment 切换，需 Fragment 监听 setUserVisibleHint 接口，通返参成对调；
     * 3.show / hide 模式切 Fragment 需监听 onHiddenChanged 接口确认 Fragment 显否，且 onResume 亦需调 onPageStart（onPause 无需调 onPageEnd）。
     *
     * @param context  Activity 上下文
     * @param pageName 页面名
     */
    public static void onPageEnd(Context context, String pageName) {
        JAnalyticsInterface.onPageEnd(context, pageName);
    }

    /**
     * 自定事件
     * <p>
     * 通传不同事件模型统计各种事件。
     *
     * @param context 上下文
     * @param event   事件模型
     *                {@link CountEvent}（计数事件）
     *                {@link CalculateEvent}（计算事件）
     *                {@link RegisterEvent}（注册事件）
     *                {@link LoginEvent}（登录事件）
     *                {@link BrowseEvent}（浏览事件）
     *                {@link PurchaseEvent}（购买事件）
     */
    private static void onEvent(Context context, Event event) {
        JAnalyticsInterface.onEvent(context, event);
    }

    /**
     * 计数事件
     * <p>
     * 自定计数事件模型扩展参数不可用 key 值 event_id。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context 上下文
     * @param eventId 事件 ID（非空）
     * @param extMap  扩展参数
     */
    public static void onCountEvent(Context context, String eventId, Map<String, String> extMap) {
        CountEvent countEvent = new CountEvent(eventId);
        countEvent.addExtMap(extMap);
        onEvent(context, countEvent);
    }

    /**
     * 计数事件
     * <p>
     * 自定计数事件模型扩展参数不可用 key 值 event_id。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context             上下文
     * @param eventId             事件 ID（非空）
     * @param extMapKeysAndValues 扩展参数键值
     */
    public static void onCountEvent(Context context, String eventId, String... extMapKeysAndValues) {
        CountEvent countEvent = new CountEvent(eventId);
        List<String> list = Arrays.asList(extMapKeysAndValues);
        countEvent.addExtMap(MapUtils.mapFromList(list));
        onEvent(context, countEvent);
    }

    /**
     * 计算事件
     * <p>
     * 自定计算事件模型扩展参数不可用 key 值 event_id、event_value。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context    上下文
     * @param eventId    事件 ID（非空）
     * @param eventValue 事件值（非空）
     * @param extMap     扩展参数
     */
    public static void onCalculateEvent(Context context, String eventId, double eventValue, Map<String, String> extMap) {
        CalculateEvent calculateEvent = new CalculateEvent(eventId, eventValue);
        calculateEvent.addExtMap(extMap);
        onEvent(context, calculateEvent);
    }

    /**
     * 计算事件
     * <p>
     * 自定计算事件模型扩展参数不可用 key 值 event_id、event_value。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context             上下文
     * @param eventId             事件 ID（非空）
     * @param eventValue          事件值（非空）
     * @param extMapKeysAndValues 扩展参数键值
     */
    public static void onCalculateEvent(Context context, String eventId, double eventValue, String... extMapKeysAndValues) {
        CalculateEvent calculateEvent = new CalculateEvent(eventId, eventValue);
        List<String> list = Arrays.asList(extMapKeysAndValues);
        calculateEvent.addExtMap(MapUtils.mapFromList(list));
        onEvent(context, calculateEvent);
    }

    /**
     * 登录事件
     * <p>
     * 自定登录事件模型扩展参数不可用 key 值 login_method、login_success。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context      上下文
     * @param loginMethod  登录方式（非空）
     * @param loginSuccess 登录成功（非空）
     * @param extMap       扩展参数
     */
    public static void onLoginEvent(Context context, String loginMethod, boolean loginSuccess, Map<String, String> extMap) {
        LoginEvent loginEvent = new LoginEvent(loginMethod, loginSuccess);
        loginEvent.addExtMap(extMap);
        onEvent(context, loginEvent);
    }

    /**
     * 登录事件
     * <p>
     * 自定登录事件模型扩展参数不可用 key 值 login_method、login_success。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context             上下文
     * @param loginMethod         登录方式（非空）
     * @param loginSuccess        登录成功（非空）
     * @param extMapKeysAndValues 扩展参数键值
     */
    public static void onLoginEvent(Context context, String loginMethod, boolean loginSuccess, String... extMapKeysAndValues) {
        LoginEvent loginEvent = new LoginEvent(loginMethod, loginSuccess);
        List<String> list = Arrays.asList(extMapKeysAndValues);
        loginEvent.addExtMap(MapUtils.mapFromList(list));
        onEvent(context, loginEvent);
    }

    /**
     * 注册事件
     * <p>
     * 自定注册事件模型扩展参数不可用 key 值 register_method、register_success。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context         上下文
     * @param registerMethod  注册方式（非空）
     * @param registerSuccess 注册成功（非空）
     * @param extMap          扩展参数
     */
    public static void onRegisterEvent(Context context, String registerMethod, boolean registerSuccess, Map<String, String> extMap) {
        RegisterEvent registerEvent = new RegisterEvent(registerMethod, registerSuccess);
        registerEvent.addExtMap(extMap);
        onEvent(context, registerEvent);
    }

    /**
     * 注册事件
     * <p>
     * 自定注册事件模型扩展参数不可用 key 值 register_method、register_success。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context             上下文
     * @param registerMethod      注册方式（非空）
     * @param registerSuccess     注册成功（非空）
     * @param extMapKeysAndValues 扩展参数键值
     */
    public static void onRegisterEvent(Context context, String registerMethod, boolean registerSuccess, String... extMapKeysAndValues) {
        RegisterEvent registerEvent = new RegisterEvent(registerMethod, registerSuccess);
        List<String> list = Arrays.asList(extMapKeysAndValues);
        registerEvent.addExtMap(MapUtils.mapFromList(list));
        onEvent(context, registerEvent);
    }

    /**
     * 浏览事件
     * <p>
     * 自定浏览事件模型扩展参数不可用 key 值 browse_content_id、browse_name、browse_type、browse_duration。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context        上下文
     * @param browseId       浏览内容 ID（自定）
     * @param browseName     浏览内容名（标题等，非空）
     * @param browseType     浏览内容类型（如热点、汽车、财经等）
     * @param browseDuration 浏览时长（单位秒）
     * @param extMap         扩展参数
     */
    public static void onBrowseEvent(Context context, String browseId, String browseName, String browseType, float browseDuration, Map<String, String> extMap) {
        BrowseEvent browseEvent = new BrowseEvent(browseId, browseName, browseType, browseDuration);
        browseEvent.addExtMap(extMap);
        onEvent(context, browseEvent);
    }

    /**
     * 浏览事件
     * <p>
     * 自定浏览事件模型扩展参数不可用 key 值 browse_content_id、browse_name、browse_type、browse_duration。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context        上下文
     * @param browseId       浏览内容 ID（自定）
     * @param browseName     浏览内容名（标题等，非空）
     * @param browseType     浏览内容类型（如热点、汽车、财经等）
     * @param browseDuration 浏览时长（单位秒）
     * @param extMapKey      扩展参数键
     * @param extMapValue    扩展参数值
     */
    public static void onBrowseEvent(Context context,
                                     String browseId,
                                     String browseName,
                                     String browseType,
                                     float browseDuration,
                                     String extMapKey,
                                     String extMapValue) {
        BrowseEvent browseEvent = new BrowseEvent(browseId, browseName, browseType, browseDuration);
        Map<String, String> extMap = new HashMap<>(1);
        extMap.put(extMapKey, extMapValue);
        browseEvent.addExtMap(extMap);
        onEvent(context, browseEvent);
    }

    /**
     * 购买事件
     * <p>
     * 自定购买事件模型扩展参数不可用 key 值 purchase_goods_id、purchase_goods_name、purchase_price、purchase_currency、purchase_goods_type、purchase_quantity。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context            上下文
     * @param purchaseGoodsId    购买商品 ID（自定）
     * @param purchaseGoodsName  购买商品名
     * @param purchasePrice      购买价格（非空）
     * @param purchaseSuccess    购买成功（非空）
     * @param purchaseCurrency   购买货币类型（人民币 CNY、美元 USD。详参枚举类 Currency）
     * @param purchaseGoodsType  购买商品类型（如衣服、手表等）
     * @param purchaseGoodsCount 购买商品数量
     * @param extMap             扩展参数
     */
    public static void onPurchaseEvent(Context context,
                                       String purchaseGoodsId,
                                       String purchaseGoodsName,
                                       double purchasePrice,
                                       boolean purchaseSuccess,
                                       Currency purchaseCurrency,
                                       String purchaseGoodsType,
                                       int purchaseGoodsCount,
                                       Map<String, String> extMap) {
        PurchaseEvent purchaseEvent = new PurchaseEvent(purchaseGoodsId, purchaseGoodsName, purchasePrice, purchaseSuccess, purchaseCurrency, purchaseGoodsType, purchaseGoodsCount);
        purchaseEvent.addExtMap(extMap);
        onEvent(context, purchaseEvent);
    }

    /**
     * 购买事件
     * <p>
     * 自定购买事件模型扩展参数不可用 key 值 purchase_goods_id、purchase_goods_name、purchase_price、purchase_currency、purchase_goods_type、purchase_quantity。
     * 此类 key 已被模型用，用致统计数据不准确。
     *
     * @param context            上下文
     * @param purchaseGoodsId    购买商品 ID（自定）
     * @param purchaseGoodsName  购买商品名
     * @param purchasePrice      购买价格（非空）
     * @param purchaseSuccess    购买成功（非空）
     * @param purchaseCurrency   购买货币类型（人民币 CNY、美元 USD。详参枚举类 Currency）
     * @param purchaseGoodsType  购买商品类型（如衣服、手表等）
     * @param purchaseGoodsCount 购买商品数量
     * @param extMapKey          扩展参数键
     * @param extMapValue        扩展参数值
     */
    public static void onPurchaseEvent(Context context,
                                       String purchaseGoodsId,
                                       String purchaseGoodsName,
                                       double purchasePrice,
                                       boolean purchaseSuccess,
                                       Currency purchaseCurrency,
                                       String purchaseGoodsType,
                                       int purchaseGoodsCount,
                                       String extMapKey,
                                       String extMapValue) {
        PurchaseEvent purchaseEvent = new PurchaseEvent(purchaseGoodsId, purchaseGoodsName, purchasePrice, purchaseSuccess, purchaseCurrency, purchaseGoodsType, purchaseGoodsCount);
        Map<String, String> extMap = new HashMap<>(1);
        extMap.put(extMapKey, extMapValue);
        purchaseEvent.addExtMap(extMap);
        onEvent(context, purchaseEvent);
    }

    /**
     * 统计上报周期
     * <p>
     * 未调前默即时上报。
     *
     * @param context 应用上下文
     * @param period  周期（单位秒）
     *                最小 10 秒，最大 1 天。
     *                超范打印调失败日志。
     *                0 表统计数据即时上报。
     */
    public static void setAnalyticsReportPeriod(Context context, int period) {
        JAnalyticsInterface.setAnalyticsReportPeriod(context, period);
    }

    /**
     * 鉴定账户
     *
     * @param context            应用上下文
     * @param accountId          账户 ID
     * @param creationTime       账户创时（时间戳）
     * @param name               姓名
     * @param sex                性别（0 未知、1 男、2 女。默 0，不可其它数字）
     * @param paid               是否付费（0 未知、1 是、2 否。默 0，不可其它数字）
     * @param birthday           出生年月（yyyyMMdd 格式校验）
     * @param phone              手机号码
     * @param email              电子邮件
     * @param weiBoId            新浪微博 ID
     * @param weChatId           微信 ID
     * @param qqId               QQ ID
     * @param key                自定维度（只可字符串）
     * @param value              自定维度
     *                           只可字符串、数字类型、null。
     *                           value 空从服务器删key，key 不可用极光内 namespace（符号 $）
     * @param jAnalyticsListener 极光统计监听
     */
    public static void identifyAccount(final Context context,
                                       String accountId,
                                       long creationTime,
                                       String name,
                                       int sex,
                                       int paid,
                                       String birthday,
                                       String phone,
                                       String email,
                                       String weiBoId,
                                       String weChatId,
                                       String qqId,
                                       String key,
                                       Object value,
                                       final JanalyticsListener jAnalyticsListener) {
        Account account = new Account(accountId);
        account.setCreationTime(creationTime);
        account.setName(name);
        account.setSex(sex);
        account.setPaid(paid);
        account.setBirthdate(birthday);
        account.setPhone(phone);
        account.setEmail(email);
        account.setWeiboId(weiBoId);
        account.setWechatId(weChatId);
        account.setQqId(qqId);
        account.setExtraAttr(key, (Serializable) value);
        JAnalyticsInterface.identifyAccount(context, account, (code, msg) -> jAnalyticsListener.callback(JanalyticsEnum.IDENTIFY, code, msg));
    }

    /**
     * 分离用户
     * <p>
     * 解绑当前用户信息。
     *
     * @param context            应用上下文
     * @param janalyticsListener 极光统计监听
     */
    public static void detachAccount(Context context, final JanalyticsListener janalyticsListener) {
        JAnalyticsInterface.detachAccount(context, (code, msg) -> janalyticsListener.callback(JanalyticsEnum.DETACH, code, msg));
    }
}
