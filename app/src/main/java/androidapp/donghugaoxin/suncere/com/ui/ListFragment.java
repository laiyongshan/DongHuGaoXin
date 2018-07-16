package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.adapter.StationInfoAdapter;
import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToastUtil;
import androidapp.donghugaoxin.suncere.com.entity.StationDataBean;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.entity.BaseBean;
import suncere.androidapp.lib.mvp.ui.baseui.MvpFragment;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.ColorUtils;

/**
 * @author lys
 * @time 2018/5/11 09:55
 * @desc:列表頁
 */

public class ListFragment extends MvpFragment<BasePresenterChild> implements IBaseView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list_refresh_iv)
    ImageView list_refresh_iv;

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.list_emptyText)
    LinearLayout list_emptyText;

    @BindView(R.id.list_refresh_SwipeRefreshLayout)
    SwipeRefreshLayout mswipeRefreshLayout;

    private Animation operatingAnim;//动画

    List<StationDataBean> mStationList = new ArrayList<>();
    String stationType = "5";

    private int mFirstPageItemCount = 6;
    StationInfoAdapter mStationInfoAdapter;

    BasePresenterChild mBasePresenterChild;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void initView() {
        operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
        operatingAnim.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        mswipeRefreshLayout.setColorSchemeColors(ColorUtils.Colors);
        mswipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStationInfoAdapter = new StationInfoAdapter(R.layout.item_station_list, mStationList);
        mStationInfoAdapter.isFirstOnly(false);//init firstOnly state
        mStationInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mStationInfoAdapter);
    }

    @Override
    protected BasePresenterChild createPresenter() {
        mBasePresenterChild = new BasePresenterChild(this);
        return mBasePresenterChild;
    }

    //获取数据
    private void getData() {
        list_refresh_iv.startAnimation(operatingAnim);
        mBasePresenterChild.getCatchOrNetData(mBasePresenterChild.getRetrofitSrevice().getStationDataHour(stationType));
    }

    @Override
    public void showRefresh() {
        list_refresh_iv.startAnimation(operatingAnim);
        mswipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void getDataSuccess(Object response) {
        mStationList.clear();
        if (response != null) {
            List<BaseBean> list = (List<BaseBean>) response;
            mStationList.addAll((List<StationDataBean>) response);
            if (mStationList != null && !mStationList.isEmpty()) {
                setData(mStationList);
                list_emptyText.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            list_emptyText.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    //设置rv的数据
    public void setData(List<StationDataBean> mStationList) {
        mStationInfoAdapter = new StationInfoAdapter(R.layout.item_station_list, mStationList);
        mStationInfoAdapter.openLoadAnimation();
        mStationInfoAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mStationInfoAdapter.setNotDoAnimationCount(mFirstPageItemCount);
        mStationInfoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setAdapter(mStationInfoAdapter);
    }


    @Override
    public void getDataFail(String msg) {
        if (msg != null)
            ToastUtil.showShortToast("网络请求错误！");

        list_refresh_iv.clearAnimation();
        mswipeRefreshLayout.setRefreshing(false);

        list_emptyText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void finishRefresh() {
        list_refresh_iv.clearAnimation();
        mswipeRefreshLayout.setRefreshing(false);
    }

    @OnClick({R.id.list_refresh_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.list_refresh_iv:
                getData();
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
