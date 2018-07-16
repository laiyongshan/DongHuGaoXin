package androidapp.donghugaoxin.suncere.com.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.entity.WranMessageBean;

/**
 * @author lys
 * @time 2018/6/1 09:40
 * @desc:
 */

public class WarnMessgeListAdapter extends BaseQuickAdapter<WranMessageBean, BaseViewHolder> {

    Context context;

    public WarnMessgeListAdapter( List<WranMessageBean> datas, Context context) {
        super(R.layout.item_home_wran_message_rv, datas);
        this.context=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, WranMessageBean item) {
        ((TextView)helper.getView(R.id.warn_level_tv)).setText("一般");
        ((TextView)helper.getView(R.id.warn_type_tv)).setText("站点一PM10浓度值报警");
        ((TextView)helper.getView(R.id.wran_updata_time)).setText("2018年05月30日 13:00");
    }
}
