package suncere.androidapp.lib.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import suncere.androidapp.lib.R;


/**
 * Created by Hjo on 2017/8/3.
 */

public class ScrollTextView extends LinearLayout {

    /***********
     * 使用一个ScrollView嵌套一个TextView 并使用属性动画来达到滑动的效果
     * mPaintde画笔的运用只是用来间接得到TextView字体的测量长度  并未用来绘制字体
     * **********/
    private TextView mTextView;
    private ScrollView mScrollView;

    private Animation mMoveTextOut=null;//滑出动画
    private Animation mMoveTextIn=null;//滑入动画

    private Paint mPaint;

    private boolean mMarqueeNeeded=false;//是否需要滚动   （滚动的前提：字体显示不全时）

    private  float mTextDifference;//需要滚动的相对距离

    private  static final  int DEFAULT_SPEED=60;//滚动的默认速度

    private static final int DEFAULT_ANIMATION_PAUSE = 2000;//滚动完成后  间隔再次的滚动的时间

    private static final int TEXTVIEW_VIRTUAL_WIDTH = 2000;//TextView 的显示宽度

    private int mSpeed = DEFAULT_SPEED;//默认速度

    private int mAnimationPause = DEFAULT_ANIMATION_PAUSE;//停顿的时间   定时滑动时使用

    private boolean mAutoStart = false;  //是否开始滚动效果

    private Interpolator mInterpolator = new LinearInterpolator();//插值器

    private boolean mCancelled = false;//取消动画

    private Runnable mAnimationStartRunnable;//使用动画线程

    private boolean mStarted; //开始

    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }

    public void setPauseBetweenAnimations(int pause) {
        this.mAnimationPause = pause;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public ScrollTextView(Context context) {
        this(context,null);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        InitAttributes(attrs);
    }

    /**
     * 实例化
     */
    private void init() {
        // TODO Auto-generated method stub
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mInterpolator=new LinearInterpolator();
    }
    /**
     * 获取自定义属性
     * @param attrs
     */
    private void InitAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollTextView);
        mSpeed = ta.getInteger(R.styleable.ScrollTextView_speed, DEFAULT_SPEED);//默认的速度为20
        mAnimationPause = ta.getInteger(R.styleable.ScrollTextView_pause, DEFAULT_ANIMATION_PAUSE);//默认停顿的时间是2秒
        mAutoStart = ta.getBoolean(R.styleable.ScrollTextView_autoStart, false);//默认不直接开始滑动
        ta.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() != 1) {//只能有一个TextView
            throw new RuntimeException("MarqueeView must have exactly one child element.");
        }
        if (changed && mScrollView == null) {
            if (!(getChildAt(0) instanceof TextView)) {//子控件只能是TextView
                throw new RuntimeException("子控件必须是TextView");
            }
            initView(getContext());
            prepareAnimation();
            if (mAutoStart) {
                startMarquee();
            }
        }
    }

    public  void startMarquee() {
        // TODO Auto-generated method stub
        if (mMarqueeNeeded) {
            startTextViewAnimation();
        }
        mCancelled=false;
        mStarted=true;
    }

    //使用线程执行动画效果   向右滑动结束后停止两秒再执行
    private void startTextViewAnimation() {
        mAnimationStartRunnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mTextView.startAnimation(mMoveTextOut);
            }
        };
        postDelayed(mAnimationStartRunnable, mAnimationPause);
    }



    private void initView(Context context) {
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        mScrollView=new ScrollView(context);

        //将原来的TextView移走
        mTextView =(TextView)this.getChildAt(0);
        this.removeView(mTextView);

        //将TextView放到ScrollView中
        mScrollView.addView(mTextView,new ScrollView.LayoutParams(TEXTVIEW_VIRTUAL_WIDTH,LayoutParams.WRAP_CONTENT));

        //监听TextView的Text变化
        mTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                final boolean continueAnimation = mStarted;
                reset();//关闭动画  以及终止执行线程
                prepareAnimation();
                cutTextView();
                post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (continueAnimation) {
                            startMarquee();
                        }
                    }
                });
            }
        });
        addView(mScrollView,params);
    }
    protected void prepareAnimation() {

        mPaint.setTextSize(mTextView.getTextSize());
        mPaint.setTypeface(mTextView.getTypeface());

        float mtextWidth=mPaint.measureText(mTextView.getText().toString());//测量字体的长度

        mMarqueeNeeded=mtextWidth>getMeasuredWidth();//如果字体的长度大于TextView的宽度  则进行文字滚动
        mTextDifference=Math.abs(mtextWidth-getMeasuredWidth())+ 5;//滚动的相对距离

        final int duration = (int) (mTextDifference * mSpeed);//持续时间

        //向左滚动
        mMoveTextOut=new TranslateAnimation(0, -mTextDifference, 0, 0);
        mMoveTextOut.setDuration(duration);
        mMoveTextOut.setInterpolator(mInterpolator);
        mMoveTextOut.setFillAfter(true);

        //向右滚动
        mMoveTextIn=new TranslateAnimation(-mTextDifference, 0, 0, 0);
        mMoveTextIn.setDuration(duration);
        mMoveTextIn.setStartOffset(mAnimationPause);
        mMoveTextIn.setInterpolator(mInterpolator);
        mMoveTextIn.setFillAfter(true);

        //动画监听
        mMoveTextOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub
                expandTextView();
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                if (mCancelled) {
                    reset();
                    return;
                }
                mTextView.startAnimation(mMoveTextIn);
            }
        });

        //动画监听
        mMoveTextIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                cutTextView();
                if (mCancelled) {
                    reset();
                    return;
                }
                startTextViewAnimation();//停止一定的时间后再执行
            }
        });
    }

    protected void reset() {
        mCancelled=true;
        if (mAnimationStartRunnable!=null) {
            removeCallbacks(mAnimationStartRunnable);
        }
        mTextView.clearAnimation();//清除动画效果
        mStarted=false;

        //重置动画
        mMoveTextIn.reset();
        mMoveTextOut.reset();
        cutTextView();
        invalidate();//刷新View
    }

    private void expandTextView() {
        ViewGroup.LayoutParams lp = mTextView.getLayoutParams();
        lp.width = TEXTVIEW_VIRTUAL_WIDTH;
        mTextView.setLayoutParams(lp);
    }

    private void cutTextView() {
        // TODO Auto-generated method stub
        if (mTextView.getWidth()!=getMeasuredWidth()) {  //布局上的TextView的宽度与添加内容后的textView宽度不一致时
            ViewGroup.LayoutParams params=mTextView.getLayoutParams();
            params.width=getMeasuredWidth();
            mTextView.setLayoutParams(params);
        }
    }

}
