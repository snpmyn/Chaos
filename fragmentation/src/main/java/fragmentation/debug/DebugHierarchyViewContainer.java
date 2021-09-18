package fragmentation.debug;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chaos.fragmentation.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @decs: DebugHierarchyViewContainer
 * @author: 郑少鹏
 * @date: 2019/5/20 9:26
 */
public class DebugHierarchyViewContainer extends ScrollView {
    private Context mContext;
    private LinearLayout mLinearLayout;
    private LinearLayout mTitleLayout;
    private int mItemHeight;
    private int mPadding;

    public DebugHierarchyViewContainer(Context context) {
        super(context);
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        horizontalScrollView.addView(mLinearLayout);
        addView(horizontalScrollView);
        mItemHeight = dip2px(50);
        mPadding = dip2px(16);
    }

    private int dip2px(float dp) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    public void bindFragmentRecords(List<DebugFragmentRecord> fragmentRecords) {
        mLinearLayout.removeAllViews();
        LinearLayout linearLayout = getTitleLayout();
        mLinearLayout.addView(linearLayout);
        if (null == fragmentRecords) {
            return;
        }
        DebugHierarchyViewContainer.this.setView(fragmentRecords, 0, null);
    }

    @NonNull
    private LinearLayout getTitleLayout() {
        if (null != mTitleLayout) {
            return mTitleLayout;
        }
        mTitleLayout = new LinearLayout(mContext);
        mTitleLayout.setPadding(dip2px(24), dip2px(24), 0, dip2px(8));
        mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams flParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLayout.setLayoutParams(flParams);
        TextView title = new TextView(mContext);
        title.setText(R.string.fragmentationStackView);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        title.setLayoutParams(layoutParams);
        mTitleLayout.addView(title);
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.fragmentation_help);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = dip2px(16);
        params.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(params);
        mTitleLayout.setOnClickListener(v -> Toast.makeText(mContext, R.string.fragmentationStackHelp, Toast.LENGTH_LONG).show());
        mTitleLayout.addView(imageView);
        return mTitleLayout;
    }

    private void setView(final @NotNull List<DebugFragmentRecord> fragmentRecordList, final int hierarchy, final TextView tvItem) {
        for (int i = (fragmentRecordList.size() - 1); i >= 0; i--) {
            DebugFragmentRecord child = fragmentRecordList.get(i);
            int tempHierarchy = hierarchy;
            final TextView childTvItem;
            childTvItem = getTextView(child, tempHierarchy);
            childTvItem.setTag(R.id.fragmentationHierarchy, tempHierarchy);
            final List<DebugFragmentRecord> childFragmentRecord = child.debugFragmentRecords;
            if ((null != childFragmentRecord) && (childFragmentRecord.size() > 0)) {
                tempHierarchy++;
                childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_arrow_right, 0, 0, 0);
                final int finalChildHierarchy = tempHierarchy;
                childTvItem.setOnClickListener(v -> {
                    if (null != v.getTag(R.id.fragmentationAreExpand)) {
                        boolean areExpand = (boolean) v.getTag(R.id.fragmentationAreExpand);
                        if (areExpand) {
                            childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_arrow_right, 0, 0, 0);
                            DebugHierarchyViewContainer.this.removeView(finalChildHierarchy);
                        } else {
                            handleExpandView(childFragmentRecord, finalChildHierarchy, childTvItem);
                        }
                        v.setTag(R.id.fragmentationAreExpand, !areExpand);
                    } else {
                        childTvItem.setTag(R.id.fragmentationAreExpand, true);
                        handleExpandView(childFragmentRecord, finalChildHierarchy, childTvItem);
                    }
                });
            } else {
                childTvItem.setPadding(childTvItem.getPaddingLeft() + mPadding, 0, mPadding, 0);
            }
            if (null == tvItem) {
                mLinearLayout.addView(childTvItem);
            } else {
                mLinearLayout.addView(childTvItem, mLinearLayout.indexOfChild(tvItem) + 1);
            }
        }
    }

    private void handleExpandView(List<DebugFragmentRecord> childFragmentRecord, int finalChildHierarchy, TextView childTvItem) {
        DebugHierarchyViewContainer.this.setView(childFragmentRecord, finalChildHierarchy, childTvItem);
        childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_arrow_expand, 0, 0, 0);
    }

    private void removeView(int hierarchy) {
        int size = mLinearLayout.getChildCount();
        for (int i = (size - 1); i >= 0; i--) {
            View view = mLinearLayout.getChildAt(i);
            if ((null != view.getTag(R.id.fragmentationHierarchy)) && ((int) view.getTag(R.id.fragmentationHierarchy) >= hierarchy)) {
                mLinearLayout.removeView(view);
            }
        }
    }

    private @NotNull TextView getTextView(DebugFragmentRecord fragmentRecord, int hierarchy) {
        TextView textView = new TextView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        textView.setLayoutParams(layoutParams);
        if (hierarchy == 0) {
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(16);
        }
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding((int) (mPadding + hierarchy * mPadding * 1.5), 0, mPadding, 0);
        textView.setCompoundDrawablePadding(mPadding / 2);
        TypedArray typedArray = mContext.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        textView.setBackground(typedArray.getDrawable(0));
        typedArray.recycle();
        textView.setText(fragmentRecord.fragmentName);
        return textView;
    }
}
