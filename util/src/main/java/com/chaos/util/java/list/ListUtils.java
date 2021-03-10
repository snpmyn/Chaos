package com.chaos.util.java.list;

import android.text.TextUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created on 2020/12/28
 *
 * @author zsp
 * @desc List 工具类
 */
public class ListUtils {
    /**
     * 排序去重
     *
     * @param inputStringList 输入集合
     * @return 去重后集合
     */
    @Contract("_ -> param1")
    public static List<String> removeDuplicationWithSort(@NotNull List<String> inputStringList) {
        for (int i = 0; i < inputStringList.size(); i++) {
            for (int j = (inputStringList.size() - 1); j > i; j--) {
                if (TextUtils.equals(inputStringList.get(i), inputStringList.get(j))) {
                    inputStringList.remove(j);
                }
            }
        }
        return inputStringList;
    }

    /**
     * 不排序去重
     *
     * @param inputStringList 输入集合
     * @return 去重后集合
     */
    @Contract("_ -> new")
    public static @NotNull List<String> removeDuplicationWithoutSort(List<String> inputStringList) {
        HashSet<String> stringHashSet = new HashSet<>(inputStringList);
        return new ArrayList<>(stringHashSet);
    }
}
