package com.chaos.util.java.property;

import android.content.Context;
import android.text.TextUtils;

import com.chaos.util.java.log.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created on 2020-09-11
 *
 * @author zsp
 * @desc 属性工具类
 */
public class PropertyUtils {
    private String line = "";
    private final List<String> keysList = new ArrayList<>();
    private final Map<String, String> keyValueMap = new LinkedHashMap<>();

    private List<String> getKeysList() {
        return keysList;
    }

    private Map<String, String> getKeyValueMap() {
        return keyValueMap;
    }

    /**
     * 获取属性键或值集合
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param flag     标示（0 键、1 值）
     * @return 属性键或值集合
     */
    public List<String> getPropertyKeyOrValueList(Context context, String fileName, int flag) {
        List<String> stringList = new ArrayList<>();
        if (TextUtils.isEmpty(fileName)) {
            return stringList;
        }
        loadProperty(context, fileName);
        if (flag == 0) {
            return getKeysList();
        } else if (flag == 1) {
            Map<String, String> keyValueMap = getKeyValueMap();
            for (String key : getKeysList()) {
                String value = keyValueMap.get(key);
                if (value != null) {
                    stringList.add(value);
                }
            }
        }
        return stringList;
    }

    /**
     * 加载属性
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    private void loadProperty(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            LogUtils.exception(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtils.exception(e);
                }
            }
        }
    }

    /**
     * 加载
     *
     * @param inputStream 输入流
     * @throws IOException 异常
     */
    private synchronized void load(InputStream inputStream) throws IOException {
        List<String> linesList = getLinesList(inputStream);
        for (String line : linesList) {
            // 处理注释外键值对
            if (!line.trim().startsWith("#") && line.contains("=")) {
                String k = line.substring(0, line.indexOf("=")).trim();
                String v = line.substring(line.indexOf("=") + 1).trim();
                keysList.add(k);
                keyValueMap.put(k, v);
            }
        }
    }

    /**
     * 获取行集
     *
     * @param inputStream 输入流
     * @return 行集
     * @throws IOException IO 异常
     */
    private @NotNull List<String> getLinesList(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> linesList = new ArrayList<>();
        while (read(bufferedReader) != null) {
            linesList.add(line);
        }
        bufferedReader.close();
        inputStreamReader.close();
        return linesList;
    }

    private String read(@NotNull BufferedReader bufferedReader) throws IOException {
        line = bufferedReader.readLine();
        return line;
    }

    /**
     * 获取属性
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 属性
     */
    public Properties getProperties(Context context, String fileName) {
        InputStream inputStream = null;
        Properties properties = new Properties();
        try {
            inputStream = context.getAssets().open(fileName);
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            inputStream.close();
        } catch (Exception e) {
            LogUtils.exception(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtils.exception(e);
                }
            }
        }
        return properties;
    }

    @Override
    public @NotNull String toString() {
        return keyValueMap.toString();
    }
}
