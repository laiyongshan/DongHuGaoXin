package suncere.androidapp.lib.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import suncere.androidapp.lib.mvp.entity.BaseBean;

/**
 * Created by HJO on 2017/5/14.
 * 使用dataBinding
 */
public class RecyclerViewAdapter<T extends BaseBean> extends RecyclerView.Adapter<BindingViewHodle>{

    private final LayoutInflater mLayoutInflater;
    private int mlayout_id;
    private int mBR;
    private List<T>mlist;//数据源
    private int tag=0;

    RecyclerViewOnItmeClickListener mrecyclerViewOnItmeClickListener;
    RecyclerViewOnBindItmeView mRecyclerViewOnBindItmeView;

    int mselectPosistion=0;
    boolean isUserBg=false;

    public RecyclerViewAdapter(Context context, int layout_id , int br){
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlist=new ArrayList<>();
        mlayout_id=layout_id;
        mBR=br;
    }


    public int getTag() {
        return tag;
    }

    public void setTag(int mtag) {
        this.tag = mtag;
    }
    public void setOnItmeClickListener(RecyclerViewOnItmeClickListener recyclerViewOnItmeClickListener){
        this.mrecyclerViewOnItmeClickListener=recyclerViewOnItmeClickListener;
    }

    public void setOnBindItmeView(RecyclerViewOnBindItmeView recyclerViewOnItmeClickListener){
        this.mRecyclerViewOnBindItmeView=recyclerViewOnItmeClickListener;
    }

    @Override
    public BindingViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id,parent,false);
        return new BindingViewHodle(binding);
    }


    @Override
    public void onBindViewHolder(BindingViewHodle holder, final int position) {
        final T model=mlist.get(position);
        if (mrecyclerViewOnItmeClickListener!=null) {
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUserBg){
                        mselectPosistion=position;
                        notifyDataSetChanged();
                    }
                    mrecyclerViewOnItmeClickListener.OnItemClick(model,position,tag);
                }
            });
        }
        holder.getBinding().setVariable(mBR,model);
        holder.getBinding().executePendingBindings();

//        if (isUserBg){ //更改点击背景色
//            if (mselectPosistion==position){
//                holder.getBinding().getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
//            } else{
//                holder.getBinding().getRoot().setBackgroundColor(Color.parseColor("#dddddd"));
//            }
//        }

        if (mRecyclerViewOnBindItmeView!=null){
            mRecyclerViewOnBindItmeView.OnBindItmeView( holder.getBinding().getRoot(),model,position,mselectPosistion,tag);
        }
    }
    public void setData(List<T> list){
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public interface RecyclerViewOnItmeClickListener{
        void OnItemClick(Object obejct, int position, int tag);
    }

    public interface RecyclerViewOnBindItmeView{ //对itme中的布局进行操作
        void OnBindItmeView(View view, Object obejct, int position, int selectPosition, int tag);
    }

    public void setUseritmeBg( boolean isUser){
        isUserBg=isUser;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
