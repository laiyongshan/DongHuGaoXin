package suncere.androidapp.lib.customview.kjchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartView extends View {

	/** 当前画布 */
	private Canvas mCanvas;

	/** 是否开始绘画 */
	private boolean mIsDrawing;

	public int mLineColor;//折线的颜色
	public int mXLineColor;//x轴的颜色
	public int mYLineColor;//y轴的颜色
	public int mXAxisTextColor;//x轴的字体颜色
	public int mYAxisTextColor;//x轴的字体颜色
	public int mDefaultPointColor;//default Color.BULE 默认点颜色
	public int mDottedLineColor;////default WHITE
	public int mLineWidth;//折线的宽度

	public Paint mLinePaint,paint_dottedLine;//绘制折线的画
	public Paint mCoordinatePaint;//绘制坐标的画笔

	public int mLeftPadding;//坐标轴距离view边距的间距
	public int mBottomPadding;//坐标轴距离view边距的间距

	public int mWidth;//view的宽度
	public int mHeight;//View的高度
	public int mAxisHeight;//y轴的实际高度
	public int mAxisWidth;//x轴的实际高度
	public float mYRange;//y轴的量程
	public float mYUnit;//每个y轴的单位的高度
	public int mXBlank;//X轴最后一个竖线与箭头的空格
	public int mYBlank;//Y轴最后一个竖线与箭头的空格
	public int mXScaleWidth;//每个x轴刻度的宽度
	public int mYScaleWidth;//每个y轴刻度的宽度
	//public int mXScaleNum;//default 10 x轴上的格子数量
	public int mYScaleNum;//y轴上的格子数量

	public int mLineChartPointRadiusSize;//LineChart点半径的大小
	public int mBarChartSize;//BarChart的大小

	public int mXAxisTextSize;//x轴的字体大小
	public int mYAxisTextSize;//y轴的字体大小
	public int mPointTextSize;//点的文本字体大小

	public String mNullString;//判断为空的字符串


	public ChartEnum chartEnum;//图表类型
	public float mYAxisTextDistanceLeft;//y轴文字距离左边距离
	public float mXAxisTextDistanceBottom;//x轴文字距离左边距离

	public boolean mIsBarChartRotatePointText90;//是否旋转BarChart的点文本90度
	public boolean mIsShowPiontToBottomBg;//是否显示点到底部的背景
	public boolean mIsShowPointColor;//是否显示点的颜色
	public boolean mIs_AccordingTo_PointLabelValue_JudgmentColor;//是否根据点的值判断点的颜色
	public boolean mIsUsePlotLineColor;//是否使用Plot线的颜色
	public boolean mIsUsePlotPointColor;//是否使用Plot点的颜色
	public boolean mIsShowPointText;//是否显示点的文本
	public boolean mIsShowYAxis;//是否显示y轴


	public int mXAxisEveryFewParagraphs;//x轴隔几段显示一个 i%mXAxisEveryFewParagraphs == 0

	/** 外部的list，用来存放折线上的值 */
	public List<String> mLines;//单条折线或BarChart
	public List<Plot> mLineChartPlots;//多条折线
	public List<String> mXAxisValues;
	public List<List<String>> mPercentageBaChartLines;//
	public List<List<Integer>> mPercentageBaChartPointColors;//
	public List<Integer> mPointColors;//点颜色数组

	public float max;//最大值
	public float min;//最小值
	public boolean mIs_AccordingTo_List_SetMax;//是否根据list设置最大值
	public boolean mIs_AccordingTo_List_SetMin;//是否根据list设置最小值
	public int defaultMax;//default 500 默认最大值
	public int defaultMin;//默认最小值
	public int PiontToBottomBgColor;
	private int lastX;//最后一次点击的x坐标
	private int mOffset = 0;//偏移量，用来实现平滑移动
	private int mSpeed = 0;//移动速度，用来实现速度递减
	private boolean mIsTouch = false;//是否触摸屏幕
	private int time = 0;//时间计数器，用来快速滚动时候减速
	private double xVelocity = 0;//移动时候X方向上的速度
	private boolean isScroll = true;//是否可以滚动，当不在范围时候不可以滚动
	private AQIToolKJ aqiTool;
	private boolean mIsScrollLast;//是否滑动最后一位

	public boolean isNeedMinValueMoreSmall;//是否再最小值的基础上 再减去一个值

	public int mdefaulYValueTextColor;

	//Paint paintEffect;
	public ChartView(Context context) {
		this(context, null);
	}

	public ChartView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public ChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		commonInit();
	}

	/** 设置一些变量的尺寸 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		mWidth = widthSize;
		mHeight = heightSize;
		setMeasuredDimension(mWidth, mHeight);
	}

	private void commonInit() {
		aqiTool = new AQIToolKJ();
		setFocusable(true);// 设置可以获取焦点
		setFocusableInTouchMode(true);// 进入触摸输入模式后,该控件是否还有获得焦点的能力
		this.setKeepScreenOn(true);// 是否保持屏幕常亮1

		mLinePaint = new Paint();
		mCoordinatePaint = new Paint();
		mLineWidth = dp2px(1);

		paint_dottedLine = new Paint();
		paint_dottedLine.setStrokeWidth(mLineWidth);
		paint_dottedLine.setStyle(Paint.Style.STROKE);
		paint_dottedLine.setPathEffect(new DashPathEffect(new float[]{5,5,5,5},1));

		//mXScaleNum = 10;
		mYScaleNum = 6;
		defaultMax = 500;

		mLineColor = Color.BLACK;
		mXLineColor = Color.BLACK;
		mYLineColor = Color.BLACK;
		mXAxisTextColor = Color.BLACK;
		mYAxisTextColor = Color.BLACK;
		mDefaultPointColor = Color.BLUE;
		mDottedLineColor = Color.WHITE;
		PiontToBottomBgColor = Color.argb(100,255,255,255);

		mXBlank = dp2px(10);
		mYBlank = dp2px(20);
		mLeftPadding = dp2px(30);
		mBottomPadding = dp2px(20);
		mLineChartPointRadiusSize = 15;
		mBarChartSize = dp2px(10);
		mYAxisTextDistanceLeft = dp2px(10);
		mXAxisTextDistanceBottom = dp2px(5);
		mXAxisTextSize = dp2px(10);
		mYAxisTextSize = dp2px(10);
		mPointTextSize = dp2px(10);

		mNullString = "—";
		mdefaulYValueTextColor=Color.BLACK;
	}

	private void initValue() {

		if (mIs_AccordingTo_List_SetMax || mIs_AccordingTo_List_SetMin) {
			List<Float> maxlist = new ArrayList<Float>();
			List<Float> minlist = new ArrayList<Float>();

			if (null != mLineChartPlots && 0 < mLineChartPlots.size()) {
				for (Plot plot : mLineChartPlots) {
					List<String> lines = plot.mLines;
					List<Float> list = new ArrayList<Float>();
					for (int i = 0; i < lines.size(); i++) {
						float value = 0;
						try {
							value = Float.valueOf(lines.get(i));
						} catch (Exception e) {

						}
						list.add(value);
					}

					float max = 0;
					float min = 0;
					if (null != lines && lines.size() != 0){
						max	= Collections.max(list);
						min = Collections.min(list);
					}
					maxlist.add(max);
					minlist.add(min);
				}

				if (mIs_AccordingTo_List_SetMax){
					max = Collections.max(maxlist);
				}else  {
					max = defaultMax;
				}
				if (mIs_AccordingTo_List_SetMin){
					min = Collections.min(minlist);
				}else  {
					min = defaultMin;
				}
			} else if (null != mLines && 0 < mLines.size()) {
				List<Float> list = new ArrayList<Float>();
				for (int i = 0; i < mLines.size(); i++) {
					float integer = 0;
					try {
						integer = Float.valueOf(mLines.get(i));
					} catch (Exception e) {

					}
					list.add(integer);
				}
				if (mIs_AccordingTo_List_SetMax){
					max = Collections.max(list);
				}else  {
					max = defaultMax;
				}
				if (mIs_AccordingTo_List_SetMin){
					min = Collections.min(list);
				}else  {
					min = defaultMin;
				}
			} else {
				max = defaultMax;
				min = defaultMin;
			}
		}else  {
			max = defaultMax;
			min = defaultMin;
		}

		if(isNeedMinValueMoreSmall && mIs_AccordingTo_List_SetMin){ //使用最小值后 再减去一个数值
			if (min<5){
				min=min-min;//以防有负数  所以不直接赋值为0
			}else{
				min=min-5;
			}
		}
		mYRange = max-min;
		mYUnit = mYRange/mYScaleNum;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);


		if (mIsDrawing) {
			setSpeedCut();// 判断是否处于边缘状态
			mCanvas = canvas;

			// 设置画布背景为白色
			//mCanvas.drawColor(0xffffffff);
			drawAxis();
			if (mIsScrollLast) {
				int size;
				if (getMaxSize() > 0){
					size = getMaxSize();
				}else {
					size = getMaxSize();
				}
				mOffset = - mXScaleWidth * size + mAxisWidth;//(mXScaleWidth * mXScaleNum + 2);
				mIsScrollLast = false;
			}
			drawLine();
		}
	}

	private int getMaxSize(){
		if (chartEnum == ChartEnum.PercentageBaChart){
			if (null != mPercentageBaChartLines){
				return mPercentageBaChartLines.size();
			}else  {
				return  0;
			}

		}else {
			if (null != mLineChartPlots){
				int maxSize = 0;
				for (Plot plot : mLineChartPlots){
					if (null != plot.mLines) {
						if (maxSize < plot.mLines.size()) {
							maxSize =  plot.mLines.size();
						}
					}else {
						return maxSize;
					}
				}
				return maxSize;
			}else  {
				if (null != mLines){
					return mLines.size();
				}else  {
					return 0;
				}
			}
		}

	}


	/** 绘制坐标系 */
	private void drawAxis() {
		mCanvas.save();

		mCanvas.translate(mLeftPadding, mHeight - mBottomPadding);

		drawXYLine();
		if (mIsShowYAxis){
			drawYScale();
		}
		mCanvas.restore();
	}

	private void drawLine() {
		mCanvas.save();
		//mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));

		mCanvas.translate(mLeftPadding, mHeight - mBottomPadding);

		// 设置画笔属性
		mLinePaint.setAntiAlias(true);
		mLinePaint.setStrokeWidth(mLineWidth);
		mLinePaint.setColor(mLineColor);
		//mLinePaint.setStyle(Paint.Style.STROKE);

		if (chartEnum == ChartEnum.LineChart){
			LineChart();
			//barChart();

		}else if (chartEnum == ChartEnum.BarChart){
			barChart();
		}else if (chartEnum == ChartEnum.PercentageBaChart){
			PercentageBaChart();
		}
		mCanvas.restore();
	}

	private void LineChart(){
		drawXAxisText();
		if (null != mLineChartPlots) {
			for (int j = 0; j < mLineChartPlots.size(); j++) {
				Plot plot = mLineChartPlots.get(j);
				mLines = plot.mLines;
				commonLineChartDrawLine(plot.mLineColor,plot.mPointColor);
			}
		}else if (null != mLines) {
			commonLineChartDrawLine(0,0);
		}
	}
	Path pathBg = new Path();

	private void commonLineChartDrawLine(int plotLineColor,int plotPointcolor){
		// 如果折线集合不为空
		if (mLines != null && mLines.size() > 0) {

			// 循环绘制所有线条
			for (int i = 1; i < mLines.size(); i++) {
				float previousValue = 0;
				float thisValue = 0;

				String previousValueStr = mLines.get(i - 1);
				String thisValueStr = mLines.get(i);

				int previousColor = mDefaultPointColor;
				int thisColor = mDefaultPointColor;

				if (null != mPointColors) {
					if (mPointColors.size() == mLines.size()) {
						previousColor = mPointColors.get(i - 1);
						thisColor = mPointColors.get(i);
					}
				}
				try {
					previousValue = Float.valueOf(previousValueStr);
					thisValue = Float.valueOf(thisValueStr);
				} catch (Exception e) {
//					previousValue = Integer.valueOf(previousValueStr);
//					thisValue = Integer.valueOf(thisValueStr);
				}

				// 上一个点的xy坐标
				float previousY = (float) ((previousValue * 1.0 - min) / mYRange * (mAxisHeight - mYBlank));
				int previousX = (i - 1) * mXScaleWidth + mOffset;


				// 当前点的xy坐标
				float thisY = (float) ((thisValue * 1.0 - min) / mYRange * (mAxisHeight - mYBlank));
				int thisX = i * mXScaleWidth + mOffset;


				// 保证只绘制坐标轴范围内的部分
				if (previousX > 0 && previousY < mAxisWidth && thisX > 0 && thisY < mAxisWidth) {
					// 两个坐标连线

					if (!previousValueStr.equals(mNullString) && !thisValueStr.equals(mNullString)) {

						//点与点之间的连线
						if (mIsUsePlotLineColor) {
							mLinePaint.setColor(plotLineColor);
						}else {
							mLinePaint.setColor(mLineColor);
						}
						mCanvas.drawLine(previousX, -previousY, thisX, -thisY, mLinePaint);

						if (mIsShowPiontToBottomBg) {
							//填充点到底部的背景
							mLinePaint.setColor(PiontToBottomBgColor);
							pathBg.reset();
							pathBg.moveTo(previousX, -previousY);
							pathBg.lineTo(previousX, 0);
							pathBg.lineTo(thisX,0);
							pathBg.lineTo(thisX,-thisY);
							pathBg.close();
							mCanvas.drawPath(pathBg, mLinePaint);

						}

					}

					if (!previousValueStr.equals(mNullString)) {
//						//画点
						if (mIsShowPointColor) {
							if (mIs_AccordingTo_PointLabelValue_JudgmentColor) {
								mLinePaint.setColor(aqiTool.getAQIColor(previousValueStr));
							}else if (mIsUsePlotPointColor){
								mLinePaint.setColor(plotPointcolor);
							}else {
								mLinePaint.setColor(previousColor);
							}
						} else {
							mLinePaint.setColor(mDefaultPointColor);
						}
						mCanvas.drawCircle(previousX, -previousY, mLineChartPointRadiusSize, mLinePaint);

						//画点上面的文本
						if (mIsShowPointText) {
							mLinePaint.setColor(mdefaulYValueTextColor);
							mLinePaint.setTextSize(mPointTextSize);
							mCanvas.drawText(previousValueStr, previousX - getStringWidth(mPointTextSize, previousValueStr) / 2, -previousY - mLineChartPointRadiusSize, mLinePaint);
						}
						if (mIsShowPiontToBottomBg) {
							drawDottedLine(previousX,-previousY + mLineChartPointRadiusSize,-1);
						}
					}

					if (i == mLines.size() - 1) {
						if (!thisValueStr.equals(mNullString)) {
							//画点
							if (mIsShowPointColor) {
								if (mIs_AccordingTo_PointLabelValue_JudgmentColor) {
									mLinePaint.setColor(aqiTool.getAQIColor(thisValueStr));
								}else if (mIsUsePlotPointColor){
									mLinePaint.setColor(plotPointcolor);
								} else {
									mLinePaint.setColor(thisColor);
								}
							} else {
								mLinePaint.setColor(mDefaultPointColor);
							}

							mCanvas.drawCircle(thisX, -thisY, mLineChartPointRadiusSize, mLinePaint);

							//画点上面的文本
							if (mIsShowPointText) {
								mLinePaint.setColor(mdefaulYValueTextColor);
								mLinePaint.setTextSize(mPointTextSize);
								mCanvas.drawText(thisValueStr, thisX - getStringWidth(mPointTextSize, thisValueStr) / 2, -thisY - mLineChartPointRadiusSize, mLinePaint);
							}

							if (mIsShowPiontToBottomBg) {
								//画点到底部的虚线
								drawDottedLine(thisX,-thisY + mLineChartPointRadiusSize,-1);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 画虚线
	 * **/
	private void drawDottedLine(float moveX, float moveY, float lineY) {

		mLinePaint.setColor(mDottedLineColor);
		float old = 0;
		float DottedLineLegth = dp2px(20);
		float DottedLineSpacing = dp2px(10);

		if (moveY < 0){
			DottedLineLegth = - DottedLineLegth;
			DottedLineSpacing = -DottedLineSpacing;
		}

		for (float i = 0;i<(moveY-lineY)/DottedLineLegth;i++){
			if (i == 0){
				old = lineY;
			}
			if (old+DottedLineSpacing > moveY) {
				mCanvas.drawLine(moveX, old, moveX, old+DottedLineSpacing, mLinePaint);
				old += DottedLineLegth;
			}else  {
				float stopY = 0;
				if (moveY > old){
					stopY = old - (moveY-old);
				}else {
					stopY = old - (old-moveY);
				}
				mCanvas.drawLine(moveX, old, moveX, stopY, mLinePaint);
			}
		}
	}

	/**
	 * 字符串的宽度
	 * */
	private float getStringWidth(float textSize, String str){
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		float strwidth = paint.measureText(str);
		return strwidth;
	}


	private void drawXAxisText() {
		if (null != mXAxisValues ) {
			for (int i = 1; i < mXAxisValues.size(); i++) {
				String XAxisValueStr = mXAxisValues.get(i);
				String previousXAxisValueStr = mXAxisValues.get(i - 1);
				int thisX = i * mXScaleWidth + mOffset;
				int previousX = (i - 1) * mXScaleWidth + mOffset;

				if (previousX > 0 && previousX < mAxisWidth && thisX > 0) {
					drawXAxisText(previousXAxisValueStr, previousX, i);
					if (i == mXAxisValues.size() - 1) {
						drawXAxisText(XAxisValueStr, thisX, i);
					}
				}
			}
		}
	}
	private void drawXAxisText(String XAxisValueStr,int x,int i){
		mLinePaint.setColor(mXAxisTextColor);
		if (mXAxisEveryFewParagraphs == 0) {
			mLinePaint.setTextSize(mXAxisTextSize);
			mCanvas.drawText(XAxisValueStr, x - getStringWidth(mXAxisTextSize, XAxisValueStr) / 2, mBottomPadding - mXAxisTextDistanceBottom, mLinePaint);
		} else {
			if (i % mXAxisEveryFewParagraphs == 0) {
				mLinePaint.setTextSize(mXAxisTextSize);
				mCanvas.drawText(XAxisValueStr, x - getStringWidth(mXAxisTextSize, XAxisValueStr) / 2, mBottomPadding - mXAxisTextDistanceBottom, mLinePaint);
			}
		}
	}

	private void barChart(){

		drawXAxisText();

		if (mLines != null && mLines.size() > 0) {
			for (int i = 0; i < mLines.size(); i++) {
				float thisValue = 0;
				String thisValueStr = mLines.get(i);
				try {
					thisValue = Float.valueOf(thisValueStr);
				} catch (Exception e) {

				}
				int thisColor = mDefaultPointColor;

				if (null != mPointColors){
					if (mPointColors.size() == mLines.size()){
						thisColor = mPointColors.get(i);
					}
				}
				// 当前点的xy坐标
				int thisY = (int) ((thisValue * 1.0 - min) / mYRange * (mAxisHeight - mYBlank));
				int thisX = i * mXScaleWidth + mOffset;
				// 保证只绘制坐标轴范围内的部分
				if (thisY < mAxisWidth  &&  thisX > 0) {

					if (!thisValueStr.equals(mNullString)) {


						if (mIsShowPointColor){
							if (mIs_AccordingTo_PointLabelValue_JudgmentColor){
								mLinePaint.setColor(aqiTool.getAQIColor(thisValueStr));
							}else {
								mLinePaint.setColor(thisColor);
							}
						}else {
							mLinePaint.setColor(mDefaultPointColor);
						}

						//	mLinePaint.setColor(Color.BLACK);


						Path path = new Path();
						path.moveTo(thisX - mBarChartSize / 2, -thisY);
						path.lineTo(thisX - mBarChartSize / 2, 0);
						path.lineTo(thisX + mBarChartSize / 2, 0);
						path.lineTo(thisX + mBarChartSize / 2, -thisY);
						mCanvas.drawPath(path, mLinePaint);

						mLinePaint.setColor(mdefaulYValueTextColor);
						mLinePaint.setTextSize(mPointTextSize);

						if (mIsShowPointText) {
							if (mIsBarChartRotatePointText90) {
								mCanvas.rotate(-90, thisX + 10, -thisY - 10);
								mCanvas.drawText(thisValueStr, thisX + 10, -thisY - 10, mLinePaint);
								mCanvas.rotate(90, thisX + 10, -thisY - 10);
							} else {
								mCanvas.drawText(thisValueStr, thisX - getStringWidth(mPointTextSize, thisValueStr) / 2, -thisY - 10, mLinePaint);
							}
						}
					}
				}
			}
		}
	}

	private void PercentageBaChart() {
		drawXAxisText();
		float TotalLength = (float) ((max * 1.0 - min) / mYRange * (mAxisHeight - mYBlank));
		if (mPercentageBaChartLines != null && mPercentageBaChartLines.size() > 0) {

			for (int i = 0; i < mPercentageBaChartLines.size(); i++) {
				List<String> valueList = mPercentageBaChartLines.get(i);
				List<Integer> colorList = mPercentageBaChartPointColors.get(i);

				float oldHeight = 0;
				float all = 0;
				float add = 0;
				for (int j =0; j<valueList.size();j++){
					String thisValueStr = valueList.get(j);
					float thisValue = 0;
					try {
						thisValue = Float.valueOf(thisValueStr);
					} catch (Exception e) {

					}
					all += thisValue;
				}
				if (all < max) {
					add = (max - all)/valueList.size();
				}
				int thisX = i * mXScaleWidth + mOffset;

				for (int j =0; j<valueList.size();j++) {
					String thisValueStr = valueList.get(j);
					float thisValue = 0;
					try {
						thisValue = Float.valueOf(thisValueStr);
					} catch (Exception e) {

					}
					float height;
					if (j == 0) {
						height = (float) ((float) TotalLength - ((TotalLength/max)*(thisValue+add)));

					}else {
						height = (float) (oldHeight - ((TotalLength/max)*(thisValue+add)));

					}

					Path path = new Path();
					mLinePaint.setColor(colorList.get(j));
					if (thisX > 0) {

						if (j == 0) {
							path.moveTo(thisX - mBarChartSize / 2, -TotalLength);
							path.lineTo(thisX - mBarChartSize / 2, -height);
							path.lineTo(thisX + mBarChartSize / 2, -height);
							path.lineTo(thisX + mBarChartSize / 2, -TotalLength);
							mCanvas.drawPath(path, mLinePaint);
						} else {
							path.moveTo(thisX - mBarChartSize / 2, -oldHeight);
							path.lineTo(thisX - mBarChartSize / 2, -height);
							path.lineTo(thisX + mBarChartSize / 2, -height);
							path.lineTo(thisX + mBarChartSize / 2, -oldHeight);
							mCanvas.drawPath(path, mLinePaint);
						}
					}

					oldHeight = height;

				}

			}
		}
	}

	/**
	 * 设置快速滚动时，末尾的减速
	 */
	private void setSpeedCut() {
		if (!mIsTouch && isScroll) {
			// 通过当前速度计算所对应的偏移量
			mOffset = mOffset + mSpeed;
			setOffsetRange();
		}
		// 每次偏移量的计算
		if (mSpeed != 0) {
			time++;
			mSpeed = (int) (xVelocity + time * time * (xVelocity / 1600.0) - (xVelocity / 20.0) * time);
		} else {
			time = 0;
			mSpeed = 0;
		}

	}

	/**
	 * 绘制x、y轴
	 */
	private void drawXYLine() {
		mAxisWidth = mWidth -  mLeftPadding;
		mAxisHeight = mHeight -  2*mBottomPadding;
		mCoordinatePaint.setStrokeWidth(mLineWidth);
		mCoordinatePaint.setAntiAlias(true);
		mCoordinatePaint.setTextSize(mYAxisTextSize);
		// 绘制x轴
		mCoordinatePaint.setColor(mXLineColor);
		mCanvas.drawLine(0, 0, mAxisWidth, 0, mCoordinatePaint);

		if (mIsShowYAxis){
			// 绘制y轴
			mCoordinatePaint.setColor(mYLineColor);
			mCanvas.drawLine(0, 0, 0, -mAxisHeight, mCoordinatePaint);
		}

	}

	/** 绘制刻度线和箭头 */
	private void drawYScale() {
		// 每个刻度的宽度
		//mXScaleWidth = (int) ((mAxisWidth - mXBlank) * 1.0 / mXScaleNum);
		mYScaleWidth = (int) ((mAxisHeight - mYBlank) * 1.0 / mYScaleNum);
//		String[] tempS = (max+"").split("/.");
//		int resultNum = 0;
//		if (tempS.length > 2){
//			char[] tempC = tempS[1].toCharArray();
//			resultNum = tempC.length;
//		}
//		StringBuilder stringBuilder = new StringBuilder("##0.");
//		for (int i=0;i<resultNum;i++){
//			stringBuilder.append("0");
//		}
//		String pattern = "##0";
//		if (resultNum > 0){
//			pattern = stringBuilder.toString();
//		}

		float all = (max-min);
		float interval = (all/mYScaleNum);
		List<String> yList = new ArrayList<>();
		for (int i=0;i<mYScaleNum+1;i++){
			String str;
			if (max < mYScaleNum){
				DecimalFormat fnum   =   new  DecimalFormat("##0.0");
				str =fnum.format((min+interval*i));
			}else  {
				DecimalFormat fnum   =   new  DecimalFormat("##0");
				str =fnum.format((min+interval*i));
			}
			yList.add(str);
		}
		mCoordinatePaint.setColor(mYAxisTextColor);

		for (int i = 0; i < mYScaleNum+1; i++){
			mCanvas.drawText(yList.get(i),mYAxisTextDistanceLeft-mLeftPadding,-mYScaleWidth * (i), mCoordinatePaint);
		}
	}

	/** 对偏移量进行边界值判定 */
	private void setOffsetRange() {
		if (null != mLines || null != mPercentageBaChartLines || null != mLineChartPlots) {
			int size;
			size = getMaxSize();
			int offsetMax = - mXScaleWidth * size + mAxisWidth;//(mXScaleWidth * mXScaleNum + 2);

			//int offsetMax = mAxisWidth;
			//int offsetMax = -mXScaleWidth * (mLines.size()) + (mXScaleWidth * mXScaleNum + 2);
			int offsetMin = 1 * mXScaleWidth;

			if (mOffset > offsetMin) {
				isScroll = false;
				mOffset = offsetMin;
			} else if (mOffset < offsetMax) {// 如果划出最大值范围
				isScroll = false;
				mOffset = offsetMax;
			} else {
				isScroll = true;
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int rawX = (int) (event.getRawX());
		// 计算当前速度
		VelocityTracker velocityTracker = VelocityTracker.obtain();
		velocityTracker.addMovement(event);
		// 计算速度的单位时间
		velocityTracker.computeCurrentVelocity(50);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 记录触摸点坐标
				lastX = rawX;
				mIsTouch = true;
				break;
			case MotionEvent.ACTION_MOVE:
				// 计算便宜量
				int offsetX = rawX - lastX;
				// 在当前偏移量的基础上增加偏移量
				mOffset = mOffset + offsetX;
				setOffsetRange();
				// 偏移量修改后下次重绘会有变化
				lastX = rawX;
				// 获取X方向上的速度
				xVelocity = velocityTracker.getXVelocity();
				mSpeed = (int) xVelocity;
				break;
			case MotionEvent.ACTION_UP:
				mIsTouch = false;
				break;
		}
		// 计算完成后回收内存
		velocityTracker.clear();
		velocityTracker.recycle();

		if (mXScaleWidth*getMaxSize() < mAxisWidth){
			return true;
		}
		invalidate();
		return true;
	}

	public void refreshChartView() {
		super.invalidate();
		initValue();
	}

	public void bindSingleLineChart(List<String> lines, List<String> xAxisValues) {
		this.mLines = lines;
		this.mXAxisValues = xAxisValues;
		this.mIsDrawing = true;
		this.mIsScrollLast = true;
		this.chartEnum = ChartEnum.LineChart;
		initSingleLineChart();
	}

	public void bindSinglePointToBottomBgLineChart(List<String> lines, List<String> xAxisValues) {
		this.mLines = lines;
		this.mXAxisValues = xAxisValues;
		this.mIsDrawing = true;
		this.mIsScrollLast = true;
		this.chartEnum = ChartEnum.LineChart;
		initSinglePointToBottomBgLineChart();
	}

	public void bindManyLineChart(List<Plot> plots, List<String> xAxisValues) {
		this.mLineChartPlots = plots;
		this.mXAxisValues = xAxisValues;
		this.mIsDrawing = true;
		this.mIsScrollLast = true;
		this.chartEnum = ChartEnum.LineChart;
		initManyLineChart();
	}


	public void bindBarChart(List<String> lines,List<String> xAxisValues) {
		this.mLines = lines;
		this.mXAxisValues = xAxisValues;
		this.mIsDrawing = true;
		this.mIsScrollLast = true;
		this.chartEnum = ChartEnum.BarChart;
		initBarChart();
	}

	public void bindPercentageBaChart(List<List<String>> lines,List<String> xAxisValues) {
		this.mPercentageBaChartLines = lines;
		this.mXAxisValues = xAxisValues;
		this.mIsDrawing = true;
		this.mIsScrollLast = true;
		this.chartEnum = ChartEnum.PercentageBaChart;
		mXAxisEveryFewParagraphs = 2;
		initPercentageBaChart();
	}

	private void initPercentageBaChart(){
		mIsShowYAxis = false;
		mLeftPadding = dp2px(0);
		mIs_AccordingTo_List_SetMax = false;
		max = 32;
		defaultMax = 32;
		mBarChartSize = dp2px(15);
		mXScaleWidth = dp2px(16);
	}

	private void initSingleLineChart(){
		mIsShowYAxis = true;
		mIsShowPointColor = true;
		mIs_AccordingTo_PointLabelValue_JudgmentColor = true;
		mIsShowPointText = true;
		mIs_AccordingTo_List_SetMax = true;
		mXScaleWidth = dp2px(30);
	}

	private void initSinglePointToBottomBgLineChart(){
		mIsShowYAxis = true;
		mIsShowPiontToBottomBg = true;
		mIsShowPointColor = true;
		mIs_AccordingTo_PointLabelValue_JudgmentColor = true;
		mIsShowPointText = true;
		mIs_AccordingTo_List_SetMax = true;
		mXScaleWidth = dp2px(30);
	}

	private void initManyLineChart() {
		mIsShowYAxis = true;
		mIsShowPointColor = true;
		mIsUsePlotLineColor = true;
		mIsUsePlotPointColor = true;
		mIs_AccordingTo_List_SetMax = true;
		mXScaleWidth = dp2px(30);

	}

	private void initBarChart(){
		//mXScaleNum = 24;
		mIsShowYAxis = true;
		mXScaleWidth = dp2px(30);
		mIsBarChartRotatePointText90 = true;
		mIsShowPointColor = true;
		mIs_AccordingTo_PointLabelValue_JudgmentColor = true;
		mIsShowPointText = true;
		mIs_AccordingTo_List_SetMax = true;
		mXAxisEveryFewParagraphs = 2;
	}

	/** dp转化为px工具 */
	public int dp2px(float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
	}

}
