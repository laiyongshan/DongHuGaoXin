package suncere.androidapp.lib.customview;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TabBar extends LinearLayout {

    public int mSelectTextColor;//default Color.WHITE 选择字体颜色
    public int mUnSelectTextColor;//default Color.parseColor("#808080") 灰色 没有选择字体颜色
    public int[] mSelectTextColorArray;
    public int[] mUnSelectTextColorArray;


    public int mUnSelectTabItemBgColor;//default Color.argb(0,0,0,0) 没有选中的item背景颜色
    public int mSelectTabVItemBgColor;//default Color.argb(0,0,0,0) 选中的item背景颜色
    public int mDefaultSelectIndex;//default 0

    public Boolean mIsFullScreen;//default false 是否全屏
    public Boolean mIsFragRemove;//default false 使用Remove frag的逻辑  true 使用hide frag的逻辑
    public Boolean mIsShowTabBarView;//default true 是否显示TabBarView

    public int[] mSelectImageVIconArray;
    public int[] mUnSelectImageVIconArray;
    public Class<?>[] mTabFragmentClassArray;
    public String[] mTextVTitleArray;

    public float mTabBarHeight; //default 49
    public float mTabBar_ImageV_Height; //default 25
    public float mTabBar_ImageV_Width;//default 25
    public float mTabBar_TextV_Height;//default WRAP_CONTENT
    public float mTabBar_TextV_Width;//default WRAP_CONTENT
    public int  mTabBarViewBackgroundColor;//default Color.rgb(0,0,0) tabbar背景颜色


    private List<Fragment> fragLst;
    private Map<Object, Fragment> tabFragS;
    private FrameLayout tabFragContainer;

    public LinearLayout getTabBarView() {
        return tabBarView;
    }

//    public void setTabBarView(LinearLayout tabBarView) {
//        this.tabBarView = tabBarView;
//    }

    private LinearLayout tabBarView;
    private List<View> tabLst;
    private int preSelectIndex=-1;
    private Context context;
    private ScreenUtils screenUtils;
    private OnTabSelectedChangingListener onTabSelectedChangingListener;
    private OnTabSelectedFragmentListener mOnTabSelectedFragmentListener;

    public TabBar(Context context) {
        super(context);
        this.context = context;

        commonInit();
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        commonInit();
    }

    public TabBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    private void commonInit(){
        this.setOrientation(LinearLayout.VERTICAL);
        screenUtils = new ScreenUtils(context);
        mIsFullScreen = false;
        mIsFragRemove = false;
        mIsShowTabBarView = true;
        mTabBarHeight = 49;//ViewGroup.LayoutParams.WRAP_CONTENT;
        mTabBar_ImageV_Height = 25;
        mTabBar_ImageV_Width = 25;
        mTabBar_TextV_Height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTabBar_TextV_Width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTabBarViewBackgroundColor = Color.rgb(0,0,0);
        mUnSelectTabItemBgColor = Color.argb(0,0,0,0);
        mSelectTabVItemBgColor = Color.argb(0,0,0,0);
        mUnSelectTextColor = Color.parseColor("#808080");
        mSelectTextColor = Color.WHITE;
        mDefaultSelectIndex = 0;
    }

    public void refreshTabBar() {

        if (this.getChildCount() > 0){
            this.removeAllViews();
        }
        tabLst=new ArrayList<View>();
        fragLst=new ArrayList<Fragment>();
        tabFragS = new HashMap<Object, Fragment>();

        tabFragContainer=new FrameLayout(context);
//      tabFragContainer.setBackgroundColor(Color.BLUE);

        if (mIsShowTabBarView) {
            tabBarView = new LinearLayout(context);
            tabBarView.setBackgroundColor(mTabBarViewBackgroundColor);
//            setTabBarView(tabBarView);
        }
        if (!mIsFullScreen) {
            this.addView(tabFragContainer);
            if (mIsShowTabBarView) {
                this.addView(tabBarView);
            }
        }else {
            RelativeLayout relativeLayout = new RelativeLayout(context);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.addView(tabFragContainer);
            relativeLayout.addView(linearLayout);

            RelativeLayout.LayoutParams relativeLayoutLayoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            relativeLayoutLayoutParams.height= LayoutParams.MATCH_PARENT;
            relativeLayoutLayoutParams.width= LayoutParams.MATCH_PARENT;

            if (mIsShowTabBarView) {
                LinearLayout linearLayout2 = new LinearLayout(context);
                linearLayout2.addView(tabBarView);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                linearLayout2.setLayoutParams(params);
                relativeLayout.addView(linearLayout2);
            }
            this.addView(relativeLayout);

        }
        LayoutParams tabPageContLayoutParams=(LayoutParams) tabFragContainer.getLayoutParams();
        tabPageContLayoutParams.height= LayoutParams.MATCH_PARENT;
        tabPageContLayoutParams.width= LayoutParams.MATCH_PARENT;
        tabPageContLayoutParams.weight=1;


            if (mIsShowTabBarView) {
                LayoutParams tabContLayoutParams = (LayoutParams) tabBarView.getLayoutParams();
                tabContLayoutParams.height = (int) screenUtils.dp2px(mTabBarHeight);
                tabContLayoutParams.width = LayoutParams.MATCH_PARENT;
            }

            if (mIsShowTabBarView) {
                for (int i = 0; i < mSelectImageVIconArray.length; i++)
                    AddTab(i);

                if (mDefaultSelectIndex != -1)
                    On_TablClick_Listner.onClick(tabLst.get(mDefaultSelectIndex));
            }else  {

                AddTabFrag(mTabFragmentClassArray[mDefaultSelectIndex],mDefaultSelectIndex);
            }

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.BOTTOM;
        tabBarView.setLayoutParams(lp);
    }


    public void setOnTabSelectedChangingListener(
            OnTabSelectedChangingListener onTabSelectedChangingListener) {
        this.onTabSelectedChangingListener = onTabSelectedChangingListener;
    }

    public void setOnTabSelectedFragmentListener(OnTabSelectedFragmentListener onTabSelectedFragmentListener){
        mOnTabSelectedFragmentListener=onTabSelectedFragmentListener;
    }

    public void SwitchTabBar(int tabBarIndex){

        if (mIsShowTabBarView) {
            if (tabBarIndex < 0 || tabBarIndex > tabLst.size())
                return;
            View sender = tabLst.get(tabBarIndex);
            this.On_TablClick_Listner.onClick(sender);
        }else  {
            AddTabFrag(mTabFragmentClassArray[tabBarIndex], tabBarIndex);

        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.setOrientation(LinearLayout.VERTICAL);

    }

    Fragment currentFrag;

    private void AddTabFrag(Class<?> fragClass,int index)
    {

        FragmentManager fm=((FragmentActivity) this.getContext()).getSupportFragmentManager();
        FragmentTransaction   tran = fm.beginTransaction();

        if (mIsFragRemove) {
            if (currentFrag != null) {
                tran.remove(currentFrag);
            }

            FrameLayout layout = new FrameLayout(this.getContext());
            layout.setId(ViewIdGenerator.generateViewId());
            tabFragContainer.addView(layout);
            layout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

            Fragment frag = null;
            try {
                frag = (Fragment) fragClass.newInstance();
                tran.add(layout.getId(), frag);
                if (mOnTabSelectedFragmentListener!=null){
                    mOnTabSelectedFragmentListener.OnTabSelectedFragment(frag,index);
                }
                tran.show(frag);
                currentFrag = frag;

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {

            Collection<Fragment> aCollection = tabFragS.values();
            for (Fragment fragment : aCollection) {
               // if (fragment.isVisible()) {
                    tran.hide(fragment);
               // }
            }
            try {
                Fragment frag = tabFragS.get(fragClass.getName());
                if (frag == null) {
                    FrameLayout layout = new FrameLayout(this.getContext());
                    layout.setId(ViewIdGenerator.generateViewId());
                    tabFragContainer.addView(layout);
                    layout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                     frag = (Fragment)fragClass.newInstance();
                    tran.add(layout.getId(), frag);
                    fragLst.add(frag);
                    tabFragS.put(fragClass.getName(), frag);
                }
                if (mOnTabSelectedFragmentListener!=null){
                    mOnTabSelectedFragmentListener.OnTabSelectedFragment(frag,index);
                }
                tran.show(frag);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //tran.addToBackStack(null);
        tran.commit();
    }

    private void AddTab(int index)
    {
        LinearLayout tab=new LinearLayout(this.getContext());
        tab.setOrientation(VERTICAL);
        tab.setBackgroundColor(mUnSelectTabItemBgColor);
        tabBarView.addView(tab);

        LayoutParams params=(LayoutParams) tab.getLayoutParams();
        params.weight=1;
        params.height = (int)screenUtils.dp2px(mTabBarHeight);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tab.setClickable(true);

        ImageView img=new ImageView(getContext());
        tab.addView(img);
        params=(LayoutParams) img.getLayoutParams();
        params.width= (int)screenUtils.dp2px(mTabBar_ImageV_Width);
        params.height= (int)screenUtils.dp2px(mTabBar_ImageV_Height);
        params.gravity=Gravity.CENTER;
        params.topMargin= (int)screenUtils.dp2px(5);
        img.setImageResource(mUnSelectImageVIconArray[index] );

        TextView tv=new TextView(getContext());
        tab.addView(tv);
        params=(LayoutParams) tv.getLayoutParams();
        params.width = (int)screenUtils.dp2px(mTabBar_TextV_Width);
        params.height = (int)screenUtils.dp2px(mTabBar_TextV_Height);
        params.gravity=Gravity.CENTER;
        params.bottomMargin = (int)screenUtils.dp2px(5);
        tv.setTextColor(mUnSelectTextColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        tv.setText(mTextVTitleArray[index]);

        tab.setOnClickListener(On_TablClick_Listner);
        tabLst.add(tab);
    }

    OnClickListener On_TablClick_Listner =new OnClickListener() {

        @Override
        public void onClick(View v) {
            int index=tabLst.indexOf(v);
            if(preSelectIndex==index)return;
            if(onTabSelectedChangingListener!=null && !onTabSelectedChangingListener.OnTabSelectedChanging(v, index))return;

            if(preSelectIndex!=-1)
            {
                ViewGroup tab= (ViewGroup) tabLst.get(preSelectIndex);
                tab.setBackgroundColor(mUnSelectTabItemBgColor);
                ((ImageView)tab.getChildAt(0)).setImageResource(mUnSelectImageVIconArray[preSelectIndex]);
                if (mUnSelectTextColorArray !=null && mUnSelectTextColorArray.length>0){
                    ((TextView)tab.getChildAt(1) ).setTextColor(mUnSelectTextColorArray[preSelectIndex]);
                }else{
                    ((TextView)tab.getChildAt(1) ).setTextColor(mUnSelectTextColor);
                }
            }
            preSelectIndex=index;
            ViewGroup currTab=(ViewGroup) v;
            currTab.setBackgroundColor(mSelectTabVItemBgColor);
            ((ImageView)currTab.getChildAt(0)).setImageResource(mSelectImageVIconArray[preSelectIndex]);

            if (mSelectTextColorArray !=null && mSelectTextColorArray.length>0){
                ((TextView)currTab.getChildAt(1) ).setTextColor(mSelectTextColorArray[preSelectIndex]);
            }else{
                ((TextView)currTab.getChildAt(1) ).setTextColor(mSelectTextColor);
            }

            AddTabFrag(mTabFragmentClassArray[index],index);
        }
    };

    public interface OnTabSelectedChangingListener
    {
        boolean OnTabSelectedChanging(View sender, int index);
    }

    public interface OnTabSelectedFragmentListener
    {
        void OnTabSelectedFragment(Fragment frag, int index);
    }


    public static class ViewIdGenerator {
        private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

        public static int generateViewId() {

            if (Build.VERSION.SDK_INT < 17) {
                for (;;) {
                    final int result = sNextGeneratedId.get();
                    // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                    int newValue = result + 1;
                    if (newValue > 0x00FFFFFF)
                        newValue = 1; // Roll over to 1, not 0.
                    if (sNextGeneratedId.compareAndSet(result, newValue)) {
                        return result;
                    }
                }
            } else {
                return View.generateViewId();
            }

        }
    }
}
