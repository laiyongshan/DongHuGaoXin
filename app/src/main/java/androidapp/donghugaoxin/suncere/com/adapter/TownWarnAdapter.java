package androidapp.donghugaoxin.suncere.com.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.entity.TownWarnBean;

/**
 * @author lys
 * @time 2018/6/1 14:14
 * @desc:
 */

public class TownWarnAdapter extends BaseQuickAdapter<TownWarnBean, BaseViewHolder> {


    public TownWarnAdapter(int layoutResId, @Nullable List<TownWarnBean> datas) {
        super(R.layout.item_town_warn_rv, datas);
    }


    @Override
    protected void convert(BaseViewHolder helper, TownWarnBean item) {
//        ((TextView)helper.getView(R.id.warn_type_tv)).setText("");
//        ((TextView)helper.getView(R.id.description_tv)).setText("");
//        ((TextView)helper.getView(R.id.warn_time_tv)).setText("");
//        ((TextView)helper.getView(R.id.commit_time_tv)).setText("");
//        ((TextView)helper.getView(R.id.go_deal_tv)).setText("");

//        ((ImageView)helper.getView(R.id.deal_with_iv)).setImageResource();
//        ((ImageView)helper.getView(R.id.warn_level_label_iv)).setImageResource();

//        ((TextView)helper.getView(R.id.check_technicist_tv)).setText("");
//        ((TextView)helper.getView(R.id.check_head_tv)).setText("");

    }
}
