package androidapp.donghugaoxin.suncere.com.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hjo on 2017/12/28 10:27.
 */

public class ChartView extends View {

    private  int  mMinX=0;  // X 轴的最小边界值
    private  int  mMaxX=0;// X 轴的最大边界值
    private OverScroller mOverScroller;//控制滑动
    private VelocityTracker mVelocityTracker;//速度获取
    private int mMaxVelocity; // 最大的滑动速度
    private int mMinVelocity; // 最小的滑动速度

    private int mWidth;  // view的宽度
    private int mHeight; // view 的高度
    private int mXScaleWidth;// X 轴刻度宽度


    //  需要传入的几个值
    private int  mMarginBottom ; //  底部画 X 轴刻度值的宽度
    private int  mXScaleCount;// X 轴上的刻度数
    private double mYScalePX;// Y 轴  每个像素表示的值
//    List<String > mXVaule;  // X 轴数据
//    List<Double > mYVaule; // Y 轴数据

    Paint mPaint;
    Paint mPaintLine;//画线
    Paint mPaintLineEffect;//画虚线
    Path mPath;//画虚线
    int mXPoint;
    int mYPoint;
    List<Point>mPoints; // 所有坐标点
    List<Point>mDrawPoints; // 保存当前可见坐标点
    ChartSet mChartSet;
    int mStartIndex=0;


    public ChartView(Context context ) {
        super(context);
        initAttrs();
    }
    public ChartView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initAttrs();
    }
    public ChartView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth= MeasureSpec.getSize(widthMeasureSpec); // 宽
