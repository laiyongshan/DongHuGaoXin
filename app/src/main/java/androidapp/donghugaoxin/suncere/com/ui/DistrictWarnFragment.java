package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToastUtil;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.androidapp.lib.mvp.ui.baseui.MvpFragment;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.ColorUtils;

/**
 * @author lys
 * @time 2018/5/29 17:24
 * @desc:
 */

public class DistrictWarnFragment extends MvpFragment<BasePresenterChild> implements IBaseView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tablayout)
    TabLayout warn_list_tablayout;

    @BindView(R.id.warn_refresh_layout)
    SwipeRefreshLayout warn_refresh_layout;

    @BindView(R.id.district_warn_rv)
    RecyclerView district_warn_rv;

    @BindView(R.id.null_data)
    LinearLayout null_data;

    private final String[] mTitles = {"全部", "待处理", "审核中", "已完成"};

    private View view;

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.district_warn_list,null);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {

        warn_refresh_layout.setColorSchemeColors(ColorUtils.Colors);
        warn_refresh_layout.setOnRefreshListener(this);

        for(int i=0;i<mTitles.length;i++){
            warn_list_tablayout.addTab(warn_list_tablayout.newTab().setText(mTitles[i]));
        }
        warn_list_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ToastUtil.showShortToast(tab.getText().toString()+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {

    }

    @Override
    public void getDataFail(String msg) {
        null_data.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishRefresh() {

    }
}
