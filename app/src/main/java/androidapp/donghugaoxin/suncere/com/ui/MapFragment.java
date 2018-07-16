package androidapp.donghugaoxin.suncere.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToastUtil;
import androidapp.donghugaoxin.suncere.com.Utils.ToolUtils;
import androidapp.donghugaoxin.suncere.com.adapter.MyViewHolder;
import androidapp.donghugaoxin.suncere.com.customview.PollutantsView;
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
 * @time 2018/5/9 16:18
 * @desc:
 */

public class MapFragment extends MvpFragment<BasePresenterChild> implements IBaseView,AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,AMap.OnInfoWindowClickListener  {

    @BindView(R.id.mapView)
    MapView mMapView ;

    AMap maMap;

    UiSettings mUiSettings;
    LatLng mLatLng;
    BitmapDescriptor mbitmapDescriptor;
    Marker mMarker;
    View mMarkView,mMarkViewWin;

    @BindView(R.id.map_PollutantsView)
    PollutantsView map_PollutantsView;

    @BindView(R.id.map_time)
    TextView map_time;

    @BindView(R.id.map_refresh_iv)
    ImageView map_refresh_iv;



    private Animation operatingAnim;//动画

    //网络请求参数
    String mpollutantType="104";//99:AQI 100:SO2 101:NO2  102:O3  103:CO  104:PM10  105:PM2.5
    String mpollutantName="PM10";//默认显示PM10
    String stationType="5";

    List<StationDataBean> mStationList=new ArrayList<>();

    BasePresenterChild mBasePresenterChild;

    StataionDataDialog mStationDataDialog;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_map,null);
        ButterKnife.bind(this,view);
        initView(savedInstanceState);
        return view;
    }



    @Override
    protected BasePresenterChild createPresenter() {
        mBasePresenterChild=new BasePresenterChild(this);
        getData();
        return mBasePresenterChild;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    public void  initView(Bundle savedInstanceState){
        operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
        operatingAnim.setDuration(500);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        mMapView.onCreate(savedInstanceState);
        if (maMap==null){
            maMap=mMapView.getMap();
        }
        mLatLng=new LatLng(30.4017058426,114.5187322712); //30.56150300   <Longitude>114.2646250</Longitude>
        maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,10));
        mUiSettings = maMap.getUiSettings();//实例化UiSettings类
        mUiSettings.setZoomControlsEnabled(false);//不显示放大缩小控件

        maMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        maMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        maMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

        map_PollutantsView.setmSelceTextListener(new PollutantsView.SelceTextListener() {
            @Override
            public boolean onSelect(View v, String pollutantName, String pollutantCode) {
                mpollutantName=pollutantName;
                mpollutantType= pollutantCode;
                getData();
                return true;
            }
        });
    }

    //获取数据
    private void getData(){
        map_refresh_iv.startAnimation(operatingAnim);
        mBasePresenterChild.getCatchOrNetData(mBasePresenterChild.getRetrofitSrevice().getStationDataHour(stationType));
    }

    @Override
    public void showRefresh() {
        map_refresh_iv.startAnimation(operatingAnim);
    }

    @Override
    public void getDataSuccess(Object response) {
        mStationList.clear();
        if(response!=null){
            List<BaseBean> list= (List<BaseBean>) response;
            mStationList.addAll((List<StationDataBean>) response);
            bindMapData(mStationList);
        }else{

        }
    }


    @Override
    public void getDataFail(String msg) {
        if(msg!=null)
            ToastUtil.showShortToast("网络请求错误！");

        map_refresh_iv.clearAnimation();
    }

    @Override
    public void finishRefresh() {
        map_refresh_iv.clearAnimation();
    }


    private View creatMarkView(StationDataBean bean) {
        View view = null;
        view = LayoutInflater.from(getActivity()).inflate(R.layout.map_marker_item, null);
        TextView value_tv = (TextView) view.findViewById(R.id.map_marker_Vaule);

        if(mpollutantType.equals("99")){//AQI
            value_tv.setText(bean.getAQI().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getAQILevelText()));
        }else if(mpollutantType.equals("100")){//SO2
            value_tv.setText(bean.getSO2().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getSO2LevelText()));
        }else if(mpollutantType.equals("101")){//NO2
            value_tv.setText(bean.getNO2().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getNO2LevelText().trim()));
        }else if(mpollutantType.equals("102")){//o3
            value_tv.setText(bean.getO3().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getO3LevelText()));
        }else if(mpollutantType.equals("103")){//CO
            value_tv.setText(bean.getCO().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getCOLevelText()));
        }else if(mpollutantType.equals("104")){//PM10
            value_tv.setText(bean.getPM10().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM10LevelText()));
        }else if(mpollutantType.equals("105")){//PM2.5
            value_tv.setText(bean.getPM2_5().toString());
            value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM2_5LevelText()));
        }
        return view;
    }

    private void   bindMapData(List<StationDataBean> listData) {
        for (Marker marker : maMap.getMapScreenMarkers()) {
            marker.remove();
        }
        mMapView.invalidate();
        if (listData != null && listData.size() > 0) {
            map_time.setText(ToolUtils.stringToData(listData.get(0).getTimePoint(),"yyyy-MM-dd HH:mm","yyyy-MM-dd HH")+ "时更新");
        }
        for (int i = 0; i < listData.size(); i++) {
            StationDataBean bean = listData.get(i);
            if (bean.getLatitude().equals("—") || bean.getLongitude().equals("—") || bean.getLatitude().length() == 0 || bean.getLongitude().length() == 0) {
                continue;
            } else {
                View view = creatMarkView(bean);
                mbitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                LatLng latLng = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitude()));
                mMarker = maMap.addMarker(new MarkerOptions().position(latLng).icon(mbitmapDescriptor).zIndex(i).title("mapfragment"));
            }
        }
    }

    TextView marker_value_tv;
    TextView marker_level_tv;
    Button map_station_data_btn;
    private View creatMarkViewWin(final StationDataBean bean) {
        if (mMarkViewWin == null) {
            mMarkViewWin = LayoutInflater.from(getActivity()).inflate(R.layout.map_mark_itme_win, null);
        }
        ((TextView) MyViewHolder.getView(mMarkViewWin, R.id.map_stationName)).setText(bean.getName());
//        if(marker_value_tv==null) {
//            marker_value_tv = ((TextView) MyViewHolder.getView(mMarkViewWin, R.id.marker_value_tv));
//        }
//
//        map_station_data_btn=MyViewHolder.getView(mMarkViewWin,R.id.map_station_data_btn);
        map_station_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStationDataDialog=new StataionDataDialog(getActivity(),R.style.Dialog,bean);
                mStationDataDialog.show();
            }
        });

