   package suncere.androidapp.lib.updataapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


   /**
    * Created by zs on 2016/7/7.
    */
   public class UpdateManager {

       private Context mContext;
       public UpdateManager(Context context) {
           this.mContext = context;
       }

       /**
        * 检测软件更新
        */
       public void checkUpdate(String downloadUrl) {
               String version_info = "检测到软件有新的版本，是否立即更新？";
               showNoticeDialog(version_info, downloadUrl);
       }

       /*
        * 显示更新对话框
        * @param version_info
        */
       private void showNoticeDialog(String version_info, final String downloadUrl) {
           // 构造对话框
           AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
           builder.setTitle("更新提示");
           builder.setMessage(version_info);
           // 更新
           builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   Intent intent=   new Intent(mContext, DownLoadService.class);
                   intent.putExtra("downloadUrl",downloadUrl);
                   mContext.startService(intent);
               }
           });
           // 稍后更新
           builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
           Dialog noticeDialog = builder.create();
           noticeDialog.show();
       }


   }
