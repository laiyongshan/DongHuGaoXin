package androidapp.donghugaoxin.suncere.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.customview.LineChartView;
import androidapp.donghugaoxin.suncere.com.customview.TipView;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.customview.PollutantNameTextView;
import suncere.androidapp.lib.customview.PollutantsView;
import suncere.androidapp.lib.mvp.ui.AQIDesActivity;
import suncere.androidapp.lib.mvp.ui.MyApplication;
import suncere.androidapp.lib.mvp.ui.SystemDesActivity;
import suncere.androidapp.lib.mvp.ui.baseui.MvpFragment;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.ColorUtils;

/**
 * @author lys
 * @time 2018/5/24 11:17
 * @desc:
 */

public class HomeFragment extends MvpFragment<BasePresenterChild> implements IBaseView, SwipeRefreshLayout.OnRefreshListener {

    private View view;

    @BindView(R.id.home_topbar_user)
    ImageView home_topbar_user;//弹出侧滑菜单栏

    @BindView(R.id.home_topbar_meassge)
    ImageView home_topbar_meassge;//顶部消息图标按钮

    @BindView(R.id.district_sort_tv)
    TextView district_sort_tv;//全区排名

    @BindView(R.id.AQI_value_tv)
    TextView AQI_value_tv;//AQI值

    @BindView(R.id.AQI_level_tv)
    TextView AQI_level_tv;//AQI等级

    @BindView(R.id.updata_time_tv)
    TextView updata_time_tv;//更新时间

    @BindView(R.id.primary_pollut_tv)
    TextView primary_pollut_tv;//首要污染物

    @BindViews({R.id.pm25_value_tv,R.id.pm10_value_tv,R.id.so2_value_tv,R.id.no2_value_tv,R.id.o3_value_tv,R.id.co_value_tv})
    TextView[] pollutsTvs;//六大空气污染物值显示

    @BindView(R.id.home_page_1_chart_PollutantsView)
    PollutantsView home_chart_tab;//图表上污染物选择控件

    @BindView(R.id.home_chart_tab_title)
    PollutantNameTextView home_chart_tab_title;

    @BindView(R.id.home_page_1_chart_LineChartView)
    LineChartView home_page_1_chart_LineChartView;//首页图表

    @BindView(R.id.home_refresh_layout)
    SwipeRefreshLayout home_refresh_layout;//刷新控件

    @BindView(R.id.drawer_main_layout)
    DrawerLayout drawer_main_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    Button exit_tv;//退出按鈕
    TextView user_name_tv;//用户名称
    TextView login_time_tv;//登录时间

    TipView mtip;


    public static HomeFragment newInstance(){
        return  new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        home_refresh_layout.setColorSchemeColors(ColorUtils.Colors);
        home_refresh_layout.setOnRefreshListener(this);

        mtip=new TipView(getActivity(), R.layout.tip_view);
        TextView set_app_version= (TextView) mtip.findViewById(R.id.set_app_version);
        set_app_version.setText("版本号："+ MyApplication.getMyApplicationVersionName());

        nav_view.setItemIconTintList(null);
        if (nav_view != null) {
            setupDrawerContent(nav_view);
        }
        nav_view.setItemIconTintList(null);
        user_name_tv=nav_view.getHeaderView(0).findViewById(R.id.user_name_tv);
        login_time_tv=nav_view.getHeaderView(0).findViewById(R.id.login_time_tv);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_nav_level:
                        Intent  intent1=new Intent(getActivity(),AQIDesActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.item_nav_sys:
                        Intent  intent2=new Intent(getActivity(),SystemDesActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.item_nav_about:
                        mtip.show();
                        break;
                }
                drawer_main_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        exit_tv=drawer_main_layout.findViewById(R.id.exit_tv);
        exit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        home_chart_tab.setmSelceTextListener(new PollutantsView.SelceTextListener() {
            @Override
            public void onSelect(String pollutantName, String pollutantCode) {

            }
        });
    }


    @OnClick({R.id.home_topbar_user,R.id.home_topbar_meassge})
    public void on_Click(View view) {
        switch (view.getId()) {
            case R.id.home_topbar_user://设置的侧滑菜单
                drawer_main_layout.openDrawer(Gravity.START);
                break;

            case R.id.home_topbar_meassge:
                WarnMessageDialog mWarnMessageDialog=new WarnMessageDialog(getActivity(), R.style.dialog);
                mWarnMessageDialog.show();
                break;
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer_main_layout.closeDrawers();
                        return true;
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

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }
}
