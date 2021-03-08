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
 * @desc 垂直间距装饰
 */
public class VerticalDividerDecoration extends BaseDividerDecoration {
    private final MarginProvider marginProvider;

    private VerticalDividerDecoration(Builder builder) {
        super(builder);
        marginProvider = builder.mMarginProvider;
    }

    @Override
    protected Rect getDividerBound(int position, @NotNull RecyclerView parent, @NotNull View child) {
        Rect rect = new Rect(0, 0, 0, 0);
        int transitionX = (int) child.getTranslationX();
        int transitionY = (int) child.getTranslationY();
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        rect.top = parent.getPaddingTop() + marginProvider.dividerTopMargin(position, parent) + transitionY;
        rect.bottom = parent.getHeight() - parent.getPaddingBottom() - marginProvider.dividerBottomMargin(position, parent) + transitionY;
        int dividerSize = getDividerSize(position, parent);
        boolean areReverseLayout = areReverseLayout(parent);
        if (dividerType == DividerType.DRAWABLE) {
            // set left and right position of divider
            if (areReverseLayout) {
                rect.right = child.getLeft() - layoutParams.leftMargin + transitionX;
                rect.left = rect.right - dividerSize;
            } else {
                rect.left = child.getRight() + layoutParams.rightMargin + transitionX;
                rect.right = rect.left + dividerSize;
            }
        } else {
            // set center point of divider
            int halfSize = dividerSize / 2;
            if (areReverseLayout) {
                rect.left = child.getLeft() - layoutParams.leftMargin - halfSize + transitionX;
            } else {
                rect.left = child.getRight() + layoutParams.rightMargin + halfSize + transitionX;
            }
            rect.right = rect.left;
        }
        if (positionInsideItem) {
            if (areReverseLayout) {
                rect.left += dividerSize;
                rect.right += dividerSize;
            } else {
                rect.left -= dividerSize;
                rect.right -= dividerSize;
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
            outRect.set(getDividerSize(position, parent), 0, 0, 0);
        } else {
            outRect.set(0, 0, getDividerSize(position, parent), 0);
        }
    }

    private int getDividerSize(int position, RecyclerView parent) {
        if (paintProvider != null) {
            return (int) paintProvider.dividerPaint(position, parent).getStrokeWidth();
        } else if (sizeProvider != null) {
            return sizeProvider.dividerSize(position, parent);
        } else if (drawableProvider != null) {
            Drawable drawable = drawableProvider.drawableProvider(position, parent);
            return drawable.getIntrinsicWidth();
        }
        throw new RuntimeException("failed to get size");
    }

    /**
     * Interface for controlling divider margin.
     */
    public interface MarginProvider {
        /**
         * Returns top margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Top margin
         */
        int dividerTopMargin(int position, RecyclerView parent);

        /**
         * Returns bottom margin of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Bottom margin
         */
        int dividerBottomMargin(int position, RecyclerView parent);
    }

    public static class Builder extends BaseDividerDecoration.Builder<Builder> {
        private MarginProvider mMarginProvider = new MarginProvider() {
            @Override
            public int dividerTopMargin(int position, RecyclerView parent) {
                return 0;
            }

            @Override
            public int dividerBottomMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder margin(final int topMargin, final int bottomMargin) {
            return marginProvider(new MarginProvider() {
                @Override
                public int dividerTopMargin(int position, RecyclerView parent) {
                    return topMargin;
                }

                @Override
                public int dividerBottomMargin(int position, RecyclerView parent) {
                    return bottomMargin;
                }
            });
        }

        public Builder margin(int verticalMargin) {
            return margin(verticalMargin, verticalMargin);
        }

        public Builder marginDimen(@DimenRes int topMarginDimenResId, @DimenRes int bottomMarginDimenResId) {
            return margin(resources.getDimensionPixelSize(topMarginDimenResId), resources.getDimensionPixelSize(bottomMarginDimenResId));
        }

        public Builder marginDimen(@DimenRes int verticalMarginDimenResId) {
            return marginDimen(verticalMarginDimenResId, verticalMarginDimenResId);
        }

        public Builder marginProvider(MarginProvider marginProvider) {
            mMarginProvider = marginProvider;
            return this;
        }

        public VerticalDividerDecoration build() {
            checkBuilderParams();
            return new VerticalDividerDecoration(this);
        }
    }
}
