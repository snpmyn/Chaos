package com.chaos.fairy.utils.resource;

import android.app.Application;

import com.chaos.fairy.utils.log.LogUtils;

import java.lang.reflect.Field;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc 资源工具类
 */
public class ResourceUtils {
    /**
     * 获取资源 ID
     *
     * @param name 名称
     * @param c    Class<?>
     * @return 资源 ID
     */
    public static int getResId(String name, Class<?> c) {
        try {
            Field field = c.getDeclaredField(name);
            return field.getInt(field);
        } catch (Exception e) {
            LogUtils.exception(e);
            return -1;
        }
    }

    /**
     * 获取位图资源 ID
     *
     * @param application  应用
     * @param name         名称
     * @param defaultResId 默认资源 ID
     * @return 位图资源 ID
     */
    public static int getDrawableResIdByName(Application application, String name, int defaultResId) {
        try {
            int drawableResId = application.getResources().getIdentifier(name, "drawable", application.getPackageName());
            return drawableResId > 0 ? drawableResId : defaultResId;
        } catch (Exception e) {
            LogUtils.exception(e);
            return defaultResId;
        }
    }
}
