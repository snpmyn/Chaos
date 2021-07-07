package com.chaos.util.java.array;

/**
 * Created on 2021/7/7
 *
 * @author zsp
 * @desc 数组工具类
 */
public class ArrayUtils {
    /**
     * 数组为空
     *
     * @param array 数组
     * @param <T>   <T>
     * @return 数组为空否
     */
    public static <T> boolean arrayIsEmpty(T[] array) {
        return ((null == array) || (array.length == 0));
    }
}
