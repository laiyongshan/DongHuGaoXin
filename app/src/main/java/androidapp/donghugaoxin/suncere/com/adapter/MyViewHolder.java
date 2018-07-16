package androidapp.donghugaoxin.suncere.com.adapter;

import android.util.SparseArray;
import android.view.View;

public class MyViewHolder {

	public static <T extends View> T getView(View view, int Id){

		SparseArray<View> holder=(SparseArray<View>) view.getTag();

		if (holder==null ) {//如果没有用来缓存View的集合
			holder=new SparseArray<View>();
			view.setTag(holder);
		}
		View childView=holder.get(Id);//获取根View中储存在集合中的子view
		if (childView==null) {//没有该孩子
			childView=view.findViewById(Id);
			holder.put(Id, childView);
		}

		return (T)childView;

	}






}
