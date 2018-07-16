package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.roundview.RoundTextView;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.ui.baseui.MvpActivity;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;

/**
 * @author lys
 * @time 2018/5/30 14:29
 * @desc:
 */

public class FillReasonActivity extends MvpActivity<BasePresenterChild> implements IBaseView {

    @BindView(R.id.back_iv)
    ImageView back_iv;//返回按钮

    @BindView(R.id.warn_condition_et)
    EditText warn_condition_et;

    @BindView(R.id.commit_round_tv)
    RoundTextView commit_round_tv;//提交按钮

    @BindView(R.id.back_round_tv)
    RoundTextView back_round_tv;//返回按钮

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_reason);
    }

    @OnClick({R.id.back_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }


    @Override
    public void showRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void finishRefresh() {

    }
}
