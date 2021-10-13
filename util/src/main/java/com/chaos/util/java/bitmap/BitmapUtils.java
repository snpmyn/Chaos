package com.chaos.util.java.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created on 2021/10/12
 *
 * @author zsp
 * @desc BitmapUtils
 */
public class BitmapUtils {
    /**
     * 旋转 Bitmap
     *
     * @param bitmap       位图
     * @param rotateDegree 旋转角度
     * @return 旋转后 Bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 从路径获位图
     *
     * @param path 路径
     * @return 位图
     */
    public static Bitmap getBitmapFromPath(String path) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int saleX = (options.outWidth / 800);
        int saleY = (options.outHeight / 800);
        options.inSampleSize = Math.max(saleX, saleY);
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
}