//        mHeight= MeasureSpec.getSize(heightMeasureSpec); // 高
//        mXScaleWidth=mWidth/mXScaleCount;
//        setMeasuredDimension(mWidth,mHeight);
//        Log.e("ChartView","调用：onMeasure");
//    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth= w; // 宽
        mHeight= h; // 高
        mXScaleWidth=mWidth/mXScaleCount;
        mMinX=-mChartSet.getmXVaule().size()*mXScaleWidth+mWidth;
        mMaxX=100;
    }

    //API小于18则关闭硬件加速  开启硬件加速
    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < 18){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    private void initAttrs() {
//        mXVaule=new ArrayList<>();
//        mYVaule=new ArrayList<>();
        mPoints=new ArrayList<>();
        mDrawPoints=new ArrayList<>();

        mPaint =new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setTextSize(25f);
//        mPaint.setStrokeWidth(6f);
//        mPaint.setColor(Color.BLACK);

        mPaintLine =new Paint();
//        mPaintLine.setTextSize(25f);
//        mPaintLine.setStrokeWidth(6f);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
//        mPaintLine.setColor(Color.BLUE);

        mPaintLineEffect =new Paint();
        mPaintLineEffect.setAntiAlias(true);
//        mPaintLineEffect.setStrokeWidth(6f);
//        mPaintLineEffect.setColor ( Color.RED ) ;
        mPaintLineEffect.setStyle ( Paint.Style.STROKE ) ;//设置画直线格式
        mPaintLineEffect.setPathEffect ( new DashPathEffect( new float [ ] { 10,10}, 0 ) ) ;//设置虚线效果
        mPath = new Path();

        mOverScroller = new OverScroller(this.getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(this.getContext()).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(this.getContext()).getScaledMinimumFlingVelocity();
        checkAPILevel();

        //第一次进入，跳转到设定刻度
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollTo(mMaxX,0);
            }
        });
    }

    public void setChartData( ChartSet chartSet,int  marginBottom,int  xScaleCount,double yScalePX){
//        mXVaule.clear();
//        mYVaule.clear();
//        mXVaule.addAll(x);
//        mYVaule.addAll(y);
        mChartSet=chartSet;
        mMarginBottom=marginBottom;
        mXScaleCount=xScaleCount;
        mYScalePX=yScalePX;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPoints.clear();
        mDrawPoints.clear();
        mStartIndex=-1;
        for (int i=1;i<=mChartSet.getmXVaule().size();i++){
            mXPoint=i*mXScaleWidth+mMinX;  // X
            mPoints.add(new Point(mXPoint,(int) (mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(i-1)*mYScalePX)));
            if (mXPoint>getScrollX()-mXScaleWidth && mXPoint<getScrollX()+mWidth+mXScaleWidth){
                if (mStartIndex==-1)mStartIndex=i-1;
//                double y=mChartSet.getmDoubleYVaule().get(i-1)*mYScalePX;
//                double yy=mHeight-mMarginBottom-getPaddingBottom();
//                double yyy=yy-y;
//                float p=(float) (mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(i-1)*mYScalePX);
//                int yyyyy=(int) (mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(i-1)*mYScalePX);
                mYPoint= (int) Math.round(  (mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(i-1)*mYScalePX) );
                mDrawPoints.add(new Point(mXPoint,mYPoint));
               if (mChartSet.isDrawXYValue())drawXTextValue(canvas,mXPoint,i-1);
                drawChartDottedLine(canvas,mXPoint,mYPoint,i-1);
                drawChartLine(canvas,mXPoint,i-1);
//                drawChartPoint(canvas,mXPoint,mYPoint,i-1);
                drawChartPointText(canvas,mXPoint,mYPoint,i-1);
            }
        }
        drawChartPoint(canvas);
       if (mChartSet.isDrawBg())drawBg(canvas);
    }
    private void drawXTextValue(Canvas canvas, float xPoint, int index){  // 画 X 轴刻度值
        int color=mPaint.getColor();
        float size=mPaint.getTextSize();
        mPaint.setTextSize(mChartSet.getSizeTextXY());
        mPaint.setColor(mChartSet.getColorTextXY());
        String xValue=mChartSet.getmXVaule().get(index);
        int textWidth= (int) mPaint.measureText(xValue);
        canvas.drawText(xValue ,xPoint-textWidth/2,mHeight-mMarginBottom/2,mPaint);
        mPaint.setTextSize(size);
        mPaint.setColor(color);
    }

    private void drawChartPoint(Canvas canvas){ // 画点
        for (int i=0;i<mDrawPoints.size();i++){
            if(mChartSet.getmDoubleYVaule().get(mStartIndex+i)!=0){
                int color=mPaint.getColor();
                float width=mPaint.getStrokeWidth();
                mPaint.setColor(mChartSet.getmColors().get(mStartIndex+i));
                mPaint.setStrokeWidth(mChartSet.getSizePoint());
                canvas.drawCircle(mDrawPoints.get(i).x, mDrawPoints.get(i).y,dp2px(5),mPaint);
                mPaint.setStrokeWidth(width);
                mPaint.setColor(color);
            }
        }
    }
    private void drawChartDottedLine(Canvas canvas, float xPoint,float yPoint, int index){ // 画虚线

        if(mChartSet.getmDoubleYVaule().get(index)!=0 ){
            int color=mPaintLineEffect.getColor();
            float width=mPaintLineEffect.getStrokeWidth();
            mPaintLineEffect.setColor(mChartSet.getColorDotted());
            mPaintLineEffect.setStrokeWidth(mChartSet.getSizeDotted());
            mPath.reset(); //  这里需要注意  如果不reset  会产生卡顿
            mPath.moveTo(xPoint,yPoint);
            mPath.lineTo(xPoint,mHeight-mMarginBottom);
            canvas.drawPath(mPath,mPaintLineEffect);
            mPaintLineEffect.setStrokeWidth(width);
            mPaintLineEffect.setColor(color);

        }
    }
    private void drawChartPointText(Canvas canvas, float xPoint,float yPoint, int index){ // 画点的值
        String yValue=mChartSet.getmStringYVaule().get(index);
        int textWidth= (int) mPaint.measureText(yValue+"");
        if(mChartSet.getmDoubleYVaule().get(index)!=0){
            int color=mPaint.getColor();
            float size=mPaint.getTextSize();
            mPaint.setTextSize(mChartSet.getSizeTextValue());
            mPaint.setColor(mChartSet.getColorTextValue());
            canvas.drawText(yValue+"",xPoint-20, yPoint -20,mPaint);
            mPaint.setTextSize(size);
            mPaint.setColor(color);
        }

    }
    float mlastPointX; // 上一个点的X轴坐标
    private  void drawChartLine(Canvas canvas ,float xPoint, int index){  // 画线
        if (index>0 ){

            mPaintLine.setColor(mChartSet.getColorLine());
            mPaintLine.setStrokeWidth(mChartSet.getSizeLine());
            double LastYValue=mChartSet.getmDoubleYVaule().get(index-1);
            double NewYValue=mChartSet.getmDoubleYVaule().get(index);
            if (LastYValue!=0 && NewYValue!=0  && Math.abs(mlastPointX-xPoint)==mXScaleWidth){ //  0 值时不画出线段   Math.abs(mlastPointX-xPoint)==mXScaleWidth: 防止不是两个相邻的点之间画出线段
                canvas.drawLine(mlastPointX,(float) (mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(index-1)*mYScalePX),
                        xPoint,(float)(mHeight-mMarginBottom-mChartSet.getmDoubleYVaule().get(index)*mYScalePX),mPaintLine);

                // 画背景
//                int mPaintLineColor=mPaintLine.getColor();
//                mPaintLine.setStyle(Paint.Style.FILL);
//                mPaintLine.setColor(Color.parseColor("#4472AAF9"));
//                mPath.reset();
//                mPath.moveTo(mlastPointX,(float) (mHeight-mMarginBottom-mYVaule.get(index-1)*mYScalePX));
//                mPath.lineTo( xPoint,(float) (mHeight-mMarginBottom-mYVaule.get(index)*mYScalePX));
//                mPath.lineTo( xPoint,(float) (mHeight-mMarginBottom));
//                mPath.lineTo( mlastPointX,(float) (mHeight-mMarginBottom));
//                mPath.close();
//                canvas.drawPath(mPath,mPaintLine);
//                mPaintLine.setColor(mPaintLineColor);
            }
        }
        mlastPointX=xPoint;
    }

    private void drawBg(Canvas canvas){  //  画背景选染色
        int mPaintLineColor=mPaintLine.getColor();
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setColor(mChartSet.getColorBg());
        int index=0;
        index= findFristPoint(index);
        for (int k=index+1;k<mDrawPoints.size()-1;k++){
            if (mDrawPoints.get(k).y==mHeight-mMarginBottom && k-index>1){  // 当前点z值为0  Y 坐标为mHeight-mMarginBottom， 且与上一个点不是相邻点时
                mPath.lineTo( mDrawPoints.get(k-1).x,mHeight-mMarginBottom);
                mPath.lineTo( mDrawPoints.get(index).x,mHeight-mMarginBottom);
                mPath.close();
                canvas.drawPath(mPath,mPaintLine);
                k=k+1;
                index=findFristPoint( k); // 重新设置起始点
            }else if(mDrawPoints.get(k).y==mHeight-mMarginBottom){// 当前点值为0  但与上一个点是相邻的点
                k=k+1;
                index=findFristPoint( k); // 重新设置起始点
            } else{
                mPath.lineTo( mDrawPoints.get(k).x, mDrawPoints.get(k).y);
            }
        }

        if (index!=mDrawPoints.size()-1){ // 特别处理 如果起始点不是最后一个点 则封闭范围 画出背景  如果是，则不再做处理
            mPath.lineTo( mDrawPoints.get(mDrawPoints.size()-1).x, mDrawPoints.get(mDrawPoints.size()-1).y);
            mPath.lineTo( mDrawPoints.get(mDrawPoints.size()-1).x,mHeight-mMarginBottom);
            mPath.lineTo( mDrawPoints.get(index).x,mHeight-mMarginBottom);
            mPath.close();
            canvas.drawPath(mPath,mPaintLine);
        }
        mPaintLine.setColor(mPaintLineColor);
    }

    private int  findFristPoint(int index){
        int t=mDrawPoints.size()-1;  // 直接设置为最后一个点的下标
        for (int i=index;i<mDrawPoints.size();i++){  // 寻找第一个 Y 坐标不为 0 的点
            if (mDrawPoints.get(i).y!=0.0){
                mPath.reset();
                mPath.moveTo(mDrawPoints.get(i).x,mDrawPoints.get(i).y);
                t=i;
                break;
            }
        }
        return t;
    }

    public void onPointClick(MotionEvent event){
        int dp10=mXScaleWidth/2;
        float clcikX=event.getX()+getScrollX();
        float clcikY=event.getY();
        if (!mOverScroller.computeScrollOffset()){ // 不是正在滑动中
            for (int i=0;i<mPoints.size();i++){
                float pointX=mPoints.get(i).x;
                float pointY=mPoints.get(i).y;
                if ( clcikX>=pointX-dp10 && clcikX<=pointX+dp10 && clcikY>=pointY-mXScaleWidth && clcikY<=pointY+mXScaleWidth ){
                    Log.e("clcik","点击了：index="+i+"   值为：x="+mChartSet.getmXVaule().get(i)+"   y="+mChartSet.getmDoubleYVaule().get(i));
                    break;
                }
            }
        }

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

    /********  滑动部分  **********/
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
                onPointClick(event);
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
    /********  滑动部分  **********/


    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
