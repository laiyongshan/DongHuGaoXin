package androidapp.donghugaoxin.suncere.com.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

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
 * @time 2018/5/23 14:30
 * @desc:普通用户
 */

public class MainActivity3 extends MvpActivity<UpDataAPPPresenter> implements IBaseView {
    TabBar mTabBar;

    UpDataAPPPresenter mUpDataAPPPresenter; // 更新APP

    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        StatusBarUtil.setColor(this, Color.rgb(0,0,0));
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mHomeFragment = (HomeFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "HomeFragment");
        } else {
            mHomeFragment = HomeFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout3, mHomeFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 存储 Fragment 的状态。
        getSupportFragmentManager().putFragment(outState, "HomeFragment", mHomeFragment);
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
