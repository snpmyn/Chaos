package widget.module.textview;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.textview.align.ActionMenu;
import com.chaos.widget.textview.align.ActionMenuCallBack;
import com.chaos.widget.textview.align.HorizontalAlignAndVerticalAlignKit;
import com.chaos.widget.textview.align.VerticalTextView;
import com.example.chaos.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @desc: 垂直 TextView 页
 * @author: zsp
 * @date: 2022/3/23 4:15 下午
 */
public class VerticalTextViewActivity extends BaseActivity implements ActionMenuCallBack {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.verticalTextViewActivityHsv)
    HorizontalScrollView verticalTextViewActivityHsv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.verticalTextViewActivityVtv)
    VerticalTextView verticalTextViewActivityVtv;
    /**
     * 水平对齐和垂直对齐配套元件
     */
    private HorizontalAlignAndVerticalAlignKit horizontalAlignAndVerticalAlignKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_vertical_text_view;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {

    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        // VerticalTextView
        verticalTextViewActivityVtv.setAreLeftToRight(true)
                .setLineSpacingExtra(10)
                .setCharSpacingExtra(2)
                .setAreUnderLineText(true)
                .setAreShowActionMenu(true)
                .setUnderLineColor(R.color.purple_500)
                .setUnderLineWidth(1.5f)
                .setUnderLineOffset(2)
                .setTextHighlightColor(R.color.purple_500)
                .setCustomActionMenuCallBack(this);
        // 水平对齐和垂直对齐配套元件
        horizontalAlignAndVerticalAlignKit = new HorizontalAlignAndVerticalAlignKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        verticalTextViewActivityVtv.setCustomActionMenuCallBack(this);
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        verticalTextViewActivityVtv.setText("壬戌之秋，七月既望，蘇子與客泛舟遊於赤壁之下。清風徐來，水波不興。舉酒屬客，誦明月之詩，歌窈窕之章。少焉，月出於東山之上，徘徊於鬥牛之間。白露橫江，水光接天。縱壹葦之所如，淩萬頃之茫然。浩浩乎如馮虛禦風，而不知其所止；飄飄乎如遺世獨立,羽化而登仙。︵馮 通：憑︶</p>　　於是飲酒樂甚，扣舷而歌之。歌曰：﹃桂桌兮蘭槳，擊空明兮溯流光。渺渺兮予懷，望美人兮天壹方。﹄客有吹洞簫者，倚歌而和之。其聲嗚嗚然，如怨如慕，如泣如訴；余音裊裊，不絕如縷。舞幽壑之潛蛟，泣孤舟之嫠婦。</p>　　蘇子愀然，正襟危坐，而問客曰：﹃何為其然也？﹄客曰：﹃﹁月明星稀，烏鵲南飛。﹂此非曹孟德之詩乎？西望夏口，東望武昌，山川相繆，郁乎蒼蒼，此非孟德之困於周郎者乎？方其破荊州，下江陵，順流而東也，舳艫千裏，旌旗蔽空，釃酒臨江，橫槊賦詩，固壹世之雄也，而今安在哉？況吾與子漁樵於江諸之上，侶魚蝦而友麋鹿，駕壹葉之扁舟，舉匏樽以相屬。寄蜉蝣於天地，渺滄海之壹粟。哀吾生之須臾，羨長 江之無窮。挾飛仙以遨遊，抱明月而長終。知不可乎驟得，托遺響於悲風。﹄</p>　　蘇子曰：﹃客亦知夫水與月乎？逝者如斯，而未嘗往也；盈虛者如彼，而卒莫消長也。蓋將自其變者而觀之，則天地曾不能以壹瞬；自其不變者而觀之，則物與我皆無盡也，而又何羨乎！且夫天地之間，物各有主，茍非吾之所有，雖壹毫而莫取。惟江上之清風，與山間之明月，耳得之而為聲，目遇之而成色，取之無禁，用之不竭。是造物者之無盡藏也，而吾與子之所共適。﹄︵共適 壹作：共食︶</p>　　客喜而笑，洗盞更酌。肴核既盡，杯盤狼籍。相與枕藉乎舟中，不知東方之既白。</p>\n");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.verticalTextViewActivityMbFromLeftToRight, R.id.verticalTextViewActivityMbFromRightToLeft,
            R.id.verticalTextViewActivityMbShowUnderLine, R.id.verticalTextViewActivityMbHideUnderLine})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 从左到右
            case R.id.verticalTextViewActivityMbFromLeftToRight:
                horizontalAlignAndVerticalAlignKit.setVerticalAlignAreFromLeftToRight(verticalTextViewActivityVtv, true, verticalTextViewActivityHsv, View.FOCUS_LEFT);
                break;
            // 从右到左
            case R.id.verticalTextViewActivityMbFromRightToLeft:
                horizontalAlignAndVerticalAlignKit.setVerticalAlignAreFromLeftToRight(verticalTextViewActivityVtv, false, verticalTextViewActivityHsv, View.FOCUS_RIGHT);
                break;
            // 显下划线
            case R.id.verticalTextViewActivityMbShowUnderLine:
                horizontalAlignAndVerticalAlignKit.setVerticalAlignAreUnderLineText(verticalTextViewActivityVtv, true);
                break;
            // 隐下划线
            case R.id.verticalTextViewActivityMbHideUnderLine:
                horizontalAlignAndVerticalAlignKit.setVerticalAlignAreUnderLineText(verticalTextViewActivityVtv, false);
                break;
            default:
                break;
        }
    }

    /**
     * 创动作菜单
     *
     * @param actionMenu 动作菜单
     * @return false 留默动作菜单、true 移默动作菜单
     */
    @Override
    public boolean onCreateActionMenu(ActionMenu actionMenu) {
        List<String> actionMenuItemTitleList = new ArrayList<>();
        actionMenuItemTitleList.add(getString(R.string.translate));
        actionMenuItemTitleList.add(getString(R.string.share));
        horizontalAlignAndVerticalAlignKit.createActionMenu(actionMenu, actionMenuItemTitleList);
        return false;
    }

    /**
     * 动作菜单条目点击
     *
     * @param item            条目
     * @param selectedContent 已选内容
     */
    @Override
    public void onActionMenuItemClick(String item, String selectedContent) {
        ToastKit.showShort(item);
    }
}