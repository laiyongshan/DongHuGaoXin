package suncere.androidapp.lib.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by HJO on 2017/5/14.
 */

public class BindingViewHodle <T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T mBinding;

    public BindingViewHodle(T binding) {
        super(binding.getRoot());
        mBinding=binding;

    }


    public T getBinding() {
        return mBinding;
    }
}
