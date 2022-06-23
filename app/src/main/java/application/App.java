package application;

import android.Manifest;

import com.chaos.doraemonkit.DoraemonKitInitConfigure;
import com.chaos.janalytics.configure.JanalyticsInitConfigure;
import com.chaos.jpush.configure.JpushInitConfigure;
import com.chaos.mobsms.sms.configure.MobSmsInitConfigure;
import com.chaos.pgyer.configure.PgyerInitConfigure;
import com.chaos.pool.application.PoolApp;
import com.chaos.pool.module.login.LoginTwoActivity;
import com.chaos.pool.module.splash.kit.SplashActivityKit;
import com.chaos.util.java.intent.IntentJump;
import com.chaos.widget.status.manager.StatusManager;
import com.example.chaos.BuildConfig;
import com.pgyer.pgyersdk.pgyerenum.Features;

import java.util.List;

import configure.FragmentationInitConfig;
import kit.AppKit;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc 应用
 */
public class App extends PoolApp {
    private static App appInstance;

    /**
     * 获单例
     *
     * @return 单例
     */
    public static App getAppInstance() {
        return appInstance;
    }

    /**
     * 应用程序创调
     * <p>
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获 Application 单例。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Application 本已单例
        appInstance = this;
        // 状态管理器布局 ID
        StatusManager.BASE_LOADING_LAYOUT_ID = com.chaos.pool.R.layout.status_loading_with_animation;
        StatusManager.BASE_EMPTY_LAYOUT_ID = com.chaos.pool.R.layout.status_empty_with_animation;
        // 初始化配置
        initConfiguration();
    }

    /**
     * 调试否
     *
     * @return 调试否
     */
    @Override
    protected Boolean debug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 集成 MobSms 否
     *
     * @return 集成 MobSms 否
     */
    @Override
    protected Boolean ensembleMobSms() {
        return super.ensembleMobSms();
    }

    /**
     * 权限集
     * <p>
     * 默动获 Manifest.permission.READ_EXTERNAL_STORAGE。
     *
     * @return 权限集
     */
    @Override
    protected List<String> permissionList() {
        List<String> list = super.permissionList();
        // TODO: 2021/10/12 华为机型除 Manifest.permission.READ_EXTERNAL_STORAGE 外，亦需 Manifest.permission.WRITE_EXTERNAL_STORAGE。待优化。
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        list.add(Manifest.permission.READ_PHONE_STATE);
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        return list;
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // DoraemonKit
        DoraemonKitInitConfigure.initDoraemonKit(this, "xxxxxx", debug(), true);
        // Fragmentation
        FragmentationInitConfig.initFragmentation(debug());
        // 极光推送
        JpushInitConfigure.initJpush(this, debug());
        // 极光统计
        JanalyticsInitConfigure.initJanalytics(this, debug());
        // MobSMS
        MobSmsInitConfigure.initMobSms(this);
        // Pgyer
        PgyerInitConfigure.initPgyer(this, "b40f6bc02fc9fd0280d38b3752a5d4a4", "33ff1520a3b3b352210b83f33b7bdf96", Features.CHECK_UPDATE);
        // 闪屏页监听
        SplashActivityKit splashActivityKit = new SplashActivityKit();
        splashActivityKit.setSplashActivityListener(appCompatActivity -> IntentJump.getInstance().jump(null, appCompatActivity, true, LoginTwoActivity.class));
        // 登录二页监听
        AppKit appKit = new AppKit();
        LoginTwoActivity.setLoginTwoActivityListener(appKit::localSave);
    }
}
