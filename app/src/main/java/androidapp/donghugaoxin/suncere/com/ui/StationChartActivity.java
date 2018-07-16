package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.customview.LineChartView;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.androidapp.lib.customview.PollutantsView;
import suncere.androidapp.lib.mvp.ui.baseui.MvpActivity;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;

/**
 * @author lys
 * @time 2018/5/28 14:57
 * @desc:
 */

public class StationChartActivity extends MvpActivity<BasePresenterChild> implements IBaseView {

    @BindView(R.id.back_iv)
    ImageView back_iv;

    @BindView(R.id.station_title_text)
    TextView station_title_text;

    @BindView(R.id.station_chart_tabTitle)
    TextView station_chart_tabTitle;

    @BindView(R.id.station_chart_PollutantsView)
    PollutantsView station_chart_PollutantsView;

    @BindView(R.id.station_chart_LineChartView)
    LineChartView station_chart_LineChartView;


    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_chart);
        ButterKnife.bind(this);
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
