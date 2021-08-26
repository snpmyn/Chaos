package com.chaos.util.java.json;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import timber.log.Timber;

/**
 * Created on 2018/4/3.
 *
 * @author xxx
 * @desc JSON 转化
 */
public class JsonTransform {
    /**
     * assets 转 JSON
     *
     * @param context    上下文
     * @param assetsPath assets 路径
     * @return JSON
     */
    public String jsonTransformFromAssets(@NonNull Context context, String assetsPath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(assetsPath)));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Timber.e(e);
        }
        return stringBuilder.toString();
    }

    /**
     * 文件转 JSON
     *
     * @param filePath 文件路径
     * @return JSON
     */
    public String jsonTransformFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Timber.e(e);
        }
        return stringBuilder.toString();
    }
}
