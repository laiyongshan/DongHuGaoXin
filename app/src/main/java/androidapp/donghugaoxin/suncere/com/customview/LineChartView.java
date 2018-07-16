package androidapp.donghugaoxin.suncere.com.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;


/**
 * Created by Hjo on 2017/12/28 09:25.
 */

public class LineChartView extends ViewGroup {
    private  int  mWidth;  // 控件宽度
    private  int  mHeight;// 控件高度

    private double mMinY=0.0;  // Y 轴的最小刻度值
    private double mMaxY=500.0;// Y 轴的最大刻度值

    private int  mYScaleCount;// Y轴上的刻度数
    private int  mXScaleCount;// X 轴上的刻度数

    private double mYScaleValue;// Y 轴  每两个刻度之间的间隔值
    private double mYScalePX;// Y 轴  每个像素表示的值

    private int  mMarginLeft ;//  左边画 Y 轴刻度值的宽度
    private int  mMarginBottom ;//  底部画 X 轴刻度值的宽度

//    List<String > mXVaule;  // X 轴数据
//    List<Double > mYVaule; // Y 轴数据
    Paint mPaint;

    ChartView  mChartView;
    Context mContext ;
    ChartSet mChartSet;

    public LineChartView(Context context) {
        super(context);
    }
    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mChartSet=new ChartSet();
        initAttrs(context,attrs);
        mContext = context;

//        mChartView = new ChartView(mContext);
//        LayoutParams layoutParams = new LayoutParams(1005, ViewGroup.LayoutParams.MATCH_PARENT);
//        mChartView.setLayoutParams(layoutParams);
////        mChartView.setBackgroundColor(Color.RED);
//        addView(mChartView);
        setWillNotDraw(false);


    }
    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(context,attrs);

//        setWillNotDraw(false);// 调用
//        mChartView = new ChartView(mContext,this);
//        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mChartView.setLayoutParams(layoutParams);
//        addView(mChartView);
    }


    // 获取自定义属性值
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.LineChart,0,0);
        mYScaleCount=typedArray.getInteger(R.styleable.LineChart_YScaleCount,6);
        mXScaleCount=typedArray.getInteger(R.styleable.LineChart_XScaleCount,8);
        mMarginLeft=typedArray.getDimensionPixelSize(R.styleable.LineChart_MarginLeft,0);
        mMarginBottom=typedArray.getDimensionPixelSize(R.styleable.LineChart_MarginLeft,60);
        typedArray.recycle();
//        mXVaule=new ArrayList<>();
//        mYVaule=new ArrayList<>();
        mPaint =new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setTextSize(25f);
//        mPaint.setStrokeWidth(6f);
//        mPaint.setColor(Color.BLACK);

        setWillNotDraw(false);
    }

    private void initView(){
        mYScaleValue=(mMaxY-mMinY)/(mYScaleCount);// Y轴每个刻度的间隔
        mYScalePX=  (mHeight-mMarginBottom-this.getPaddingBottom()-this.getPaddingTop()) / (mMaxY-mMinY) ;// 每个值所占的像素个数

        mChartView = new ChartView(mContext);
        removeAllViews();
        addView(mChartView);
        setWillNotDraw(false);
        mChartView.setChartData(mChartSet,mMarginBottom,mXScaleCount,mYScalePX);
    }

    public void setData(List<String > x, List<String > y){ // 设置  X Y 轴数据
//        mXVaule.clear();
//        mYVaule.clear();
//        mXVaule.addAll(x);
//        findMaxOrMinYValue( y );
//        initView();
//        requestLayout();
//        invalidate();
    }
    public void setData( ChartSet chartSet){
        this.mChartSet=chartSet;
//        mXVaule.clear();
//        mYVaule.clear();
//        mXVaule.addAll(x);
        findMaxOrMinYValue(  );
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (mChartView!=null){
            int width = mChartView.getMeasuredWidth();
            int height = mChartView.getMeasuredHeight();
            int left=l+mMarginLeft;
//            int top=t+getPaddingTop();
            mChartView.layout(left, 0, width, height);
        }


    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth= MeasureSpec.getSize(widthMeasureSpec);
//        mHeight= MeasureSpec.getSize(heightMeasureSpec);
//    }

        @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth= MeasureSpec.getSize(widthMeasureSpec);
        mHeight= MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);

     for (int i=0;i<this.getChildCount();i++){// 测量子view
         this.measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
     }

    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
////        initView();
//    }



    private void findMaxOrMinYValue(){
        List<Double> mYVaule=new ArrayList();
        for (String value: mChartSet.getmStringYVaule()){
            double yvalue=string2double(value);
            mYVaule.add(yvalue);
        }
        mChartSet.setmDoubleYVaule(mYVaule);
        mMaxY= Collections.max(mYVaule);
//        mMinY= Collections.min(mYVaule);
        mMaxY=mMaxY+mMaxY/9;  // 调整最大最小值
//        mMinY=mMinY-mMinY/10;
        mMinY=0;
        if (mMaxY==0.0)mMaxY=500;  // 避免最大值都是0

    }

    private double string2double(String value){
        double yvalue=0;
        try {
            yvalue= Double.valueOf(value);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;

        }
        return yvalue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (mChartSet.isDrawY())drawY(canvas);
        if (mChartSet.isDrawX()) drawX(canvas);
//        if (mChartSet.isDrawY()) drawYTextValue(canvas);
    }

    private void drawY(Canvas canvas){ // 画Y 轴

        mPaint.setColor(mChartSet.getColorXY());
        mPaint.setStrokeWidth(mChartSet.getSizeXY());
        canvas.drawLine(mMarginLeft+this.getPaddingLeft(),mHeight-mMarginBottom-getPaddingBottom(),
                mMarginLeft+this.getPaddingLeft(),getPaddingTop(),mPaint);
    }
    private void drawX(Canvas canvas){ // 画X 轴
        mPaint.setColor(mChartSet.getColorXY());
        mPaint.setStrokeWidth(mChartSet.getSizeXY());
        canvas.drawLine(mMarginLeft+this.getPaddingLeft(),mHeight-mMarginBottom-getPaddingBottom(),
                mWidth-getPaddingRight(),mHeight-mMarginBottom-getPaddingBottom(),mPaint);
    }
    private void drawYTextValue(Canvas canvas){ // 画Y 轴刻度值
        mPaint.setColor(mChartSet.getColorTextXY());
        mPaint.setTextSize(mChartSet.getSizeTextXY());
        for(int i=0;i<mYScaleCount;i++){
            String   yValue;

            if (mMaxY<mYScaleCount){
                double h=Math.round(   (10.0*mYScaleValue)  ) *i;
                yValue =h/10.0+mMinY +"";
            }else{
                int h= (int) (Math.round(   (10*mYScaleValue)  ) *i);
                yValue =((int)(h/10+mMinY)) +"";
            }
            int textWidth= (int) mPaint.measureText(yValue);

            canvas.drawText( yValue,getScrollX()+mMarginLeft+this.getPaddingLeft()-textWidth-dp2px(5),
                    (float) (mHeight-mMarginBottom-getPaddingBottom()-mYScaleValue*mYScalePX*i),
                    mPaint);
        }

    }
    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}
