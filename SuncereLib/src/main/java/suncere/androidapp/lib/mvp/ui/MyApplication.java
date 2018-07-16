package suncere.androidapp.lib.mvp.ui;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Hjo on 2017/4/6.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        myApplication=this;
        super.onCreate();
    }

    /**
     * 获取Application的context
     * @return
     */
    public static Context getMyApplicationContext(){
        return myApplication.getApplicationContext();
    }

    /**
     * 获取当前APP版本号
     * @return
     */
    public static int getMyApplicationVersion(){
        try {
            PackageInfo info=getMyApplicationContext().getPackageManager().getPackageInfo(getMyApplicationContext().getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * 获取当前APP版本号
     * @return
     */
    public static String  getMyApplicationVersionName(){
        try {
            PackageInfo info=getMyApplicationContext().getPackageManager().getPackageInfo(getMyApplicationContext().getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "1.0.0";
    }

}
