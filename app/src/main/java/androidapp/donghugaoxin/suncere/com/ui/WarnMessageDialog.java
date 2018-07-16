package androidapp.donghugaoxin.suncere.com.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.adapter.WarnMessgeListAdapter;
import androidapp.donghugaoxin.suncere.com.entity.WranMessageBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lys
 * @time 2018/5/29 15:22
 * @desc:
 */

public class WarnMessageDialog extends Dialog {

    @BindView(R.id.dismiss_dialog_iv)
    ImageView dismiss_dialog_iv;

    @BindView(R.id.warn_message_rv)
    RecyclerView warn_message_rv;


    Context context;

    WarnMessgeListAdapter mWarnMessgeListAdapter;
    List<WranMessageBean> mWarnMessageList=new ArrayList<>();

    public WarnMessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warn_message);
        ButterKnife.bind(this,this);
        setCanceledOnTouchOutside(false);

        for(int i=0;i<20;i++){
            mWarnMessageList.add(new WranMessageBean());
        }
        mWarnMessgeListAdapter=new WarnMessgeListAdapter(mWarnMessageList,context);
        mWarnMessgeListAdapter.setNewData(mWarnMessageList);
        mWarnMessgeListAdapter.notifyDataSetChanged();

        warn_message_rv.setLayoutManager(new LinearLayoutManager(context));
        warn_message_rv.setAdapter(mWarnMessgeListAdapter);
    }

    @OnClick({R.id.dismiss_dialog_iv})
    public  void  click(View view){
        switch (view.getId()){
            case R.id.dismiss_dialog_iv:
                dismiss();
                break;
        }
    }
}
