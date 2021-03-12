package com.chaos.bmob.configure;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created on 2021/2/5
 *
 * @author zsp
 * @desc Bmob 初始化配置
 * v3.4.7+ 可通 BmobConfig 设置。
 */
public class BmobInitConfiguration {
    /**
     * 初始化 Bmob
     *
     * @param application    应用
     * @param applicationId  应用 ID
     * @param connectTimeout 超时时间
     * @param blockSize      每片大小
     * @param expiration     过期时间
     */
    public static void initBmob(Application application, String applicationId, long connectTimeout, int blockSize, long expiration) {
        BmobConfig config = new BmobConfig.Builder(application)
                .setApplicationId(applicationId)
                // 请求超时时间（默 15s）
                .setConnectTimeout(connectTimeout)
                // 文件分片上传每片大小（默 512*1024 字节）
                .setUploadBlockSize(blockSize)
                // 文件过期时间（默 1800s）
                .setFileExpiration(expiration)
                .build();
        Bmob.initialize(config);
    }
}
