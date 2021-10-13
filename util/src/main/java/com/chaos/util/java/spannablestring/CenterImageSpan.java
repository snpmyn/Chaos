package com.chaos.util.java.spannablestring;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * Created on 2021/10/12
 *
 * @author zsp
 * @desc 居中 ImageSpan
 */
public class CenterImageSpan extends ImageSpan {
    public CenterImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        Drawable drawable = getDrawable();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        // Y 轴位移
        int translateY = (((y + fontMetricsInt.descent + y + fontMetricsInt.ascent) / 2) - (drawable.getBounds().bottom / 2));
        canvas.save();
        // 绘图位移一段距离
        canvas.translate(x, translateY);
        drawable.draw(canvas);
        canvas.restore();
    }
}

