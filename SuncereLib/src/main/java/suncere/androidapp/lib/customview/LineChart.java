package suncere.androidapp.lib.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import suncere.androidapp.lib.R;

/**
 * Created by Hjo on 2017/11/3 10:27.
 */

public class LineChart extends View {

    private  int  mWidth;  // 控件宽度
    private  int  mHeight;// 控件高度
    private  int  mWidthRange;  // 图表的宽度
    private  int  mHeightRange;// 图表的可用高度

    private  int  mMinX=0;  // X 轴的最小边界值
    private  int  mMaxX=0;// X 轴的最大边界值
    private double mMinY=0.0;  // Y 轴的最小刻度值
    private double mMaxY=500.0;// Y 轴的最大刻度值

    private int  mYScaleCount;// Y轴上的刻度数
    private int  mXScaleCount;// X 轴上的刻度数

    private double mYScaleValue;// Y 轴  每两个刻度之间的间隔值
    private int mYScalePX;// Y 轴  每个像素表示的值

    private int mXScaleWidth;// X 轴刻度宽度
    private int mYScaleWidth;// Y 轴刻度宽度

    private int  mMarginLeft ;//  左边画 Y 轴刻度值的宽度
    private int  mMarginBottom ;//  底部画 X 轴刻度值的宽度

    private int  mMarginLeftWidth ;//  左边画 Y 轴刻度值和减去paddingleft 后的宽度
    private int  mMarginBottomWidth ;//  底部画 X 轴刻度值和减去paddingbottom 后的宽度的宽度

    private OverScroller mOverScroller;//控制滑动
    private VelocityTracker mVelocityTracker;//速度获取
    private int mMaxVelocity; // 最大的滑动速度
    private int mMinVelocity; // 最小的滑动速度

    List<String > mXVaule;  // X 轴数据
    List<Double > mYVaule; // Y 轴数据

    Paint mPaint;
    Paint mPaintLine;//画线
    Paint mPaintLineEffect;//画虚线
    Path mPath;//画虚线

    public LineChart(Context context) {
        this(context,null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }
    // 获取自定义属性值
    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.LineChart,0,0);
        mYScaleCount=typedArray.getInteger(R.styleable.LineChart_YScaleCount,6);
        mXScaleCount=typedArray.getInteger(R.styleable.LineChart_XScaleCount,8);
        mMarginLeft=typedArray.getDimensionPixelSize(R.styleable.LineChart_MarginLeft,40);
        mMarginBottom=typedArray.getDimensionPixelSize(R.styleable.LineChart_MarginLeft,60);
        typedArray.recycle();

        mXVaule=new ArrayList<>();
        mYVaule=new ArrayList<>();

        mPaint =new Paint();
        mPaint.setTextSize(25f);
        mPaint.setStrokeWidth(6f);
        mPaint.setColor(Color.BLACK);

        mPaintLine =new Paint();
        mPaintLine.setTextSize(25f);
        mPaintLine.setStrokeWidth(6f);
        mPaintLine.setColor(Color.BLUE);

        mPaintLineEffect =new Paint();
        mPaintLineEffect.setStrokeWidth(6f);
        mPaintLineEffect.setColor ( Color.RED ) ;
        mPaintLineEffect.setStyle ( Paint.Style.STROKE ) ;//设置画直线格式
        mPaintLineEffect.setPathEffect ( new DashPathEffect( new float [ ] { 10,10}, 0 ) ) ;//设置虚线效果
        mPath = new Path();

        mOverScroller = new OverScroller(this.getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(this.getContext()).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(this.getContext()).getScaledMinimumFlingVelocity();
        checkAPILevel();

    }

    private void initView(){  // 做一些初始化工作
        mMarginLeftWidth= mMarginLeft+this.getPaddingLeft();
        mMarginBottomWidth=mMarginBottom+this.getPaddingBottom();

        mWidthRange=mWidth-mMarginLeftWidth-this.getPaddingRight();
        mHeightRange=mHeight-mMarginBottomWidth-this.getPaddingTop();

        mXScaleWidth=mWidthRange/mXScaleCount;
        mYScaleWidth=mHeightRange/mYScaleCount;

        mYScaleValue=(mMaxY-mMinY)/mYScaleCount;// Y轴每个刻度的间隔
        mYScalePX= (int) (mHeightRange/(mMaxY-mMinY));// 每个值所占的像素个数

    }

