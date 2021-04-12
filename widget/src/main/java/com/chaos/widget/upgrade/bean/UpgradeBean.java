package com.chaos.widget.upgrade.bean;

/**
 * Created on 2019/7/15.
 *
 * @author 郑少鹏
 * @desc 更新
 */
public class UpgradeBean {
    /**
     * 内更新否
     */
    private final boolean innerUpgrade;
    /**
     * 最低版本号
     */
    private final int minVersionCode;
    /**
     * 新版本号
     */
    private final int newVersionCode;
    /**
     * 新版本名
     */
    private final String newVersionName;
    /**
     * 下载链接
     */
    private final String downloadUrl;
    /**
     * 文件长度
     */
    private final int fileLength;
    /**
     * 强制更新
     */
    private final boolean forceUpdate;
    /**
     * 更新描述
     */
    private final String updateDescription;

    /**
     * constructor
     *
     * @param innerUpgrade      内更新否
     * @param minVersionCode    最低版本号
     * @param newVersionCode    新版本号
     * @param newVersionName    新版本名
     * @param downloadUrl       下载链接
     * @param fileLength        文件长度
     * @param forceUpdate       强制更新
     * @param updateDescription 更新描述
     */
    public UpgradeBean(boolean innerUpgrade, int minVersionCode, int newVersionCode, String newVersionName, String downloadUrl, int fileLength, boolean forceUpdate, String updateDescription) {
        this.innerUpgrade = innerUpgrade;
        this.minVersionCode = minVersionCode;
        this.newVersionCode = newVersionCode;
        this.newVersionName = newVersionName;
        this.downloadUrl = downloadUrl;
        this.fileLength = fileLength;
        this.forceUpdate = forceUpdate;
        this.updateDescription = updateDescription;
    }

    public boolean isInnerUpgrade() {
        return innerUpgrade;
    }

    public int getMinVersionCode() {
        return minVersionCode;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public String getNewVersionName() {
        return newVersionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public int getFileLength() {
        return fileLength;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }
}
