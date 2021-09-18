package com.chaos.pool.module.login.listener;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created on 2021/9/18
 *
 * @author zsp
 * @desc 登录二页监听
 */
public interface LoginTwoActivityListener {
    /**
     * 处理手机号
     *
     * @param appCompatActivity 活动
     * @param phoneNumber       手机号
     */
    void handleWithPhoneNumber(AppCompatActivity appCompatActivity, String phoneNumber);
}
