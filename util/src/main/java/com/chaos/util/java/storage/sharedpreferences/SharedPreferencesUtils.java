package com.chaos.util.java.storage.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/11/24.
 * getSharedPreferences()
 * Fragment 内访通资源字符串 R.string.preference_file_key 识别共享首选项文件并专用模式打开，仅允许您应用访文件。
 * 命名您共享首选项文件应使用对您应用唯一可识别名称，如 "com.example.myapp.PREFERENCE_FILE_KEY"。
 * <p>
 * getPreferences()
 * 仅需 Activity 一共享首选项，从 Activity 调此法，该法无需提供名称并检索属该 Activity 默认共享首选项文件。
 * <p>
 * getDefaultSharedPreferences()
 * 默应用包名
 *
 * @author 郑少鹏
 * @desc SharedPreferencesUtils
 */
public class SharedPreferencesUtils {
    private SharedPreferencesUtils instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * 获取单例
     *
     * @param context 上下文
     * @param name    Desired preferences file.
     * @param mode    Operating mode.
     */
    @SuppressLint("CommitPrefEdits")
    public void getInstance(Context context, String name, int mode) {
        if (null == instance) {
            synchronized (SharedPreferencesUtils.class) {
                if (null == instance) {
                    instance = new SharedPreferencesUtils();
                    sharedPreferences = context.getSharedPreferences(name, mode);
                    editor = sharedPreferences.edit();
                }
            }
        }
    }

    /**
     * Save int data.
     *
     * @param key   key
     * @param value value
     */
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Save long data.
     *
     * @param key   key
     * @param value value
     */
    public void saveLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Save String data.
     *
     * @param key   key
     * @param value value
     */
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Save List<String> data.
     *
     * @param key  key
     * @param list list
     */
    public void saveListString(String key, @NotNull List<String> list) {
        // 存前清已存数据保唯一性
        clearListString(key);
        int size = list.size();
        saveInt(key + "size", size);
        for (int i = 0; i < size; i++) {
            saveString(key + i, list.get(i));
        }
    }

    /**
     * Get int data.
     *
     * @param key key
     * @return int
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * Get long data.
     *
     * @param key key
     * @return long
     */
    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * Get String data.
     *
     * @param key      key
     * @param defValue defValue
     * @return string
     */
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * Get List<String> data.
     *
     * @param key key
     * @return list<String>
     */
    public @NotNull List<String> getListString(String key) {
        List<String> list = new ArrayList<>();
        int size = getInt(key + "size");
        for (int i = 0; i < size; i++) {
            list.add(getString(key + i, null));
        }
        return list;
    }

    /**
     * Clear List<String> data.
     *
     * @param key key
     */
    public void clearListString(String key) {
        int size = getInt(key + "size");
        if (0 == size) {
            return;
        }
        clearValueByKey(key + "size");
        for (int i = 0; i < size; i++) {
            clearValueByKey(key + i);
        }
    }

    /**
     * Clear ListString's one data.
     *
     * @param key key
     * @param str str
     */
    public void clearListStringOne(String key, String str) {
        int size = getInt(key + "size");
        if (0 == size) {
            return;
        }
        List<String> list = getListString(key);
        for (String string : list) {
            if (string.equals(str)) {
                list.remove(str);
                saveListString(key, list);
            }
        }
    }

    /**
     * Clear data corresponding to key.
     *
     * @param key key
     */
    public void clearValueByKey(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear all data.
     */
    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
