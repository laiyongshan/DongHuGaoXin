package androidapp.donghugaoxin.suncere.com.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.LeakCanaryUtil;
import androidapp.donghugaoxin.suncere.com.presenter.UpDataAPPPresenter;
import butterknife.ButterKnife;
import suncere.androidapp.lib.customview.TabBar;
import suncere.androidapp.lib.mvp.ui.MyApplication;
import suncere.androidapp.lib.mvp.ui.baseui.MvpActivity;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.updataapp.UpdateManager;
import suncere.androidapp.lib.utils.StatusBarUtil;

/**
 * @author lys
 * @time 2018/5/23 14:29
 * @desc:普通管理员界面
 */

public class MainActivity2 extends MvpActivity<UpDataAPPPresenter> implements IBaseView {
    TabBar mTabBar;

    UpDataAPPPresenter mUpDataAPPPresenter; // 更新APP


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        StatusBarUtil.setColor(this, Color.rgb(0,0,0));
        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        mTabBar= findViewById(R.id.tabbar2);
        mTabBar.mTextVTitleArray = new String[]{"首页","地图","排名","设置"};
        mTabBar.mSelectTextColor= Color.parseColor("#0F91FF");
        mTabBar.mUnSelectTextColor=Color.parseColor("#666666");
        mTabBar.mTabBarViewBackgroundColor=Color.parseColor("#102834");
        mTabBar.mSelectImageVIconArray = new int[]{R.mipmap.icon_home_s,R.mipmap.icon_ditu_s,R.mipmap.icon_paiming_s,R.mipmap.icon_setting_s};
        mTabBar.mUnSelectImageVIconArray = new int[]{R.mipmap.icon_home_u,R.mipmap.icon_ditu_u,R.mipmap.icon_paiming_u,R.mipmap.icon_setting_u};
        mTabBar.mTabFragmentClassArray = new Class<?>[]{ListFragment.class,MapFragment.class,SortFragment.class,WarnFragment.class};
//        mTabBar.mDefaultSelectIndex=1;
        mTabBar.refreshTabBar();
//        mTabBar.SwitchTabBar(0);
    }


    @Override
    protected UpDataAPPPresenter createPresenter() {
        mUpDataAPPPresenter=new UpDataAPPPresenter(this);
        return mUpDataAPPPresenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUpDataAPPPresenter.updataAPP(MyApplication.getMyApplicationVersionName());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ShowExitAPP();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {
        if (response != null) {
            String downloadUrl = response.toString();
            if (downloadUrl.startsWith("http") && downloadUrl.endsWith(".apk"))
                new UpdateManager(this).checkUpdate(downloadUrl);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakCanaryUtil.fixMemoryLeak(this);
    }
}
