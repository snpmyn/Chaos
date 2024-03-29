package com.chaos.widget.dialog.sweetalertdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;

import com.chaos.widget.R;

/**
 * Created on 2017/11/3.
 *
 * @author xxx
 * @desc Rotate3dAnimation
 */
public class Rotate3dAnimation extends Animation {
    private static final int ROLL_BY_X = 0;
    private static final int ROLL_BY_Y = 1;
    private static final int ROLL_BY_Z = 2;
    private final float mFromDegrees;
    private final float mToDegrees;
    private final int mRollType;
    private int mPivotTypeWithX = ABSOLUTE;
    private int mPivotTypeWithY = ABSOLUTE;
    private float mPivotValueWithX = 0.0F;
    private float mPivotValueWithY = 0.0F;
    private float mPivotX;
    private float mPivotY;
    private Camera mCamera;

    public Rotate3dAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Rotate3dAnimation);
        mFromDegrees = a.getFloat(R.styleable.Rotate3dAnimation_fromDeg, 0.0f);
        mToDegrees = a.getFloat(R.styleable.Rotate3dAnimation_toDeg, 0.0f);
        mRollType = a.getInt(R.styleable.Rotate3dAnimation_rollType, ROLL_BY_X);
        Description d = parseValue(a.peekValue(R.styleable.Rotate3dAnimation_pivotX));
        mPivotTypeWithX = d.type;
        mPivotValueWithX = d.value;
        d = parseValue(a.peekValue(R.styleable.Rotate3dAnimation_pivotY));
        mPivotTypeWithY = d.type;
        mPivotValueWithY = d.value;
        a.recycle();
        initializePivotPoint();
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotX = 0.0F;
        mPivotY = 0.0F;
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotTypeWithX = ABSOLUTE;
        mPivotTypeWithY = ABSOLUTE;
        mPivotValueWithX = pivotX;
        mPivotValueWithY = pivotY;
        initializePivotPoint();
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees, int pivotTypeWithX, float pivotValueWithX, int pivotTypeWithY, float pivotValueWithY) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotValueWithX = pivotValueWithX;
        mPivotTypeWithX = pivotTypeWithX;
        mPivotValueWithY = pivotValueWithY;
        mPivotTypeWithY = pivotTypeWithY;
        initializePivotPoint();
    }

    @NonNull
    private Description parseValue(TypedValue value) {
        Description d = new Description();
        if (null == value) {
            d.type = ABSOLUTE;
            d.value = 0;
        } else {
            if (value.type == TypedValue.TYPE_FRACTION) {
                d.type = (value.data & TypedValue.COMPLEX_UNIT_MASK) == TypedValue.COMPLEX_UNIT_FRACTION_PARENT ? RELATIVE_TO_PARENT : RELATIVE_TO_SELF;
                d.value = TypedValue.complexToFloat(value.data);
                return d;
            } else if (value.type == TypedValue.TYPE_FLOAT) {
                d.type = ABSOLUTE;
                d.value = value.getFloat();
                return d;
            } else if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                d.type = ABSOLUTE;
                d.value = value.data;
                return d;
            }
        }
        d.type = ABSOLUTE;
        d.value = 0.0F;
        return d;
    }

    private void initializePivotPoint() {
        if (mPivotTypeWithX == ABSOLUTE) {
            mPivotX = mPivotValueWithX;
        }
        if (mPivotTypeWithY == ABSOLUTE) {
            mPivotY = mPivotValueWithY;
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mPivotX = resolveSize(mPivotTypeWithX, mPivotValueWithX, width, parentWidth);
        mPivotY = resolveSize(mPivotTypeWithY, mPivotValueWithY, height, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, @NonNull Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = (fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime));
        final Matrix matrix = t.getMatrix();
        mCamera.save();
        switch (mRollType) {
            case ROLL_BY_X:
                mCamera.rotateX(degrees);
                break;
            case ROLL_BY_Y:
                mCamera.rotateY(degrees);
                break;
            case ROLL_BY_Z:
                mCamera.rotateZ(degrees);
                break;
            default:
                break;
        }
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mPivotX, -mPivotY);
        matrix.postTranslate(mPivotX, mPivotY);
    }

    protected static class Description {
        public int type;
        public float value;
    }
}