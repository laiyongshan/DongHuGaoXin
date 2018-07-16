package androidapp.donghugaoxin.suncere.com.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.customview.AlwaysMarqueeTextView;
import androidapp.donghugaoxin.suncere.com.entity.SortBean;

/**
 * @author lys
 * @time 2018/5/15 11:28
 * @desc:
 */

public class SortAdapter extends BaseQuickAdapter<SortBean, BaseViewHolder> {


    public SortAdapter(int layoutResId, @Nullable List<SortBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortBean item) {
        ((TextView) helper.getView(R.id.sort_num_tv)).setText("");
        ((AlwaysMarqueeTextView) helper.getView(R.id.sort_station_name_tv)).setText("");
        ((TextView) helper.getView(R.id.sort_AQI_value_tv)).setText("");
        ((TextView)helper.getView(R.id.sort_PM2_5_value_tv)).setText("");
        ((TextView)helper.getView(R.id.sort_PM10_value_tv)).setText("");
    }
}