//        if(marker_level_tv==null){
//            marker_level_tv=((TextView) MyViewHolder.getView(mMarkViewWin, R.id.marker_level_tv));
//        }

        if(mpollutantType.equals("99")){//AQI
            marker_value_tv.setText(bean.getAQI().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getAQILevelText()));
            marker_level_tv.setText(bean.getAQILevelText()+"");
        }else if(mpollutantType.equals("100")){//SO2
            marker_value_tv.setText(bean.getSO2().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getSO2LevelText()));
            marker_level_tv.setText(bean.getSO2LevelText()+"");
        }else if(mpollutantType.equals("101")){//NO2
            marker_value_tv.setText(bean.getNO2().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getNO2LevelText()));
            marker_level_tv.setText(bean.getNO2LevelText()+"");
        }else if(mpollutantType.equals("102")){//o3
            marker_value_tv.setText(bean.getO3().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getO3LevelText()));
            marker_level_tv.setText(bean.getO3LevelText()+"");
        }else if(mpollutantType.equals("103")){//CO
            marker_value_tv.setText(bean.getCO().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getCOLevelText()));
            marker_level_tv.setText(bean.getCOLevelText()+"");
        }else if(mpollutantType.equals("104")){//PM10
            marker_value_tv.setText(bean.getPM10().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM10LevelText()));
            marker_level_tv.setText(bean.getPM10LevelText()+"");
        }else if(mpollutantType.equals("105")){//PM2.5
            marker_value_tv.setText(bean.getPM2_5().toString());
            marker_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM2_5LevelText()));
            marker_level_tv.setText(bean.getPM2_5LevelText()+"");
        }

        return mMarkViewWin;
    }



    @Override
    public View getInfoWindow(Marker marker) {
        int index = (int) marker.getZIndex();
        return creatMarkViewWin(mStationList.get(index));
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker != null) {
            marker.hideInfoWindow();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;//要设为true
    }


    @OnClick({R.id.map_refresh_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.map_refresh_iv:
                getData();
                break;
        }
    }


}
