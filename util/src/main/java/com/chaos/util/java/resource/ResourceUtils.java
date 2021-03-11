package com.chaos.util.java.resource;

import com.chaos.util.java.log.LogUtils;

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
}
