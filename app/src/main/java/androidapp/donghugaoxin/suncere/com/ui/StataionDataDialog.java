package androidapp.donghugaoxin.suncere.com.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToolUtils;
import androidapp.donghugaoxin.suncere.com.entity.StationDataBean;
import suncere.androidapp.lib.utils.ColorUtils;

/**
 * @author lys
 * @time 2018/5/10 14:42
 * @desc:
 */

public class StataionDataDialog extends Dialog {

    private StationDataBean bean;

    ImageView cancel_iv;
    TextView station_name_tv,AQI_value_tv,AQI_level_tv,updata_time_tv;
    TextView pm25_value_tv,pm10_value_tv,pmO3_value_tv,pmSO2_value_tv,no2_value_tv,co_value_tv;

    public StataionDataDialog(@NonNull Context context, int themeResId, StationDataBean bean) {
        super(context, themeResId);
        this.bean=bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_station_data);
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void initView(){
        cancel_iv=findViewById(R.id.cancel_iv);
        cancel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        station_name_tv=findViewById(R.id.station_name_tv);
        station_name_tv.setText(bean.getName()+"空气质量指数/AQI");

        AQI_value_tv=findViewById(R.id.AQI_value_tv);
        AQI_value_tv.setText(bean.getAQI()+"");
        AQI_value_tv.setTextColor(ColorUtils.getColorFromQuality(bean.getAQILevelText()));

        AQI_level_tv=findViewById(R.id.AQI_level_tv);
        AQI_level_tv.setText(bean.getAQILevelText());
        AQI_level_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getAQILevelText()));

        updata_time_tv=findViewById(R.id.updata_time_tv);
        updata_time_tv.setText(ToolUtils.stringToData(bean.getTimePoint(),"yyyy-MM-dd HH:mm","yy月dd日 HH：mm")+"更新");

        pm25_value_tv=findViewById(R.id.pm25_value_tv);
        pm25_value_tv.setText(bean.getPM2_5()+"");
        pm25_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM2_5LevelText()));

        pm10_value_tv=findViewById(R.id.pm10_value_tv);
        pm10_value_tv.setText(bean.getPM10());
        pm10_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getPM10LevelText()));

        pmO3_value_tv=findViewById(R.id.pmO3_value_tv);
        pmO3_value_tv.setText(bean.getO3());
        pmO3_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getO3LevelText()));

        pmSO2_value_tv=findViewById(R.id.pmSO2_value_tv);
        pmSO2_value_tv.setText(bean.getSO2());
        pmSO2_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getSO2LevelText()));

        no2_value_tv=findViewById(R.id.no2_value_tv);
        no2_value_tv.setText(bean.getNO2());
        no2_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getNO2LevelText()));

        co_value_tv=findViewById(R.id.co_value_tv);
        co_value_tv.setText(bean.getCO());
        co_value_tv.setBackground(ColorUtils.getBgFromQualitys(bean.getCOLevelText()));

    }
}
