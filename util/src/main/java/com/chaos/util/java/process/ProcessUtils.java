package com.chaos.util.java.process;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import timber.log.Timber;

/**
 * Created on 2021/1/11
 *
 * @author zsp
 * @desc 进程工具类
 */
public class ProcessUtils {
    /**
     * 获取进程 ID
     *
     * @return 进程 ID
     */
    public static @NotNull String getProcessId() {
        return String.valueOf(android.os.Process.myPid());
    }

    /**
     * 获取进程名
     *
     * @param pid 进程 ID
     * @return 进程名
     */
    public static @Nullable String getProcessName(int pid) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = bufferedReader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            Timber.e(throwable);
        } finally {
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException exception) {
                Timber.e(exception);
            }
        }
        return null;
    }
}
