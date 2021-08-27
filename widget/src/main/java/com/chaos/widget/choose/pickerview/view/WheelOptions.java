package com.chaos.widget.choose.pickerview.view;

import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.widget.R;
import com.chaos.widget.choose.pickerview.adapter.ArrayWheelAdapter;
import com.chaos.widget.choose.pickerview.listener.OnOptionsSelectChangeListener;
import com.chaos.widget.choose.wheelview.listener.OnItemSelectedListener;
import com.chaos.widget.choose.wheelview.view.WheelView;

import java.util.List;

/**
 * @decs: WheelOptions
 * @author: 郑少鹏
 * @date: 2018/4/3 17:39
 */
public class WheelOptions<T> {
    private View view;
    private final WheelView wvOption1;
    private final WheelView wvOption2;
    private final WheelView wvOption3;
    private List<T> mOptions1Items;
    private List<List<T>> mOptions2Items;
    private List<List<List<T>>> mOptions3Items;
    /**
     * 默联动
     */
    private boolean linkage = true;
    /**
     * 切换时还原第一项
     */
    private final boolean areRestoreItem;
    private OnItemSelectedListener onItemSelectedListener;
    private OnOptionsSelectChangeListener onOptionsSelectChangeListener;
    /**
     * 文本 / 分割线色
     */
    private int textColorOut;
    private int textColorCenter;
    private int dividerColor;
    private WheelView.DividerType dividerType;
    /**
     * 条目间距倍数
     */
    private float lineSpacingMultiplier;

