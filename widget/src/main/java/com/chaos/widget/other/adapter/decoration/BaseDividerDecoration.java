package com.chaos.widget.other.adapter.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created on 2020-09-21
 *
 * @author zsp
 * @desc 间距装饰基类
 */
public abstract class BaseDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_SIZE = 2;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    protected enum DividerType {
        /**
         * 位图
         */
        DRAWABLE,
        /**
         * 画笔
         */
        PAINT,
        /**
         * 颜色
         */
        COLOR
    }

    DividerType dividerType = DividerType.DRAWABLE;
    private final VisibilityProvider visibilityProvider;
    PaintProvider paintProvider;
    private ColorProvider colorProvider;
    DrawableProvider drawableProvider;
    SizeProvider sizeProvider;
    private final boolean showLastDivider;
    boolean positionInsideItem;
    private Paint paint;

    BaseDividerDecoration(@NotNull Builder builder) {
        if (builder.mPaintProvider != null) {
            dividerType = DividerType.PAINT;
            paintProvider = builder.mPaintProvider;
        } else if (builder.mColorProvider != null) {
            dividerType = DividerType.COLOR;
            colorProvider = builder.mColorProvider;
            paint = new Paint();
            setSizeProvider(builder);
        } else {
            if (builder.mDrawableProvider == null) {
                TypedArray typedArray = builder.context.obtainStyledAttributes(ATTRS);
                final Drawable divider = typedArray.getDrawable(0);
                typedArray.recycle();
                drawableProvider = (position, parent) -> divider;
            } else {
                drawableProvider = builder.mDrawableProvider;
            }
            sizeProvider = builder.mSizeProvider;
        }
        visibilityProvider = builder.mVisibilityProvider;
        showLastDivider = builder.mShowLastDivider;
        positionInsideItem = builder.mPositionInsideItem;
    }

    private void setSizeProvider(@NotNull Builder builder) {
        sizeProvider = builder.mSizeProvider;
        if (sizeProvider == null) {
            sizeProvider = (position, parent) -> DEFAULT_SIZE;
        }
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }
        int itemCount = adapter.getItemCount();
        int lastDividerOffset = getLastDividerOffset(parent);
        int validChildCount = parent.getChildCount();
        int lastChildPosition = -1;
        for (int i = 0; i < validChildCount; i++) {
            View child = parent.getChildAt(i);
            int childPosition = parent.getChildLayoutPosition(child);
            if (childPosition < lastChildPosition) {
                // Avoid remaining divider when animation starts.
                continue;
            }
            lastChildPosition = childPosition;
            if (!showLastDivider && childPosition >= itemCount - lastDividerOffset) {
                // Don't draw divider for last line if showLastDivider = false.
                continue;
            }
            if (wasDividerAlreadyDrawn(childPosition, parent)) {
                // No need to draw divider again as it was drawn already by previous column.
                continue;
            }
            int groupIndex = getGroupIndex(childPosition, parent);
            if (visibilityProvider.shouldHideDivider(groupIndex, parent)) {
                continue;
            }
            Rect rect = getDividerBound(groupIndex, parent, child);
            switch (dividerType) {
                case DRAWABLE:
                    Drawable drawable = drawableProvider.drawableProvider(groupIndex, parent);
                    drawable.setBounds(rect);
                    drawable.draw(c);
                    break;
                case PAINT:
                    paint = paintProvider.dividerPaint(groupIndex, parent);
                    c.drawLine(rect.left, rect.top, rect.right, rect.bottom, paint);
                    break;
                case COLOR:
                    paint.setColor(colorProvider.dividerColor(groupIndex, parent));
                    paint.setStrokeWidth(sizeProvider.dividerSize(groupIndex, parent));
                    c.drawLine(rect.left, rect.top, rect.right, rect.bottom, paint);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     *
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
        int lastDividerOffset = getLastDividerOffset(parent);
        if (!showLastDivider && position >= itemCount - lastDividerOffset) {
            // Don't set item offset for last line if showLastDivider = false.
            return;
        }
        int groupIndex = getGroupIndex(position, parent);
        if (visibilityProvider.shouldHideDivider(groupIndex, parent)) {
            return;
        }
        setItemOffsets(outRect, groupIndex, parent);
    }

    /**
     * Check if RecyclerView is reverse layout.
     *
     * @param parent RecyclerView
     * @return True if RecyclerView is reverse layout
     */
    boolean areReverseLayout(@NotNull RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getReverseLayout();
        } else {
            return false;
        }
    }

    /**
     * In the case showLastDivider = false,
     * Returns offset for how many views we don't have to draw a divider for,
     * for LinearLayoutManager it is as simple as not drawing the last child divider,
     * but for a GridLayoutManager it needs to take the span count for the last items into account
     * until we use the span count configured for the grid.
     *
     * @param parent RecyclerView
     * @return Offset for how many views we don't have to draw a divider or 1 if its a LinearLayoutManager
     */
    private int getLastDividerOffset(@NotNull RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanCount = gridLayoutManager.getSpanCount();
            int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
            for (int i = itemCount - 1; i >= 0; i--) {
                if (spanSizeLookup.getSpanIndex(i, spanCount) == 0) {
                    return itemCount - i;
                }
            }
        }
        return 1;
    }

    /**
     * Determines whether divider was already drawn for the row the item is in, effectively only makes sense for a grid.
     *
     * @param position current view position to draw divider
     * @param parent   RecyclerView
     * @return True if the divider can be skipped as it is in the same row as the previous one
     */
    private boolean wasDividerAlreadyDrawn(int position, @NotNull RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanCount = gridLayoutManager.getSpanCount();
            return spanSizeLookup.getSpanIndex(position, spanCount) > 0;
        }
        return false;
    }

    /**
     * Returns a group index for GridLayoutManager.
     * For LinearLayoutManager, always returns position.
     *
     * @param position current view position to draw divider
     * @param parent   RecyclerView
     * @return Group index of items
     */
    private int getGroupIndex(int position, @NotNull RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanCount = gridLayoutManager.getSpanCount();
            return spanSizeLookup.getSpanGroupIndex(position, spanCount);
        }
        return position;
    }

    /**
     * 获取分割线界限
     *
     * @param position 位置
     * @param parent   列表视图
     * @param child    视图
     * @return 矩形
     */
    protected abstract Rect getDividerBound(int position, RecyclerView parent, View child);

    /**
     * 设置条目偏移
     *
     * @param outRect  矩形
     * @param position 位置
     * @param parent   列表视图
     */
    protected abstract void setItemOffsets(Rect outRect, int position, RecyclerView parent);

    /**
     * Interface for controlling divider visibility.
     */
    public interface VisibilityProvider {
        /**
         * Returns true if divider should be hidden.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return True if the divider at position should be hidden
         */
        boolean shouldHideDivider(int position, RecyclerView parent);
    }

    /**
     * Interface for controlling paint instance for divider drawing.
     */
    public interface PaintProvider {
        /**
         * Returns {@link Paint} for divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Paint instance
         */
        Paint dividerPaint(int position, RecyclerView parent);
    }

    /**
     * Interface for controlling divider color.
     */
    public interface ColorProvider {
        /**
         * Returns {@link android.graphics.Color} value of divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Color value
         */
        int dividerColor(int position, RecyclerView parent);
    }

    /**
     * Interface for controlling drawable object for divider drawing.
     */
    public interface DrawableProvider {
        /**
         * Returns drawable instance for divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Drawable instance
         */
        Drawable drawableProvider(int position, RecyclerView parent);
    }

    /**
     * Interface for controlling divider size.
     */
    public interface SizeProvider {
        /**
         * Returns size value of divider.
         * Height for horizontal divider, width for vertical divider.
         *
         * @param position Divider position (or group index for GridLayoutManager)
         * @param parent   RecyclerView
         * @return Size of divider
         */
        int dividerSize(int position, RecyclerView parent);
    }

    public static class Builder<T extends Builder> {
        private final Context context;
        Resources resources;
        private PaintProvider mPaintProvider;
        private ColorProvider mColorProvider;
        private DrawableProvider mDrawableProvider;
        private SizeProvider mSizeProvider;
        private VisibilityProvider mVisibilityProvider = (position, parent) -> false;
        private boolean mShowLastDivider = false;
        private boolean mPositionInsideItem = false;

        public Builder(@NotNull Context context) {
            this.context = context;
            resources = context.getResources();
        }

        public T paint(final Paint paint) {
            return paintProvider((position, parent) -> paint);
        }

        T paintProvider(PaintProvider provider) {
            mPaintProvider = provider;
            return (T) this;
        }

        public T color(final int color) {
            return colorProvider((position, parent) -> color);
        }

        public T colorResId(@ColorRes int colorId) {
            return color(ContextCompat.getColor(context, colorId));
        }

        T colorProvider(ColorProvider provider) {
            mColorProvider = provider;
            return (T) this;
        }

        public T drawable(@DrawableRes int id) {
            return drawable(ContextCompat.getDrawable(context, id));
        }

        public T drawable(final Drawable drawable) {
            return drawableProvider((position, parent) -> drawable);
        }

        T drawableProvider(DrawableProvider provider) {
            mDrawableProvider = provider;
            return (T) this;
        }

        public T size(final int size) {
            return sizeProvider((position, parent) -> size);
        }

        public T sizeResId(@DimenRes int dimenResId) {
            return size(resources.getDimensionPixelSize(dimenResId));
        }

        T sizeProvider(SizeProvider provider) {
            mSizeProvider = provider;
            return (T) this;
        }

        public T visibilityProvider(VisibilityProvider visibilityProvider) {
            mVisibilityProvider = visibilityProvider;
            return (T) this;
        }

        public T showLastDivider() {
            mShowLastDivider = true;
            return (T) this;
        }

        public T positionInsideItem(boolean positionInsideItem) {
            mPositionInsideItem = positionInsideItem;
            return (T) this;
        }

        void checkBuilderParams() {
            if (null != mPaintProvider) {
                if (null != mColorProvider) {
                    throw new IllegalArgumentException("Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.");
                }
                if (null != mSizeProvider) {
                    throw new IllegalArgumentException("Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.");
                }
            }
        }
    }
}
