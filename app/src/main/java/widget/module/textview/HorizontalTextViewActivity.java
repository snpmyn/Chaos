package widget.module.textview;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.textview.align.ActionMenu;
import com.chaos.widget.textview.align.ActionMenuCallBack;
import com.chaos.widget.textview.align.HorizontalAlignAndVerticalAlignKit;
import com.chaos.widget.textview.align.LeftAndRightAlignTextView;
import com.example.chaos.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @desc: 水平 TextView 页
 * @author: zsp
 * @date: 2022/3/23 4:16 下午
 */
public class HorizontalTextViewActivity extends BaseActivity implements ActionMenuCallBack {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.horizontalTextViewActivityLaratv)
    LeftAndRightAlignTextView horizontalTextViewActivityLaratv;
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
        return R.layout.activity_horizontal_text_view;
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
        horizontalAlignAndVerticalAlignKit = new HorizontalAlignAndVerticalAlignKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        horizontalTextViewActivityLaratv.setCustomActionMenuCallBack(this);
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.horizontalTextViewActivityMbNativeAlignment,
            R.id.horizontalTextViewActivityMbDistributeAlignment,
            R.id.horizontalTextViewActivityMbChinese,
            R.id.horizontalTextViewActivityMbEnglish,
            R.id.horizontalTextViewActivityMbMix})
    public void onViewClicked(@NonNull View view) {
        String chinese = "壬戌之秋，七月既望，苏子与客泛舟游于赤壁之下。清风徐来，水波不兴。举酒属客，诵明月之诗，歌窈窕之章。少焉，月出于东山之上，徘徊于斗牛之间。白露横江，水光接天。纵一苇之所如，凌万顷之茫然。浩浩乎如冯虚御风，而不知其所止；飘飘乎如遗世独立,羽化而登仙。︵冯 通：凭︶</p>　　于是饮酒乐甚，扣舷而歌之。歌曰：﹃桂棹兮兰桨，击空明兮溯流光。渺渺兮予怀，望美人兮天一方。﹄客有吹洞箫者，倚歌而和之。其声呜呜然，如怨如慕，如泣如诉；余音袅袅，不绝如缕。舞幽壑之潜蛟，泣孤舟之嫠妇。</p>　　苏子愀然，正襟危坐，而问客曰：﹃何为其然也？﹄客曰：﹃﹁月明星稀，乌鹊南飞。﹂此非曹孟德之诗乎？西望夏口，东望武昌，山川相缪，郁乎苍苍，此非孟德之困于周郎者乎？方其破荆州，下江陵，顺流而东也，舳舻千里，旌旗蔽空，酾酒临江，横槊赋诗，固一世之雄也，而今安在哉？况吾与子渔樵于江渚之上，侣鱼虾而友麋鹿，驾一叶之扁舟，举匏樽以相属。寄蜉蝣于天地，渺沧海之一粟。哀吾生之须臾，羡长 江之无穷。挟飞仙以遨游，抱明月而长终。知不可乎骤得，托遗响于悲风。﹄</p>　　苏子曰：﹃客亦知夫水与月乎？逝者如斯，而未尝往也；盈虚者如彼，而卒莫消长也。盖将自其变者而观之，则天地曾不能以一瞬；自其不变者而观之，则物与我皆无尽也，而又何羡乎！且夫天地之间，物各有主，苟非吾之所有，虽一毫而莫取。惟江上之清风，与山间之明月，耳得之而为声，目遇之而成色，取之无禁，用之不竭。是造物者之无尽藏也，而吾与子之所共适。﹄︵共适 一作：共食︶</p>　　客喜而笑，洗盏更酌。肴核既尽，杯盘狼籍。相与枕藉乎舟中，不知东方之既白。</p>";
        String english = "renxu autumn July, Jiwang, Suzi and guest boating tours under Chibi. Xu breeze, rippleless. Wine is off, chanting the moon poetry, song slim chapter. A little while, months of Dongshan, wandering in the bullfight. Dew Yokoe, Shuiguang next day. Even a reed like, Ling Wan Qing loss. The vast Feng seems such as virtual Yufeng, and not to stop; waving as aloof, emergence and immortal. (Feng Tong) </p>    and drinking music, I and song. The Song said: \"Zhao Gui Xi Lan paddle upstream come blow out light and air. You come to my arms, at beauty come one day.\" A blowing flute, and the Yi song. The hum of course, such as resentment, such as mu of bamboo, linger on faintly, as if weeping and complaining. Dance Youhe the cry of a hidden dragon boat. </p> Suzi stern, sat, and asked the guests said: \"what is it?\" The guests said: \"yuemingxingxi, Ukraine magpie flying south.\" This is not a poem of Cao Mengde? Seomang Xiakou, looking east Wuchang mountains, Miao Yu, between green and the non shuro Meng moral trapped in between? The broken Jingzhou, Jiangling, East River, a convoy of ships thousands of miles, empty word flags, pour wine, having a lance sideward and poetizing Linjiang, a hero of the age and where is also solid,? Kuangwu and son, in Jiang Nagisa, Lu fish and shrimp and the friends of the elk, riding a leaf boat, belonging to lift bottle gourd. Send ephemera in the world, a boundless sea. Sad moment of my life, the infinite envy of Yangtze river. With the fly to roam, hold the moon and long end. Don't know will suddenly left, supporting ring in Beifeng.\" </p>";
        String mix = "壬戌之秋，renxu autumn July, 七月既望，Jiwang, 苏子与客泛舟游于赤壁之下。Suzi and guest boating tours under Chibi.清风徐来，水波不兴。 Xu breeze, rippleless. 举酒属客，Wine is off, 诵明月之诗，chanting the moon poetry, 歌窈窕之章。song slim chapter. 少焉，A little while, 月出于东山之上，months of Dongshan, 徘徊于斗牛之间。wandering in the bullfight. 白露横江，Dew Yokoe, 水光接天。Shuiguang next day. 纵一苇之所如，Even a reed like, 凌万顷之茫然。Ling Wan Qing loss. 浩浩乎如冯虚御风，The vast Feng seems such as virtual Yufeng, 而不知其所止；and not to stop; 飘飘乎如遗世独立,waving as aloof, 羽化而登仙。emergence and immortal. (冯 通：凭)(Feng Tong)</p> 　　于是饮酒乐甚，then drinking music very, 扣舷而歌之。 I and song. 歌曰：“桂棹兮兰桨，击空明兮溯流光。渺渺兮予怀，望美人兮天一方。”。The Song said: \"Zhao Gui Xi Lan paddle upstream come blow out light and air. You come to my arms, at beauty come one day.\"。客有吹洞箫者，A blowing flute, 倚歌而和之。 and the Yi song. 其声呜呜然，The hum of course, 如怨如慕，such as resentment, 如泣如诉；such as mu of bamboo, 余音袅袅，linger on faintly, 不绝如缕。as if weeping and complaining. 舞幽壑之潜蛟，泣孤舟之嫠妇。Dance Youhe the cry of a hidden dragon boat.</p>";
        switch (view.getId()) {
            // 原生对齐
            case R.id.horizontalTextViewActivityMbNativeAlignment:
                horizontalAlignAndVerticalAlignKit.setHorizontalAlignAreTextJustify(horizontalTextViewActivityLaratv, false);
                break;
            // 分散对齐
            case R.id.horizontalTextViewActivityMbDistributeAlignment:
                horizontalAlignAndVerticalAlignKit.setHorizontalAlignAreTextJustify(horizontalTextViewActivityLaratv, true);
                break;
            // 中文
            case R.id.horizontalTextViewActivityMbChinese:
                horizontalAlignAndVerticalAlignKit.setHorizontalAlignText(horizontalTextViewActivityLaratv, chinese);
                break;
            // 英文
            case R.id.horizontalTextViewActivityMbEnglish:
                horizontalAlignAndVerticalAlignKit.setHorizontalAlignText(horizontalTextViewActivityLaratv, english);
                break;
            // 混编
            case R.id.horizontalTextViewActivityMbMix:
                horizontalAlignAndVerticalAlignKit.setHorizontalAlignText(horizontalTextViewActivityLaratv, mix);
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