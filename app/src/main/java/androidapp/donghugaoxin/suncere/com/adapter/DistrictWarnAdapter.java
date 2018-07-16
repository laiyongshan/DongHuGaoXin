package androidapp.donghugaoxin.suncere.com.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.entity.DistrictWarnBean;

/**
 * @author lys
 * @time 2018/6/1 14:14
 * @desc:
 */

public class DistrictWarnAdapter extends BaseQuickAdapter<DistrictWarnBean,BaseViewHolder> {

    public DistrictWarnAdapter(int layoutResId, @Nullable List<DistrictWarnBean> datas) {
        super(R.layout.item_district_warn_rv, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, DistrictWarnBean item) {
//        ((TextView)helper.getView(R.id.warn_type_tv)).setText("");
//        ((TextView)helper.getView(R.id.description_tv)).setText("");
//        ((TextView)helper.getView(R.id.warn_time_tv)).setText("");
//        ((TextView)helper.getView(R.id.commit_time_tv)).setText("");
//        ((TextView)helper.getView(R.id.go_deal_tv)).setText("");
//
//        ((ImageView)helper.getView(R.id.deal_with_iv)).setImageResource();
//        ((ImageView)helper.getView(R.id.warn_level_label_iv)).setImageResource();
    }
}
