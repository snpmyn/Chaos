package com.chaos.widget.other.adapter.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2020-09-21
 *
 * @author zsp
 * @desc 水平间距装饰
 */
public class HorizontalDividerDecoration extends BaseDividerDecoration {
    private final MarginProvider marginProvider;

    private HorizontalDividerDecoration(Builder builder) {
        super(builder);
        marginProvider = builder.mMarginProvider;
    }

    @Override
    protected Rect getDividerBound(int position, @NotNull RecyclerView parent, @NotNull View child) {
        Rect rect = new Rect(0, 0, 0, 0);
        int transitionX = (int) child.getTranslationX();
        int transitionY = (int) child.getTranslationY();
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        rect.left = parent.getPaddingLeft() + marginProvider.dividerLeftMargin(position, parent) + transitionX;
        rect.right = parent.getWidth() - parent.getPaddingRight() - marginProvider.dividerRightMargin(position, parent) + transitionX;
        int dividerSize = getDividerSize(position, parent);
        boolean areReverseLayout = areReverseLayout(parent);
        if (dividerType == DividerType.DRAWABLE) {
            // set top and bottom position of divider
            if (areReverseLayout) {
                rect.bottom = child.getTop() - layoutParams.topMargin + transitionY;
                rect.top = rect.bottom - dividerSize;
            } else {
                rect.top = child.getBottom() + layoutParams.bottomMargin + transitionY;
                rect.bottom = rect.top + dividerSize;
            }
        } else {
            // set center point of divider
            int halfSize = dividerSize / 2;
            if (areReverseLayout) {
                rect.top = child.getTop() - layoutParams.topMargin - halfSize + transitionY;
            } else {
                rect.top = child.getBottom() + layoutParams.bottomMargin + halfSize + transitionY;
            }
            rect.bottom = rect.top;
        }
        if (positionInsideItem) {
            if (areReverseLayout) {
                rect.top += dividerSize;
                rect.bottom += dividerSize;
            } else {
                rect.top -= dividerSize;
                rect.bottom -= dividerSize;
            }
        }
        return rect;
    }

    @Override
    protected void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
        if (positionInsideItem) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (areReverseLayout(parent)) {
            outRect.set(0, getDividerSize(position, parent), 0, 0);
        } else {
            outRect.set(0, 0, 0, getDividerSize(position, parent));
        }
    }

    private int getDividerSize(int position, RecyclerView parent) {
        if (paintProvider != null) {
            return (int) paintProvider.dividerPaint(position, parent).getStrokeWidth();
        } else if (sizeProvider != null) {
            return sizeProvider.dividerSize(position, parent);
        } else if (drawableProvider != null) {
            Drawable drawable = drawableProvider.drawableProvider(position, parent);
            return drawable.getIntrinsicHeight();
        }
        throw new RuntimeException("failed to get size");
    }

    /**
     * Interface for controlling divider margin.
     */
    public interface MarginProvider {
        /**
         * Returns left margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Left margin
         */
        int dividerLeftMargin(int position, RecyclerView parent);

        /**
         * Returns right margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Right margin
         */
        int dividerRightMargin(int position, RecyclerView parent);
    }

    public static class Builder extends BaseDividerDecoration.Builder<Builder> {
        private MarginProvider mMarginProvider = new MarginProvider() {
            @Override
            public int dividerLeftMargin(int position, RecyclerView parent) {
                return 0;
            }

            @Override
            public int dividerRightMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder margin(final int leftMargin, final int rightMargin) {
            return marginProvider(new MarginProvider() {
                @Override
                public int dividerLeftMargin(int position, RecyclerView parent) {
                    return leftMargin;
                }

                @Override
                public int dividerRightMargin(int position, RecyclerView parent) {
                    return rightMargin;
                }
            });
        }

        public Builder margin(int horizontalMargin) {
            return margin(horizontalMargin, horizontalMargin);
        }

        public Builder marginDimen(@DimenRes int leftMarginDimenResId, @DimenRes int rightMarginDimenResId) {
            return margin(resources.getDimensionPixelSize(leftMarginDimenResId), resources.getDimensionPixelSize(rightMarginDimenResId));
        }

        public Builder marginDimen(@DimenRes int horizontalMarginDimenResId) {
            return marginDimen(horizontalMarginDimenResId, horizontalMarginDimenResId);
        }

        public Builder marginProvider(MarginProvider marginProvider) {
            mMarginProvider = marginProvider;
            return this;
        }

        public HorizontalDividerDecoration build() {
            checkBuilderParams();
            return new HorizontalDividerDecoration(this);
        }
    }
}