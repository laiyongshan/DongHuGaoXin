package androidapp.donghugaoxin.suncere.com.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.LeakCanaryUtil;
import androidapp.donghugaoxin.suncere.com.Utils.NotificationCheckUtil;
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
 * @desc:高级管理员用户界面
 */

public class MainActivity extends MvpActivity<UpDataAPPPresenter> implements IBaseView {

    TabBar mTabBar;

    UpDataAPPPresenter mUpDataAPPPresenter; // 更新APP


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this,Color.argb(00,67,153,235));

        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        mTabBar= findViewById(R.id.tabbar);
        mTabBar.mTextVTitleArray = new String[]{"首页","地图","站点","排名","报警信息"};
        mTabBar.mSelectTextColor= Color.parseColor("#0F91FF");
        mTabBar.mUnSelectTextColor=Color.parseColor("#666666");
        mTabBar.mTabBarViewBackgroundColor=Color.parseColor("#102834");
        mTabBar.mSelectImageVIconArray = new int[]{R.mipmap.icon_home_s,R.mipmap.icon_ditu_s,R.mipmap.icon_station_s,R.mipmap.icon_paiming_s,R.mipmap.icon_warn_s};
        mTabBar.mUnSelectImageVIconArray = new int[]{R.mipmap.icon_home_u,R.mipmap.icon_ditu_u,R.mipmap.icon_station_u,R.mipmap.icon_paiming_u,R.mipmap.icon_warn_u};
        mTabBar.mTabFragmentClassArray = new Class<?>[]{HomeFragment.class,MapFragment.class,ListFragment.class,SortFragment.class,WarnFragment.class};

        mTabBar.refreshTabBar();
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

        Log.i("Lys", NotificationCheckUtil.notificationIsOpen(this)+"");
    }


    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2500) {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ShowExitAPP();
        }
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
