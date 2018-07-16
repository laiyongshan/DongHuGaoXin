package suncere.androidapp.lib.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hjo on 2017/4/1.
 */

public class MyViewPageAdapter<T> extends PagerAdapter {

    private List<T> list;
    private int layoutId;
    private int BRId;
    private Pools.Pool<View> pool = new Pools.SimplePool<>(4); //造一个池，提高加载效率，与复用率，
    private ViewpagerOnBindView mViewpagerOnBindView;


    public MyViewPageAdapter(List<T> list, int layoutId, int variableId) {
        this.list=new ArrayList<>();
        this.layoutId = layoutId;
        this.BRId = variableId;
    }

    public void setData(List<T> list){

        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
    public void setViewpagerOnBindView(ViewpagerOnBindView viewpagerOnBindView){
        this.mViewpagerOnBindView=viewpagerOnBindView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pool.acquire();
        if (view == null) {
            view = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), layoutId, container, false).getRoot();
        }
        ViewDataBinding bind = DataBindingUtil.bind(view);
        bind.setVariable(BRId, list.get(position));
        if (mViewpagerOnBindView!=null){
            mViewpagerOnBindView.onBindingView( view,list.get(position) );
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        pool.release(view);
    }

    public interface  ViewpagerOnBindView{
       void onBindingView(View view, Object object);
    }

    // 以下解决调用notifyDataSetChanged不刷新的问题
    private int mChildCount = 0;
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;// 反回POSITION_NONE 强迫viewpager重绘所有item
        }
        return super.getItemPosition(object);
    }

}
