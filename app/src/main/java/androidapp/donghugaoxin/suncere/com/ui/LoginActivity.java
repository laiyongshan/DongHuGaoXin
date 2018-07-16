package androidapp.donghugaoxin.suncere.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToastUtil;
import androidapp.donghugaoxin.suncere.com.customview.LoginProgressDialog;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.ui.baseui.MvpActivity;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.StatusBarUtil;

/**
 * @author lys
 * @time 2018/5/16 14:19
 * @desc:
 */

public class LoginActivity extends MvpActivity<BasePresenterChild> implements IBaseView {

    LoginProgressDialog mLoginProgressDialog;

    @BindView(R.id.login_go_btn)
    Button login_go_btn;

    @BindView(R.id.user_name_et)
    EditText  user_name_et;

    @BindView(R.id.user_psw_et)
    EditText user_psw_et;

    String userName;
    String userPsw;

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setColor(LoginActivity.this , 0 );
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        boolean isLogin=getIntent().getBooleanExtra("isLogin",true);
        mLoginProgressDialog=new LoginProgressDialog(this);
        mLoginProgressDialog.setProgressText("正在登陆...");
    }

    @OnClick({R.id.login_go_btn})
    public void click(View view){
        switch(view.getId()){
            case R.id.login_go_btn:
                userName=user_name_et.getText().toString().trim();
                userPsw=user_psw_et.getText().toString().trim();
                if(!user_psw_et.equals("")&&!userName.equals(""))
                    loginApp(userName,userPsw);
                else
                    ToastUtil.showShortToast("账号和密码不能为空");
                break;
        }
    }

    //登录操作
    private void loginApp(String userName,String psw){
        mLoginProgressDialog.showDialog();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void showRefresh() {
        mLoginProgressDialog.showDialog();
    }

    @Override
    public void getDataSuccess(Object response) {

    }

    @Override
    public void getDataFail(String msg) {
        mLoginProgressDialog.dismiss();
    }

    @Override
    public void finishRefresh() {
        mLoginProgressDialog.dismiss();
    }


}
