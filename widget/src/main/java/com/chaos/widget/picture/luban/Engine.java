package com.chaos.widget.picture.luban;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

import com.chaos.widget.picture.luban.provider.InputStreamProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import value.WidgetMagic;

/**
 * @decs: Engine
 * Responsible for starting compress and managing active and cached resources.
 * @author: 郑少鹏
 * @date: 2019/8/28 19:03
 */
class Engine {
    private final InputStreamProvider srcImg;
    private final File tagImg;
    private final boolean focusAlpha;
    private int srcWidth;
    private int srcHeight;

    Engine(@NonNull InputStreamProvider srcImg, File tagImg, boolean focusAlpha) throws IOException {
        this.tagImg = tagImg;
        this.srcImg = srcImg;
        this.focusAlpha = focusAlpha;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeStream(srcImg.open(), null, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
    }

    private int computeSize() {
        srcWidth = (((srcWidth % 2) == 1) ? (srcWidth + 1) : srcWidth);
        srcHeight = (((srcHeight % 2) == 1) ? (srcHeight + 1) : srcHeight);
        int longSide = Math.max(srcWidth, srcHeight);
        int shortSide = Math.min(srcWidth, srcHeight);
        float scale = ((float) shortSide / longSide);
        if ((scale <= 1) && (scale > WidgetMagic.FLOAT_ZERO_DOT_FIVE_SIX_TWO_FIVE)) {
            if (longSide < WidgetMagic.INT_ONE_THOUSAND_SIX_HUNDRED_SIXTY_FOUR) {
                return 1;
            } else if (longSide < WidgetMagic.INT_FOUR_THOUSAND_NINE_HUNDRED_NINETY) {
                return 2;
            } else if ((longSide > WidgetMagic.INT_FOUR_THOUSAND_NINE_HUNDRED_NINETY) && (longSide < WidgetMagic.INT_ONE_THOUSAND_TWO_HUNDRED_FIFTY)) {
                return 4;
            } else {
                return longSide / 1280;
            }
        } else if ((scale <= WidgetMagic.FLOAT_ZERO_DOT_FIVE_SIX_TWO_FIVE) && (scale > WidgetMagic.FLOAT_ZERO_DOT_FIVE)) {
            return ((longSide / 1280 == 0) ? 1 : longSide / 1280);
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }

    private Bitmap rotatingImage(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    File compress() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = computeSize();
        Bitmap tagBitmap = BitmapFactory.decodeStream(srcImg.open(), null, options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (Checker.SINGLE.areJpg(srcImg.open())) {
            tagBitmap = rotatingImage(tagBitmap, Checker.SINGLE.getOrientation(srcImg.open()));
        }
        if (null != tagBitmap) {
            tagBitmap.compress(focusAlpha ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
            tagBitmap.recycle();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(tagImg);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        byteArrayOutputStream.close();
        return tagImg;
    }
}