package com.chaos.util.java.base64;

import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaos.util.java.data.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import timber.log.Timber;

/**
 * Created on 2018/7/10.
 *
 * @author 郑少鹏
 * @desc Base64 工具类
 */
public class Base64Utils {
    /**
     * 文件转 Base64 编码字符集
     *
     * @param path 路径
     * @return Base64 编码字符集
     */
    @Nullable
    public static String fileToBase64(String path) {
        if (StringUtils.areEmpty(path)) {
            return null;
        }
        InputStream inputStream = null;
        byte[] data;
        String result = null;
        try {
            inputStream = new FileInputStream(path);
            // 创字符流大小数组
            data = new byte[inputStream.available()];
            // 写入数组
            inputStream.read(data);
            // 默认编码格式编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            Timber.e(e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Timber.e(e);
                }
            }
        }
        return result;
    }

    /**
     * Base64 编码字符集转文件
     *
     * @param base64Str Base64 编码字符集
     * @param path      路径
     * @return 成否
     */
    public static boolean base64ToFile(String base64Str, String path) {
        byte[] data = Base64.decode(base64Str, Base64.DEFAULT);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                // 调整异常数据
                data[i] += 256;
            }
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(path);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            Timber.e(e);
            return false;
        }
    }

    /**
     * 文件转 Base64 编码字符集
     * <p>
     * 分段
     *
     * @param path 路径
     * @return Base64 编码字符集
     */
    @NonNull
    public static String fileToBase64Section(String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(path);
            byte[] byteBuffer = new byte[3 * 1000];
            byte[] base64ByteBuffer;
            // 每次从文件读到有效字节数
            int count;
            while ((count = inputStream.read(byteBuffer)) != -1) {
                // 有效字节数不足 3*1000 表至尾（不够填充满 byteBuffer）
                if (count != byteBuffer.length) {
                    // byteBuffer 中截含有效字节数字节段
                    byte[] copy = Arrays.copyOf(byteBuffer, count);
                    // 编码有效字节段
                    base64ByteBuffer = Base64.encode(copy, Base64.DEFAULT);
                } else {
                    base64ByteBuffer = Base64.encode(byteBuffer, Base64.DEFAULT);
                }
                byteArrayOutputStream.write(base64ByteBuffer, 0, base64ByteBuffer.length);
                byteArrayOutputStream.flush();
            }
            inputStream.close();
        } catch (IOException e) {
            Timber.e(e);
        }
        return byteArrayOutputStream.toString();
    }
}
