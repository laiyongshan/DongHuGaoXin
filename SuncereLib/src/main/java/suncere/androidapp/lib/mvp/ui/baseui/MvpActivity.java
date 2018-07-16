package suncere.androidapp.lib.mvp.ui.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import suncere.androidapp.lib.mvp.presenter.BasePresenter;


/**
 * Created by Hjo on 2017/5/11.
 */

public abstract  class MvpActivity<P extends BasePresenter> extends BaseActivity {

    protected  P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
    }

    protected  abstract  P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();//RXjava取消注册，以避免内存泄露
        }
    }
}
