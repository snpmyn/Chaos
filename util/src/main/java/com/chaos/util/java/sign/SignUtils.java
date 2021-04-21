package com.chaos.util.java.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.chaos.util.java.log.LogUtils;

import org.jetbrains.annotations.Nullable;

import okio.ByteString;

/**
 * Created on 2020-08-31
 *
 * @author zsp
 * @desc 签名工具类
 */
public class SignUtils {
    /**
     * 获取签名 MD5Hex
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 签名 MD5Hex
     */
    @Nullable
    public static String getSignMd5Hex(Context context, String packageName) {
        byte[] sign;
        sign = getSign(context, packageName);
        if (null == sign) {
            return null;
        }
        return ByteString.of(sign).md5().hex();
    }

    /**
     * 获取签名 SHA256Hex
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 签名 SHA256Hex
     */
    @Nullable
    public static String getSignSha256Hex(Context context, String packageName) {
        byte[] sign;
        sign = getSign(context, packageName);
        if (null == sign) {
            return null;
        }
        return ByteString.of(sign).sha256().hex();
    }

    /**
     * 获取签名
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 字节数组
     */
    @SuppressLint("PackageManagerGetSignatures")
    private static byte[] getSign(Context context, String packageName) {
        if ((null == context) || (null == packageName)) {
            return null;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.exception(e);
        }
        if ((null != packageInfo) && (null != packageInfo.signatures) && (packageInfo.signatures.length > 0)) {
            return packageInfo.signatures[0].toByteArray();
        }
        return null;
    }
}
