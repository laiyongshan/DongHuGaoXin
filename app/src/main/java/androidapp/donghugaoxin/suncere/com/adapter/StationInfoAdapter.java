package androidapp.donghugaoxin.suncere.com.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.ToolUtils;
import androidapp.donghugaoxin.suncere.com.customview.AlwaysMarqueeTextView;
import androidapp.donghugaoxin.suncere.com.entity.StationDataBean;
import suncere.androidapp.lib.utils.ColorUtils;


/**
 * @author lys
 * @time 2018/5/8 14:18
 * @desc:
 */

public class StationInfoAdapter extends BaseQuickAdapter<StationDataBean, BaseViewHolder> {
    public StationInfoAdapter(int layoutResId, List<StationDataBean> StationInfoList) {
        super(layoutResId,StationInfoList);
    }

    @Override
    protected void convert(BaseViewHolder helper, StationDataBean item) {
        ((AlwaysMarqueeTextView) helper.getView(R.id.station_name_tv)).setText(item.getName()+"");
        ((TextView) helper.getView(R.id.timepoint_tv)).setText(ToolUtils.stringToData(item.getTimePoint(),"yyyy-MM-dd HH:mm","MM月dd日 HH时"));
        ((TextView) helper.getView(R.id.rv_item_aqi_tv)).setText(ColorUtils.getQualityWithAQI2(item.getAQI())+"  "+item.getAQI());
        ((TextView) helper.getView(R.id.rv_item_aqi_tv)).setBackground(ColorUtils.getBgFromQualitys(item.getAQILevelText()));
        ((TextView) helper.getView(R.id.pm25_value_tv)).setText(item.getPM2_5()+"");
        ((TextView) helper.getView(R.id.pm10_value_tv)).setText(item.getPM10()+"");
        ((TextView) helper.getView(R.id.so2_value_tv)).setText(item.getSO2()+"");
        ((TextView) helper.getView(R.id.no2_value_tv)).setText(item.getNO2()+"");
        ((TextView) helper.getView(R.id.o3_value_tv)).setText(item.getO3()+"");
        ((TextView) helper.getView(R.id.co_value_tv)).setText(item.getO3()+"");

    }
}
