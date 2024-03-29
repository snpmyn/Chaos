package com.chaos.scan.listener;

/**
 * Created on 2019/8/31.
 *
 * @author 郑少鹏
 * @desc 扫码监听
 */
public interface ScanCodeListener {
    /**
     * 扫码成功
     *
     * @param result 摄像头扫码回调该法时 result 定有值，解本地图或 Bitmap 时 result 或 null。
     */
    void onScanQrCodeSuccess(String result);

    /**
     * 扫码开相机错
     */
    void onScanQrCodeOpenCameraError();
}
