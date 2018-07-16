package suncere.androidapp.lib.mvp.ui.baseui;

import android.os.Bundle;
import android.view.View;

import suncere.androidapp.lib.mvp.presenter.BasePresenter;


/**
 * Created by Hjo on 2017/5/11.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
