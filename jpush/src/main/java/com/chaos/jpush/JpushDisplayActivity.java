package com.chaos.jpush;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.jpush.value.JpushConstant;
import com.chaos.util.java.intent.IntentVerify;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * @desc: 极光推送展示页
 * @author: zsp
 * @date: 2021/9/7 10:39 上午
 */
public class JpushDisplayActivity extends AppCompatActivity {
    private MaterialToolbar jpushDisplayActivityMt;
    private TextView jpushDisplayActivityTvTitle;
    private TextView jpushDisplayActivityTvContent;
    /**
     * 通知标题
     */
    private String notificationTitle;
    /**
     * 通知内容
     */
    private String notificationContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_display);
        stepUi();
        initConfiguration();
        setListener();
        startLogic();
    }

    /**
     * 初始控件
     */
    protected void stepUi() {
        jpushDisplayActivityMt = findViewById(R.id.jpushDisplayActivityMt);
        jpushDisplayActivityTvTitle = findViewById(R.id.jpushDisplayActivityTvTitle);
        jpushDisplayActivityTvContent = findViewById(R.id.jpushDisplayActivityTvContent);
    }

    /**
     * 初始配置
     */
    protected void initConfiguration() {
        notificationTitle = IntentVerify.getStringExtra(getIntent(), JpushConstant.NOTIFICATION_TITLE);
        notificationContent = IntentVerify.getStringExtra(getIntent(), JpushConstant.NOTIFICATION_CONTENT);
    }

    /**
     * 设置监听
     */
    protected void setListener() {
        jpushDisplayActivityMt.setNavigationOnClickListener(view -> finish());
    }

    /**
     * 开始逻辑
     */
    protected void startLogic() {
        jpushDisplay();
    }

    /**
     * 极光推送展示
     */
    private void jpushDisplay() {
        jpushDisplayActivityTvTitle.setText(notificationTitle);
        jpushDisplayActivityTvContent.setText(notificationContent);
    }
}