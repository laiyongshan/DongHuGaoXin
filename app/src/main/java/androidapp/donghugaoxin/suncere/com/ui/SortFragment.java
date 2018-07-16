package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToolUtils;
import androidapp.donghugaoxin.suncere.com.adapter.SortAdapter;
import androidapp.donghugaoxin.suncere.com.customview.SegmentControl;
import androidapp.donghugaoxin.suncere.com.entity.SortBean;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.ui.baseui.MvpFragment;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.ColorUtils;

/**
 * @author lys
 * @time 2018/5/14 15:17
 * @desc:排名頁面
 */

public class SortFragment extends MvpFragment<BasePresenterChild> implements IBaseView,SwipeRefreshLayout.OnRefreshListener{
    View mcontentView;

    @BindView(R.id.list_sore_way)
    LinearLayout list_sore_way;//排名顺序或倒序按钮
    @BindView(R.id.list_sore_image_up)
    ImageView listSoreImageUp;
    @BindView(R.id.list_sore_image_down)
    ImageView listSoreImageDown;

    boolean misSequence=true; //正序 倒序

    @BindView(R.id.sort_refresh_Layout)
    SwipeRefreshLayout sort_refresh_Layout;

    @BindView(R.id.sort_rv)
    RecyclerView sort_rv;//排名列表

    @BindView(R.id.updata_time_tv)
    TextView updata_time_tv;//更新時間

    @BindView(R.id.list_time_select)
    SegmentControl list_time_select;//排名時間選擇

    @BindView(R.id.list_emptyText)
    LinearLayout list_emptyText;

    SortAdapter mSortAdapter;
    List<SortBean> mSortList=new ArrayList<>();

    BasePresenterChild mBasePresenterChild;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sort, null);
        ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getSortData();
    }

    public void initViews() {

        sort_refresh_Layout.setColorSchemeColors(ColorUtils.Colors);
        sort_refresh_Layout.setOnRefreshListener(this);

        updata_time_tv.setText(ToolUtils.getNowTime()+"更新");

        list_time_select.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                //排名時間選擇

            }
        });

        sort_rv.setVisibility(View.GONE);
        if (mSortList.isEmpty())
            list_emptyText.setVisibility(View.VISIBLE);

        mSortAdapter=new SortAdapter(R.layout.item_sort_list,mSortList);
    }



    /*
    * 数据
    * */
    private void setSortData(List<SortBean> mSortList){
        mSortAdapter = new SortAdapter(R.layout.item_station_list,mSortList);
        mSortAdapter.openLoadAnimation();
        mSortAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mSortAdapter.setNotDoAnimationCount(5);
        mSortAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        sort_rv.setAdapter(mSortAdapter);
    }

    @OnClick({R.id.list_sore_way})
    public void click(View view){
        switch (view.getId()){


            case R.id.list_sore_way:
                if(misSequence){
                    misSequence=false;
                    listSoreImageUp.setImageResource(R.mipmap.down);
                    listSoreImageDown.setImageResource(R.mipmap.down_w);
                }else{
                    misSequence=true;
                    listSoreImageUp.setImageResource(R.mipmap.up_w);
                    listSoreImageDown.setImageResource(R.mipmap.up);
                }
                getSortData(); //获取排名数据
                break;
        }
    }


    //获取排名数据
    public void getSortData(){
//        mBasePresenterChild.getCatchOrNetData(mBasePresenterChild.getRetrofitSrevice().getSortDataHour());
    }


    @Override
    public void showRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {
        if(response!=null){

        }
    }

    @Override
    public void getDataFail(String msg) {
    }

    @Override
    public void finishRefresh() {
    }

    @Override
    protected BasePresenterChild createPresenter() {
        mBasePresenterChild=new BasePresenterChild(this);
        return mBasePresenterChild;
    }


    @Override
    public void onRefresh() {

    }
}
