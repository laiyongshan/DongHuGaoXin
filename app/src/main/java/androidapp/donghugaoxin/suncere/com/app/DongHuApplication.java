package androidapp.donghugaoxin.suncere.com.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;

import cn.jpush.android.api.JPushInterface;
import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * @author lys
 * @time 2018/5/9 15:51
 * @desc:
 */

public class DongHuApplication extends MyApplication {

    private static MyApplication myApplication;
    private static final  String BUGLY_ID="c49bdbec93";

    @Override
    public void onCreate() {
//        myApplication=this;
        super.onCreate();
        Bugly.init(getApplicationContext(),BUGLY_ID , false);
        initJPush();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    /**
     * 获取Application的context
     *
     * @return
     */
    public static Context getMyApplicationContext() {
        return myApplication.getApplicationContext();
    }

    /**
     * 获取当前APP版本号
     *
     * @return
     */
    public static String getAppVersion() {
        try {
            PackageInfo info = getMyApplicationContext().getPackageManager().getPackageInfo(getMyApplicationContext().getPackageName(), 0);
//			return info.versionCode;
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }
}
