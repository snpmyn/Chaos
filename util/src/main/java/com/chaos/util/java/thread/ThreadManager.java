package com.chaos.util.java.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created on 2018/9/6.
 *
 * @author 郑少鹏
 * @desc 线程管理器
 * 依赖 org.apache.commons:commons-lang3:xxx
 */
public class ThreadManager {
    @Contract(" -> new")
    public static @NotNull ScheduledExecutorService stepScheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    }
}