    //API小于18则关闭硬件加速  开启硬件加速
    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < 18){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth= MeasureSpec.getSize(widthMeasureSpec); // 宽
        mHeight= MeasureSpec.getSize(heightMeasureSpec); // 高
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
        mMinX=-mXVaule.size()*mXScaleWidth-mMarginLeftWidth+mWidth;
        mMaxX=0;//mWidth-getPaddingRight()
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawY(canvas);
        drawX(canvas);
        drawYTextValue(canvas);

        for (int i=1;i<=mXVaule.size();i++){
            float drawXPoint=i*mXScaleWidth+mMinX;
            if (drawXPoint>getScrollX()+mMarginLeftWidth && drawXPoint<getScrollX()+mWidth-getPaddingLeft()){
                drawXTextValue(canvas,drawXPoint,i-1);
                drawChartPoint(canvas,drawXPoint,i-1);
                drawChartPointText(canvas,drawXPoint,i-1);
                drawChartLine(canvas,drawXPoint,i-1);
                drawChartDottedLine(canvas,drawXPoint,i-1);
            }
        }
    }

    public void setData(List<String > x, List<String > y){ // 设置  X Y 轴数据
        mXVaule.clear();
        mYVaule.clear();
        mXVaule.addAll(x);
        findMaxOrMinYValue( y);

    }

    private void findMaxOrMinYValue(List<String > y){
        for (String value: y){
            double yvalue=string2double(value);
            mYVaule.add(yvalue);
        }
        mYVaule.set(10,0.0);
        mMaxY= Collections.max(mYVaule);
        mMinY= Collections.min(mYVaule);
        mMaxY=mMaxY+mMaxY/10;  // 调整最大最小值
        mMinY=mMinY-mMinY/10;
        if (mMaxY==0.0)mMaxY=500;  // 避免最大值都是0

    }

    private double string2double(String value){
        double yvalue=0;
        try {
            yvalue= Double.valueOf(value);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0.0;

        }
        return yvalue;
    }

    private void drawY(Canvas canvas){ // 画Y 轴
        canvas.drawLine(getScrollX()+mMarginLeftWidth,mHeight-mMarginBottomWidth,
                getScrollX()+mMarginLeftWidth,getPaddingTop(),mPaint);
    }
    private void drawX(Canvas canvas){ // 画X 轴
        canvas.drawLine(getScrollX()+mMarginLeftWidth,mHeight-mMarginBottomWidth,
                getScrollX()+mWidth-getPaddingRight(),mHeight-mMarginBottomWidth,mPaint);
    }
    private void drawYTextValue(Canvas canvas){ // 画Y 轴刻度值
        for(int i=0;i<=mYScaleCount;i++){
            String yValue=Math.round( mMinY+i*mYScaleValue )+"";
            int textWidth= (int) mPaint.measureText(yValue);
            canvas.drawText( yValue,getScrollX()+mMarginLeftWidth-textWidth-dp2px(5), (float) (mHeight-mMarginBottomWidth-mYScaleValue*mYScalePX*i),mPaint);
        }
    }

    private void drawXTextValue(Canvas canvas, float xPoint, int index){  // 画 X 轴刻度值
        String xValue=mXVaule.get(index);
        int textWidth= (int) mPaint.measureText(xValue);
        canvas.drawText(xValue ,xPoint-textWidth/2,mHeight-mMarginBottomWidth+mMarginBottom/2,mPaint);
    }

    private void drawChartPoint(Canvas canvas, float xPoint, int index){ // 画点
        if(mYVaule.get(index)!=0)
            canvas.drawCircle(xPoint, (float) (mHeight-mMarginBottomWidth-mYVaule.get(index)*mYScalePX),dp2px(5),mPaint);
    }


    private void drawChartDottedLine(Canvas canvas, float xPoint, int index){ // 画虚线
        if(mYVaule.get(index)!=0 ){
            mPath.reset(); //  这里需要注意  如果不reset  会产生卡顿
            mPath.moveTo(xPoint, (float) (mHeight-mMarginBottomWidth-mYVaule.get(index)*mYScalePX));
            mPath.lineTo(xPoint,mHeight-mMarginBottomWidth);
            canvas.drawPath(mPath,mPaintLineEffect);
        }
    }

    private void drawChartPointText(Canvas canvas, float xPoint, int index){ // 画点的值
        String yValue=mYVaule.get(index)+"";
        int textWidth= (int) mPaint.measureText(yValue);
        if(mYVaule.get(index)!=0)
            canvas.drawText(yValue,xPoint, (float) (mHeight-mMarginBottomWidth-mYVaule.get(index)*mYScalePX)-textWidth/2,mPaint);
    }

    float mlastPointX;// 上一个点的X轴坐标
    private  void drawChartLine(Canvas canvas ,float xPoint, int index){  // 画线
        if (index>0 ){
            double LastYValue=mYVaule.get(index-1);
            double NewYValue=mYVaule.get(index);
            if (LastYValue!=0 && NewYValue!=0  && Math.abs(mlastPointX-xPoint)==mXScaleWidth){ //  0 值时不画出线段   Math.abs(mlastPointX-xPoint)==mXScaleWidth: 防止不是两个相邻的点之间画出线段
                canvas.drawLine(mlastPointX,(float) (mHeight-mMarginBottomWidth-mYVaule.get(index-1)*mYScalePX),
                        xPoint,(float) (mHeight-mMarginBottomWidth-mYVaule.get(index)*mYScalePX),mPaintLine);


                int mPaintLineColor=mPaintLine.getColor();
                mPaintLine.setStyle(Paint.Style.FILL);
                mPaintLine.setColor(Color.parseColor("#4472AAF9"));
                mPath.reset();
                mPath.moveTo(mlastPointX,(float) (mHeight-mMarginBottomWidth-mYVaule.get(index-1)*mYScalePX));
                mPath.lineTo( xPoint,(float) (mHeight-mMarginBottomWidth-mYVaule.get(index)*mYScalePX));
                mPath.lineTo( xPoint,(float) (mHeight-mMarginBottomWidth));
                mPath.lineTo( mlastPointX,(float) (mHeight-mMarginBottomWidth));
                mPath.close();
                canvas.drawPath(mPath,mPaintLine);
                mPaintLine.setColor(mPaintLineColor);
            }
        }
        mlastPointX=xPoint;
    }


    // 滑动冲突处理
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        //滑动处理
        float mX = 0;
        float mY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX=ev.getX();
                mY=ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);//不拦截
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX()-mX)> Math.abs(ev.getY()-mY)) {//如果横向滑动大于纵向滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);//交给父类处理
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private float mLastX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX=event.getX();
        if (mVelocityTracker==null){
            mVelocityTracker= VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                if (!mOverScroller.isFinished()){
                    mOverScroller.abortAnimation();
                }
                mLastX=currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX=mLastX-currentX;
                mLastX=currentX;
                scrollBy((int) moveX,0);
                break;
            case MotionEvent.ACTION_UP:
                // 抬手后设置惯性滑动
                mVelocityTracker.computeCurrentVelocity(1000,mMaxVelocity);
                int velocityX= (int) mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX)>mMinVelocity)fling(-velocityX);

                if (mVelocityTracker!=null){
                    mVelocityTracker.recycle();
                    mVelocityTracker=null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mVelocityTracker!=null){
                    mVelocityTracker.recycle();
                    mVelocityTracker=null;
                }
                if (!mOverScroller.isFinished()){
                    mOverScroller.abortAnimation();
                }
                break;
        }

        return true;
    }

    private void fling(int velocityX){
        mOverScroller.fling(getScrollX(), 0, velocityX, 0, mMinX, mMaxX, 0, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(),mOverScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) { // 边界值检查
        if (x < mMinX)
        {
            x = mMinX;
        }
        if (x > mMaxX)
        {
            x = mMaxX;
        }
        if (x != getScrollX())
        {
            super.scrollTo(x, y);
        }
    }
    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
