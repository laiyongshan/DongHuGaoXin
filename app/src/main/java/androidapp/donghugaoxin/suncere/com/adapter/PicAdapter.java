package androidapp.donghugaoxin.suncere.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.Utils.CornersTransform;

/**
 * @author lys
 * @time 2018/5/29 10:58
 * @desc:
 */

public class PicAdapter extends BaseQuickAdapter<Uri, BaseViewHolder>  {

    Context context;
    public PicAdapter(List<Uri> mSelecteds, Context  context) {
        super(R.layout.item_pic_rv,mSelecteds);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Uri uri) {
//        ((ImageView) helper.getView(R.id.item_pic_iv)).setImageURI(uri);
        helper.addOnClickListener(R.id.cancel_pic_iv);
        Glide.with(context).load(uri).transform(new CornersTransform(context,20)).into(((ImageView) helper.getView(R.id.item_pic_iv)));
    }
}
