package com.chaos.util.java.number;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Random;

/**
 * Created on 2021/6/24
 *
 * @author zsp
 * @desc 随机数工具类
 */
public class RandomNumberUtils {
    /**
     * 某范围随机数
     * <p>
     * 需获随机数范围 [2,100]，
     * 假设返伪随机数范围 [0,N) 即 [0,N - 1]，
     * 对所得数模 99 计算所得数范围 [0,98]，
     * 结果加 2 即 [2,100]。
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @return 随机数
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 随机指定范围 N 个不重复数
     * <p>
     * 最简单最易理解两重循环去重。
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     * @return 随机数结果集
     */
    @Nullable
    public static int[] randomCommon(int min, int max, int n) {
        if ((max < min) || (n > (max - min + 1))) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = ((int) (Math.random() * (max - min)) + min);
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /**
     * 随机指定范围 N 个不重复数
     * <p>
     * HashSet 仅存不同值。
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     * @param set 随机数结果集
     */
    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if ((n > (max - min + 1)) || (max < min)) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调 Math.random() 法
            int num = ((int) (Math.random() * (max - min)) + min);
            // 存不同数到 HashSet
            set.add(num);
        }
        int setSize = set.size();
        // 存入的数小指定生成个数则递归再生剩余个数的随机数（循环直至达指定大小）
        if (setSize < n) {
            // 递归
            randomSet(min, max, n - setSize, set);
        }
    }

    /**
     * 随机指定范围 N 个不重复数
     * <p>
     * 于初始化无重复待选数组随机生一数放入结果，
     * 待选数组被随机到的数用待选数组 len - 1 下标对应数替换，
     * 然后从 len - 2 随机生下一随机数（类推）。
     *
     * @param max 指定范围最大值
     * @param min 指定范围最小值
     * @param n   随机数个数
     * @return 随机数结果集
     */
    @Nullable
    public static int[] randomArray(int min, int max, int n) {
        int len = (max - min + 1);
        if ((max < min) || (n > len)) {
            return null;
        }
        // 初始给定范围待选数组
        int[] source = new int[len];
        for (int i = min; i < (min + len); i++) {
            source[i - min] = i;
        }
        int[] result = new int[n];
        Random rd = new Random();
        int index;
        for (int i = 0; i < result.length; i++) {
            // 待选数组 0 到 len-2 随机一下标
            index = Math.abs(rd.nextInt() % len--);
            // 放随机到的数到结果集
            result[i] = source[index];
            // 待选数组被随机到的数用待选数组 len-1 下标对应数替换
            source[index] = source[len];
        }
        return result;
    }

    /**
     * 生成随机数
     *
     * @param length 长度
     * @return 随机数
     */
    public long generateRandomNumber(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int firstNumber = (random.nextInt(9) + 1);
        stringBuilder.append(firstNumber);
        for (int i = 0; i < (length - 1); ++i) {
            stringBuilder.append(random.nextInt(10));
        }
        return Long.parseLong(stringBuilder.toString());
    }

    /**
     * 生成 16 进制随机值
     *
     * @param length 长度
     * @return 16 进制随机值
     */
    public String generateHexRandomValue(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char temp = 0;
            int key = (int) (random.nextDouble() * 2);
            switch (key) {
                case 0:
                    // 生成随机数字
                    temp = (char) (Math.random() * 10 + 48);
                    break;
                case 1:
                    // 生成 a - f
                    temp = (char) (Math.random() * 6 + 'a');
                    break;
                default:
                    break;
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}
