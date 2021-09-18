package application;

import android.app.Application;

import com.chaos.janalytics.configure.JanalyticsInitConfigure;
import com.chaos.jpush.configure.JpushInitConfigure;
import com.chaos.mobsms.sms.configure.MobSmsInitConfigure;
import com.chaos.pool.application.PoolApp;
import com.chaos.pool.module.login.LoginTwoActivity;
import com.chaos.pool.module.splash.kit.SplashActivityKit;
import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.BuildConfig;
import com.example.chaos.MainActivity;

import java.util.List;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc 应用
 */
public class App extends PoolApp {
    /**
     * 应用程序创调
     * <p>
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获 Application 单例。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化配置
        initConfiguration();
    }

    /**
     * 单例
     * <p>
     * Application 本已单例。
     *
     * @return 单例
     */
    @Override
    protected Application instance() {
        return this;
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
     *
     * @return 权限集
     */
    @Override
    protected List<String> permissionList() {
        return super.permissionList();
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // 极光推送
        JpushInitConfigure.initJpush(this, debug());
        // 极光统计
        JanalyticsInitConfigure.initJanalytics(this, debug());
        // MobSMS
        MobSmsInitConfigure.initMobSms(this);
        // 闪屏页监听
        SplashActivityKit splashActivityKit = new SplashActivityKit();
        splashActivityKit.setSplashActivityListener(appCompatActivity -> IntentJump.getInstance().jump(null, appCompatActivity, true, LoginTwoActivity.class));
        // 登录二页监听
        LoginTwoActivity.setLoginTwoActivityListener((appCompatActivity, phoneNumber) -> IntentJump.getInstance().jump(null, appCompatActivity, true, MainActivity.class));
    }
}
