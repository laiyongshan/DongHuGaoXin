package suncere.androidapp.lib.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import suncere.androidapp.lib.R;


/**
 * Created by HJO on 2017/8/23.
 */

public class SelectTabView extends LinearLayout {


    private String [] mItmeTexts; //传入的itme文字
    private int mTextSize;
    private int mSelectItmeTextColor; //被选中字体颜色
    private int mUnSelectItmeTextColor; //未被选中的字体颜色
    public int mSelectTextBackgroundColor;//被选中itme背景
    public int mUnSelectTextBackgroundColor;//未被选中的itme背景
//    private int mDefaultSelectIndexs ;//默认选中itme下标
   private int mItmeLayoutHeight;

    private List<LinearLayout> mLayoutParenItme;
    private List<TextView> mTextViewItme;
    private List<ImageView> mImageViewItme;


    private List<Boolean> misSelectView;
    Context mContext;

    OnSelsectItmeClickLisener monSelsectItmeClickLisener;

    public SelectTabView(Context context) {
        this(context,null);
    }

    public SelectTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext =context;
        initView(attrs,defStyleAttr);
    }

    public List<Boolean> getMisSelectView() {
        return misSelectView;
    }

    public void setOnSelsectItmeClickLisener(OnSelsectItmeClickLisener onSelsectItmeClickLisener){
        monSelsectItmeClickLisener=onSelsectItmeClickLisener;
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta= mContext.obtainStyledAttributes(attrs, R.styleable.PollutantsView);
        String _ItmeTexts=ta.getString(R.styleable.PollutantsView_pollutantsTexts);

        mSelectItmeTextColor=ta.getColor(R.styleable.PollutantsView_selectPollutantTextColor, Color.BLACK);
        mSelectTextBackgroundColor=ta.getResourceId(R.styleable.PollutantsView_selectPollutantTextBackground,Color.TRANSPARENT);
        mUnSelectItmeTextColor=ta.getColor(R.styleable.PollutantsView_unSelectPollutantTextColor,Color.WHITE);
        mUnSelectTextBackgroundColor=ta.getResourceId(R.styleable.PollutantsView_unSelectPollutantTextBackground,Color.TRANSPARENT);

//        mDefaultSelectIndex=ta.getInteger(R.styleable.PollutantsView_defaultSelectPollutantIndex,0);
        mTextSize=ta.getInteger(R.styleable.PollutantsView_pollutantstextSize,12 );
//        mTextSize =dp2px(mTextSize);
        mItmeLayoutHeight=ta.getInteger(R.styleable.PollutantsView_pollutantstextHeight,100 );
//        mItmeLayoutHeight =dp2px(mItmeLayoutHeight);

        ta.recycle();
        mLayoutParenItme=new ArrayList<>();
        mTextViewItme=new ArrayList<>();
        mImageViewItme=new ArrayList<>();
        misSelectView=new ArrayList<>();

        if (_ItmeTexts!=null){
            mItmeTexts=_ItmeTexts.split(",");
        }
        addTextView();
    }

    private void addTextView(){

        int tag=0;
        for (String itmeText : mItmeTexts){

            LayoutParams Layoutparams=new LayoutParams(LayoutParams.WRAP_CONTENT,80);
            Layoutparams.weight=1;
            Layoutparams.setMargins(dp2px(10),0,0,0);
            Layoutparams.gravity=Gravity.CENTER;

            LinearLayout layout =new LinearLayout(mContext);
            layout.setGravity(Gravity.CENTER);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setBackgroundResource(mSelectTextBackgroundColor); // 默认在开始阶段  选中所有的itme
            layout.setPadding(dp2px(6),0,dp2px(6),0);
            layout.setTag(tag);
            tag++;

            TextView textView=new TextView(mContext);
            textView.setTextSize(mTextSize);
            textView.setText(itmeText);
            textView.setTextColor(mSelectItmeTextColor);
            textView.setPadding(0,0,dp2px(5),0);
            layout.addView(textView);

            ImageView imageView=new ImageView(mContext);
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.map_select));
            layout.addView(imageView);
            this.addView(layout);

            layout.setLayoutParams(Layoutparams);

            mLayoutParenItme.add(layout);
            mTextViewItme.add(textView);
            mImageViewItme.add(imageView);
            misSelectView.add(true);//默认所有view 被选中

            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index=(Integer) view.getTag();
                    setItmeViewClickState(index);
                    if (monSelsectItmeClickLisener!=null){
                        monSelsectItmeClickLisener.onSelsectItmeClick( mLayoutParenItme.get(index),mTextViewItme.get(index).getText().toString(),index);
                    }

                }
            });
        }
    }

    /**
     *   点击view后 改变其状态
     * @param index
     */
    private  void setItmeViewClickState(int index){
       if (misSelectView.get(index)){ //  true 为选中  上一个状态为选中状态
           misSelectView.set(index,false);
           mLayoutParenItme.get(index).setBackgroundResource(mUnSelectTextBackgroundColor);
           mTextViewItme.get(index).setTextColor(mContext.getResources().getColor(R.color.moretextcorol2));
           mImageViewItme.get(index).setVisibility(View.GONE);
       }else{
           misSelectView.set(index,true);
           mLayoutParenItme.get(index).setBackgroundResource(mSelectTextBackgroundColor);
           mTextViewItme.get(index).setTextColor(Color.WHITE);
           mImageViewItme.get(index).setVisibility(View.VISIBLE);
       }
    }

    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public interface OnSelsectItmeClickLisener{
       void onSelsectItmeClick(View view, String itmetext, int position);
    }
}
