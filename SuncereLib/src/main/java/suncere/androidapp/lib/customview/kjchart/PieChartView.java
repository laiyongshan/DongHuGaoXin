package suncere.androidapp.lib.customview.kjchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {

	private Paint paint;
	private Canvas mCanvas;
	private int mWidth;
	private int mHeight;

	public int mPadding;
	public int m2R;
	public List<String> mList;
	public int[] mColors;

	public PieChartView(Context context) {
		super(context);
	}

	public PieChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init(){
		mPadding = dp2px(10);
	}
	/** 设置一些变量的尺寸 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		mWidth = widthSize;
		mHeight = heightSize;
		if (mWidth >= mHeight){
			m2R = mHeight;
		}else  {
			m2R = mWidth;
		}
		setMeasuredDimension(mWidth, mHeight);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		mCanvas = canvas;

		mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		mCanvas.save();
		paint = new Paint();
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLUE);

		drawPieChart();

		mCanvas.restore();
	}

	private void drawPieChart(){
		if (null != mList && mList.size() > 0){
			List<Float> values = new ArrayList<>();
			float all = 0;
			for (int i = 0;i<mList.size();i++){
				float value = 0;
				try {
					value = Float.valueOf(mList.get(i));
				}catch (Exception e){

				}
				all += value;
				values.add(value);
			}
			float Angle = 360/all;
			float oldStartAngle = -99;
			for (int i=0;i<values.size();i++){
				paint.setColor(mColors[i]);
				float value = values.get(i);
				float startAngle = oldStartAngle;
				float sweepAngle = value*Angle;
				RectF rectf = null;
				if (mWidth == mHeight){
					rectf = new RectF(mPadding,mPadding,m2R-mPadding,m2R-mPadding);
				}else if (mWidth > mHeight){
					float LeftRinghtIncreaseCenter = (mWidth-m2R)/2;
					rectf = new RectF(mPadding+LeftRinghtIncreaseCenter,mPadding,m2R-mPadding+LeftRinghtIncreaseCenter,m2R-mPadding);
				}else {
					float TopBottomIncreaseCenter = (mHeight-m2R)/2;
					rectf = new RectF(mPadding,mPadding+TopBottomIncreaseCenter,m2R-mPadding,m2R-mPadding+TopBottomIncreaseCenter);
				}
				mCanvas.drawArc(rectf, startAngle, sweepAngle, true, paint);
				oldStartAngle = startAngle + sweepAngle;
			}

		}
	}

	public void bindPieChart(List<String> list,int[] colors){
		mList = list;
		mColors = colors;
		invalidate();
	}

	/** dp转化为px工具 */
	public int dp2px(float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

}