    WheelOptions(@NonNull View view, boolean areRestoreItem) {
        super();
        this.areRestoreItem = areRestoreItem;
        this.view = view;
        // 初始化显示数据
        this.wvOption1 = view.findViewById(R.id.options1);
        this.wvOption2 = view.findViewById(R.id.options2);
        this.wvOption3 = view.findViewById(R.id.options3);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    void setPicker(List<T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items) {
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        // 选项 1 显示数据
        wvOption1.setWheelAdapter(new ArrayWheelAdapter<>(mOptions1Items));
        // 初始化显示数据
        wvOption1.setCurrentItem(0);
        // 选项 2
        if (null != mOptions2Items) {
            // 显示数据
            wvOption2.setWheelAdapter(new ArrayWheelAdapter<>(mOptions2Items.get(0)));
        }
        // 初始化显示数据
        wvOption2.setCurrentItem(wvOption2.getCurrentItem());
        // 选项 3
        if (null != mOptions3Items) {
            // 显示数据
            wvOption3.setWheelAdapter(new ArrayWheelAdapter<>(mOptions3Items.get(0).get(0)));
        }
        wvOption3.setCurrentItem(wvOption3.getCurrentItem());
        wvOption1.setAreOptions(true);
        wvOption2.setAreOptions(true);
        wvOption3.setAreOptions(true);
        if (null == this.mOptions2Items) {
            wvOption2.setVisibility(View.GONE);
        } else {
            wvOption2.setVisibility(View.VISIBLE);
        }
        if (null == this.mOptions3Items) {
            wvOption3.setVisibility(View.GONE);
        } else {
            wvOption3.setVisibility(View.VISIBLE);
        }
        // 联动监听器
        // 仅 1 级联动数据
        // 上一 opt2 选中位
        // 新 opt2 位，旧位没超数据范围则沿用旧位，否选最后一项
        // 仅 2 级联动数据，滑动第 1 项回调
        OnItemSelectedListener wheelListenerOption1 = index -> {
            int opt2Select = 0;
            if (null == mOptions2Items) {
                // 仅1级联动数据
                if (null != onOptionsSelectChangeListener) {
                    onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), 0, 0);
                }
            } else {
                if (!areRestoreItem) {
                    // 上一 opt2 选中位
                    opt2Select = wvOption2.getCurrentItem();
                    // 新 opt2 位，旧位没超数据范围则沿用旧位，否选最后一项
                    opt2Select = Math.min(opt2Select, mOptions2Items.get(index).size() - 1);
                }
                wvOption2.setWheelAdapter(new ArrayWheelAdapter<>(mOptions2Items.get(index)));
                wvOption2.setCurrentItem(opt2Select);
                if (null != mOptions3Items) {
                    onItemSelectedListener.onItemSelected(opt2Select);
                } else {
                    // 仅 2 级联动数据，滑动第 1 项回调
                    if (null != onOptionsSelectChangeListener) {
                        onOptionsSelectChangeListener.onOptionsSelectChanged(index, opt2Select, 0);
                    }
                }
            }
        };
        onItemSelectedListener = index -> {
            if (null != mOptions3Items) {
                int opt1Select = wvOption1.getCurrentItem();
                opt1Select = Math.min(opt1Select, mOptions3Items.size() - 1);
                index = Math.min(index, mOptions2Items.get(opt1Select).size() - 1);
                int opt3 = 0;
                if (!areRestoreItem) {
                    // wv_option3.getCurrentItem() 上一 opt3 选中位
                    // 新 opt3 位，旧位没超数据范围则用旧位，否选最后一项
                    opt3 = Math.min(wvOption3.getCurrentItem(), mOptions3Items.get(opt1Select).get(index).size() - 1);
                }
                wvOption3.setWheelAdapter(new ArrayWheelAdapter<>(mOptions3Items.get(wvOption1.getCurrentItem()).get(index)));
                wvOption3.setCurrentItem(opt3);
                // 3 级联动数据实时回调
                if (null != onOptionsSelectChangeListener) {
                    onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, opt3);
                }
            } else {
                // 仅 2 级联动数据，滑动第 2 项回调
                if (null != onOptionsSelectChangeListener) {
                    onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, 0);
                }
            }
        };
        // 添联动监听
        if ((null != options1Items) && linkage) {
            wvOption1.setOnItemSelectedListener(wheelListenerOption1);
        }
        if ((null != options2Items) && linkage) {
            wvOption2.setOnItemSelectedListener(onItemSelectedListener);
        }
        if ((null != options3Items) && linkage && (null != onOptionsSelectChangeListener)) {
            wvOption3.setOnItemSelectedListener(index -> onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), wvOption2.getCurrentItem(), index));
        }
    }

    /**
     * 不联动
     *
     * @param options1Items options1Items
     * @param options2Items options2Items
     * @param options3Items options3Items
     */
    void setNnPicker(List<T> options1Items, List<T> options2Items, List<T> options3Items) {
        // 选项 1 显示数据
        wvOption1.setWheelAdapter(new ArrayWheelAdapter<>(options1Items));
        // 初始化显示数据
        wvOption1.setCurrentItem(0);
        // 选项 2
        if (null != options2Items) {
            // 显示数据
            wvOption2.setWheelAdapter(new ArrayWheelAdapter<>(options2Items));
        }
        // 初始化显示数据
        wvOption2.setCurrentItem(wvOption2.getCurrentItem());
        // 选项 3
        if (null != options3Items) {
            // 显示数据
            wvOption3.setWheelAdapter(new ArrayWheelAdapter<>(options3Items));
        }
        wvOption3.setCurrentItem(wvOption3.getCurrentItem());
        wvOption1.setAreOptions(true);
        wvOption2.setAreOptions(true);
        wvOption3.setAreOptions(true);
        if (null != onOptionsSelectChangeListener) {
            wvOption1.setOnItemSelectedListener(index -> onOptionsSelectChangeListener.onOptionsSelectChanged(index, wvOption2.getCurrentItem(), wvOption3.getCurrentItem()));
        }
        if (null == options2Items) {
            wvOption2.setVisibility(View.GONE);
        } else {
            wvOption2.setVisibility(View.VISIBLE);
            if (null != onOptionsSelectChangeListener) {
                wvOption2.setOnItemSelectedListener(index -> onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), index, wvOption3.getCurrentItem()));
            }
        }
        if (null == options3Items) {
            wvOption3.setVisibility(View.GONE);
        } else {
            wvOption3.setVisibility(View.VISIBLE);
            if (null != onOptionsSelectChangeListener) {
                wvOption3.setOnItemSelectedListener(index -> onOptionsSelectChangeListener.onOptionsSelectChanged(wvOption1.getCurrentItem(), wvOption2.getCurrentItem(), index));
            }
        }
    }

    void setTextContentSize(int textSize) {
        wvOption1.setTextSize(textSize);
        wvOption2.setTextSize(textSize);
        wvOption3.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wvOption1.setTextColorOut(textColorOut);
        wvOption2.setTextColorOut(textColorOut);
        wvOption3.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wvOption1.setTextColorCenter(textColorCenter);
        wvOption2.setTextColorCenter(textColorCenter);
        wvOption3.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wvOption1.setDividerColor(dividerColor);
        wvOption2.setDividerColor(dividerColor);
        wvOption3.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wvOption1.setDividerType(dividerType);
        wvOption2.setDividerType(dividerType);
        wvOption3.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wvOption1.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvOption2.setLineSpacingMultiplier(lineSpacingMultiplier);
        wvOption3.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    /**
     * 选项单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    void setLabels(String label1, String label2, String label3) {
        if (null != label1) {
            wvOption1.setLabel(label1);
        }
        if (null != label2) {
            wvOption2.setLabel(label2);
        }
        if (null != label3) {
            wvOption3.setLabel(label3);
        }
    }

    /**
     * X 轴偏移量
     *
     * @param xOffsetOne   int
     * @param xOffsetTwo   int
     * @param xOffsetThree int
     */
    void setxOffsetOfText(int xOffsetOne, int xOffsetTwo, int xOffsetThree) {
        wvOption1.setxOffsetOfText(xOffsetOne);
        wvOption2.setxOffsetOfText(xOffsetTwo);
        wvOption3.setxOffsetOfText(xOffsetThree);
    }

    /**
     * 循环滚动
     *
     * @param cyclic 循环
     */
    public void setCyclic(boolean cyclic) {
        wvOption1.setCyclic(cyclic);
        wvOption2.setCyclic(cyclic);
        wvOption3.setCyclic(cyclic);
    }

    /**
     * 字体样式
     *
     * @param font 系统提供样式
     */
    void setTypeface(Typeface font) {
        wvOption1.setTypeface(font);
        wvOption2.setTypeface(font);
        wvOption3.setTypeface(font);
    }

    /**
     * 分设一二三级循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 循环
     */
    void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wvOption1.setCyclic(cyclic1);
        wvOption2.setCyclic(cyclic2);
        wvOption3.setCyclic(cyclic3);
    }

    /**
     * 返当前选中结果对应位置数组
     * <p>
     * 因支持三级联动，分三个级别索引 0、1、2。
     * 快滑未停点确定，进行判断，匹配数据越界设 0，防 index 出错致崩溃。
     *
     * @return 索引数组
     */
    int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wvOption1.getCurrentItem();
        if ((null != mOptions2Items) && (mOptions2Items.size() > 0)) {
            // 非空判断
            currentItems[1] = ((wvOption2.getCurrentItem() > (mOptions2Items.get(currentItems[0]).size() - 1)) ? 0 : wvOption2.getCurrentItem());
        } else {
            currentItems[1] = wvOption2.getCurrentItem();
        }
        if ((null != mOptions3Items) && (mOptions3Items.size() > 0)) {
            // 非空判断
            currentItems[2] = ((wvOption3.getCurrentItem() > (mOptions3Items.get(currentItems[0]).get(currentItems[1]).size() - 1)) ? 0 : wvOption3.getCurrentItem());
        } else {
            currentItems[2] = wvOption3.getCurrentItem();
        }
        return currentItems;
    }

    void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        } else {
            wvOption1.setCurrentItem(option1);
            wvOption2.setCurrentItem(option2);
            wvOption3.setCurrentItem(option3);
        }
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (null != mOptions1Items) {
            wvOption1.setCurrentItem(opt1Select);
        }
        if (null != mOptions2Items) {
            wvOption2.setWheelAdapter(new ArrayWheelAdapter<>(mOptions2Items.get(opt1Select)));
            wvOption2.setCurrentItem(opt2Select);
        }
        if (null != mOptions3Items) {
            wvOption3.setWheelAdapter(new ArrayWheelAdapter<>(mOptions3Items.get(opt1Select).get(opt2Select)));
            wvOption3.setCurrentItem(opt3Select);
        }
    }

    /**
     * 间距倍数（1.2 - 4.0f）
     *
     * @param lineSpacingMultiplier 间距倍数
     */
    void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 分割线色
     *
     * @param dividerColor 分割线色
     */
    void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 分割线类型
     *
     * @param dividerType 分割线类型
     */
    void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 分割线间文本色
     *
     * @param textColorCenter 分割线间文本色
     */
    void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 分割线外文本色
     *
     * @param textColorOut 分割线外文本色
     */
    void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * 仅显中间选中项 Label
     *
     * @param areCenterLabel 仅显中间选中项 Label 否
     */
    void setAreCenterLabel(boolean areCenterLabel) {
        this.wvOption1.setAreCenterLabel(areCenterLabel);
        this.wvOption2.setAreCenterLabel(areCenterLabel);
        this.wvOption3.setAreCenterLabel(areCenterLabel);
    }

    void setOnOptionsSelectChangeListener(OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        this.onOptionsSelectChangeListener = onOptionsSelectChangeListener;
    }

    void setLinkage() {
        this.linkage = false;
    }
}
