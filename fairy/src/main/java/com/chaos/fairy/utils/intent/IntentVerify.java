package com.chaos.fairy.utils.intent;

import android.content.Intent;

import org.jetbrains.annotations.Nullable;

import okio.ByteString;

/**
 * Created on 2021/1/4
 *
 * @author zsp
 * @desc 意图核实
 * 防止服务攻击。
 */
public class IntentVerify {
    private final static String KEY = ByteString.encodeUtf8("FairyKey").md5().hex();

    /**
     * 是否是坏意图
     *
     * @param intent 意图
     * @return 是否是坏意图
     */
    public static boolean badIntent(Intent intent) {
        if (null == intent) {
            return false;
        }
        try {
            intent.putExtra(KEY, "x");
            intent.removeExtra(KEY);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取 String 类型额外信息
     *
     * @param intent 意图
     * @param key    键
     * @return String 类型额外信息
     */
    @Nullable
    public static String getStringExtra(Intent intent, String key) {
        if (badIntent(intent)) {
            return null;
        }
        return intent.getStringExtra(key);
    }

    /**
     * 获取 double 类型额外信息
     *
     * @param intent       意图
     * @param key          键
     * @param defaultValue 默值
     * @return double 类型额外信息
     */
    public static double getDoubleExtra(Intent intent, String key, double defaultValue) {
        if (badIntent(intent)) {
            return 0.0;
        }
        return intent.getDoubleExtra(key, defaultValue);
    }
}
