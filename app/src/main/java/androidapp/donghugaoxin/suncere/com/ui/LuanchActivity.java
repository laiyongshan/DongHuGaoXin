package androidapp.donghugaoxin.suncere.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.LeakCanaryUtil;
import androidapp.donghugaoxin.suncere.com.Utils.ToolUtils;
import suncere.androidapp.lib.mvp.ui.baseui.BaseActivity;

/**
 * Created by Hjo on 2017/11/23 15:17.
 */

public class LuanchActivity extends BaseActivity {

    ToolUtils mToolUtils;

   private   Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        setContentView(R.layout.luanch_act);

        mToolUtils=new ToolUtils();

        intoMainAct();
    }

    private void intoMainAct(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(LuanchActivity.this, LoginActivity.class);
                Intent intent =new Intent(LuanchActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakCanaryUtil.fixMemoryLeak(this);
        mHandler.removeCallbacksAndMessages(null);
        mHandler=null;
    }

}
