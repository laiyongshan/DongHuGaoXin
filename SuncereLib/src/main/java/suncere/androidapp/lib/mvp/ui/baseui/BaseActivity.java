package suncere.androidapp.lib.mvp.ui.baseui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * Created by Hjo on 2017/5/12.
 */
public class BaseActivity extends AppCompatActivity {

        private  ExitAPPBroadcast mExitAPPBroadcast;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            getSupportActionBar().hide();//去掉标题
            IntentFilter filter = new IntentFilter();
            filter.addAction("suncere.androidAPP.exitApp");
            mExitAPPBroadcast=new ExitAPPBroadcast();
            registerReceiver(mExitAPPBroadcast, filter);
//            setPermissions(this);
        }

        //申请权限
//        public static void setPermissions(Activity context) {
//            int REQUEST_EXTERNAL_STORAGE = 1;
//            String[] PERMISSIONS_STORAGE = {
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
//                    Manifest.permission.SET_WALLPAPER,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.WRITE_SETTINGS,
//                    Manifest.permission.CAMERA};
//            ActivityCompat.requestPermissions(context, PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE);
//        }

        public class ExitAPPBroadcast extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("suncere.androidAPP.exitApp".equals(intent.getAction())) {
                    finish();
                }
            }
        }

        public  void exitAPP(){
            Intent intent = new Intent("suncere.androidAPP.exitApp");
            MyApplication.getMyApplicationContext().sendBroadcast(intent);
        }

        public   void ShowExitAPP() {
//            new AlertDialog.Builder(this)
//                    .setTitle("提示")
//                    .setMessage("确定要退出？")
//                    .setNeutralButton ("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int arg1) {
//                            dialog.dismiss();
//                            exitAPP();
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int arg1) {
//                    dialog.dismiss();
//                }
//            }).show();


//            final MaterialDialog mMaterialDialog = new MaterialDialog(this);
//            mMaterialDialog.setTitle("提示");
//            mMaterialDialog.setMessage("确定要退出？");
//            mMaterialDialog.setPositiveButton("确定", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mMaterialDialog.dismiss();
                    exitAPP();
//                }
//            });
//            mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mMaterialDialog.dismiss();
//                }
//            });
//            mMaterialDialog.show();
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExitAPPBroadcast!=null){
            unregisterReceiver(mExitAPPBroadcast);
        }
    }

}
