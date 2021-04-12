package com.chaos.widget.upgrade.event;

/**
 * @decs: 下载进度事件
 * @author: 郑少鹏
 * @date: 2018/8/22 8:46
 */
public class DownloadProgressEvent {
    /**
     * 文件总大小
     */
    private final long fileTotalSize;
    /**
     * 当前下载进度
     */
    private final long currentProgress;

    public DownloadProgressEvent(long fileTotalSize, long currentProgress) {
        this.fileTotalSize = fileTotalSize;
        this.currentProgress = currentProgress;
    }

    /**
     * 文件总大小
     *
     * @return 总大小
     */
    public long getTotal() {
        return fileTotalSize;
    }

    /**
     * 当前进度
     *
     * @return 当前进度
     */
    public long getProgress() {
        return currentProgress;
    }

    /**
     * 未下载完
     *
     * @return 未下载完
     */
    public boolean isNotDownloadFinished() {
        return fileTotalSize != currentProgress;
    }
}
