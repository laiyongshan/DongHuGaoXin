package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.ui.baseui.MvpActivity;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;

/**
 * @author lys
 * @time 2018/5/30 11:54
 * @desc:
 */

public class AuditActivity extends  MvpActivity<BasePresenterChild> implements IBaseView {

    @BindView(R.id.back_iv)
    ImageView back_iv;

    @BindView(R.id.warn_condition_tv)
    TextView warn_condition_tv;

    @BindView(R.id.scene_pic_rv)
    RecyclerView scene_pic_rv;

    @BindView(R.id.pass_round_tv)
    RoundTextView pass_round_tv;

    @BindView(R.id.no_pass_round_tv)
    RoundTextView no_pass_round_tv;

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        ButterKnife.bind(this);
    }

    private void initView(){

    }

    @OnClick({R.id.back_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.back_iv:

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
