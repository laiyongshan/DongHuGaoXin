package suncere.androidapp.lib.adapter;

//import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HJO on 2017/5/14.
 * 使用dataBinding  传入两个布局
 */
public class RecyclerViewAdapterTwoView<T ,E> extends RecyclerView.Adapter<BindingViewHodle>{

    private final LayoutInflater mLayoutInflater;

    private int mlayout_id1,mlayout_id0;
    private int mBR0,mBR1;

    private static final int PAGE0=0;
    private static final int PAGE1=1;

    int mPosition;


    private List<T>mlist;//数据源
    private E mpage0Model;
    RecyclerViewOnItmeClickListener mrecyclerViewOnItmeClickListener;

    public RecyclerViewAdapterTwoView(Context context, int layout_id0 , int layout_id1, int br0, int br1){
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlist=new ArrayList<>();
        mlayout_id0=layout_id0;
        mlayout_id1=layout_id1;
        mBR0=br0;
        mBR1=br1;
    }

    public void setOnItmeClickListener(RecyclerViewOnItmeClickListener recyclerViewOnItmeClickListener){
        this.mrecyclerViewOnItmeClickListener=recyclerViewOnItmeClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return PAGE0;
        }else{
            return PAGE1;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public BindingViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType==PAGE0){
            binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id0,parent,false);
        }else{
            binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id1,parent,false);
        }
        return new BindingViewHodle(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHodle holder, final int position) {

        if (position==0){
            holder.getBinding().setVariable(mBR0,mpage0Model);
        }else {
            final T model=mlist.get(position-1);
            if (mrecyclerViewOnItmeClickListener!=null) {
                holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mrecyclerViewOnItmeClickListener.OnItemClick(model,position);
                    }
                });
            }
            holder.getBinding().setVariable(mBR1,model);
            holder.getBinding().executePendingBindings();
        }
    }
    public void setData(List<T> list,E model){
        mlist.clear();
        mlist.addAll(list);
        mpage0Model=model;
        notifyDataSetChanged();
    }

    public interface RecyclerViewOnItmeClickListener{
        void OnItemClick(Object obejct, int position);
    }

    @Override
    public int getItemCount() {
        return mlist.size()+1;
    }
}
