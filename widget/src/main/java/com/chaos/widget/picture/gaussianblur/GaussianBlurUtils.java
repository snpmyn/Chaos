package com.chaos.widget.picture.gaussianblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.NonNull;

/**
 * Created on 2021/5/28
 *
 * @author zsp
 * @desc 高斯模糊工具类
 */
public class GaussianBlurUtils {
    /**
     * 缩放比例
     */
    private static final float SCALE = 1.0F;

    /**
     * 高斯模糊
     *
     * @param context 上下文
     * @param bitmap  位图
     * @param radius  模糊度
     * @return 高斯模糊后位图
     */
    public static Bitmap gaussianBlur(Context context, @NonNull Bitmap bitmap, float radius) {
        // 原位图宽高缩放
        int width = Math.round(bitmap.getWidth() * SCALE);
        int height = Math.round(bitmap.getHeight() * SCALE);
        // 缩放后位图作预渲染图
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建 RenderScript 内核对象
        RenderScript renderScript = RenderScript.create(context);
        // 创建模糊效果的 RenderScript 的工具对象
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        // RenderScript 并没用 VM 分配内存，故需用 Allocation 创建和分配内存空间。
        // 创建 Allocation 对象时内存为空，需 copyTo() 填充数据。
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        // 渲染模糊度（25.0F 最大）
        scriptIntrinsicBlur.setRadius(radius);
        // 输入内存
        scriptIntrinsicBlur.setInput(tmpIn);
        // 保存输出数据到输出内存
        scriptIntrinsicBlur.forEach(tmpOut);
        // 填充数据到 Allocation
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}
