package androidapp.donghugaoxin.suncere.com.customview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import suncere.androidapp.lib.customview.ScreenUtils;
import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * Created by Hjo on 2017/12/29 17:27.
 */

public class ChartSet   {

    List<String > mXVaule=new ArrayList<>();  // X 轴数据
    List<String > mStringYVaule=new ArrayList<>(); // Y 轴数据
    List<Double > mDoubleYVaule=new ArrayList<>(); // Y 轴数据
    List<Integer> mColors=new ArrayList<>(); // 颜色值
    boolean DrawY=true; // 是否画Y 轴
    boolean DrawX=true; // 是否画X 轴
    boolean DrawXYValue=true; // 是否画数值
    boolean DrawBg=true; // 是否画背景色

    int ColorXY= Color.WHITE; // XY轴的颜色
    int ColorTextValue= Color.BLACK; // 值颜色
    int ColorTextXY= Color.BLACK; // XY 轴刻度文字颜色值

    int ColorLine= Color.BLUE;// 折线颜色
    int ColorBg= Color.parseColor("#4472AAF9");// 数值所包围的背景色
    int ColorDotted= Color.RED; // 虚线的颜色

    float sizeXY=3f ;  // XY 轴的大小
    float sizeTextXY1=30f; // XY 轴刻度值文字的大小
    float sizeTextXY2=26f;//XY轴刻度值文字的大小
    float sizeTextValue1=30f; // XY 轴刻度值文字的大小
    float sizeTextValue2=22f;
    float sizePoint=28f; //点的大小
    float sizeLine=5f;// 折线大小
    float sizeDotted=3f;// 折线大小


    public float getSizeDotted() {
        return sizeDotted;
    }

    public void setSizeDotted(float sizeDotted) {
        this.sizeDotted = sizeDotted;
    }

    public int getColorTextValue() {
        return ColorTextValue;
    }

    public void setColorTextValue(int colorTextValue) {
        ColorTextValue = colorTextValue;
    }

    public float getSizeTextValue() {
        if(ScreenUtils.getScreenWidth(MyApplication.getMyApplicationContext())>720)
            return sizeTextValue1;
        else
            return sizeTextValue2;
    }

    public void setSizeTextValue(float sizeTextValue1) {
        this.sizeTextValue1 = sizeTextValue1;
    }

    public int getColorTextXY() {
        return ColorTextXY;
    }

    public void setColorTextXY(int colorTextXY) {
        ColorTextXY = colorTextXY;
    }

    public float getSizeXY() {
        return sizeXY;
    }

    public void setSizeXY(float sizeXY) {
        this.sizeXY = sizeXY;
    }

    public List<String> getmXVaule() {
        return mXVaule;
    }

    public void setmXVaule(List<String> mXVaule) {
        this.mXVaule = mXVaule;
    }

    public List<String> getmStringYVaule() {
        return mStringYVaule;
    }

    public void setmStringYVaule(List<String> mStringYVaule) {
        this.mStringYVaule = mStringYVaule;
    }

    public List<Double> getmDoubleYVaule() {
        return mDoubleYVaule;
    }

    public void setmDoubleYVaule(List<Double> mDoubleYVaule) {
        this.mDoubleYVaule = mDoubleYVaule;
    }

    public List<Integer> getmColors() {
        return mColors;
    }

    public void setmColors(List<Integer> mColors) {
        this.mColors = mColors;
    }

    public boolean isDrawY() {
        return DrawY;
    }

    public void setDrawY(boolean drawY) {
        DrawY = drawY;
    }

    public boolean isDrawX() {
        return DrawX;
    }

    public void setDrawX(boolean drawX) {
        DrawX = drawX;
    }

    public boolean isDrawXYValue() {
        return DrawXYValue;
    }

    public void setDrawXYValue(boolean drawXYValue) {
        DrawXYValue = drawXYValue;
    }

    public boolean isDrawBg() {
        return DrawBg;
    }

    public void setDrawBg(boolean drawBg) {
        DrawBg = drawBg;
    }

    public int getColorXY() {
        return ColorXY;
    }

    public void setColorXY(int colorXY) {
        ColorXY = colorXY;
    }

    public int getColorLine() {
        return ColorLine;
    }

    public void setColorLine(int colorLine) {
        ColorLine = colorLine;
    }

    public int getColorBg() {
        return ColorBg;
    }

    public void setColorBg(int colorBg) {
        ColorBg = colorBg;
    }

    public int getColorDotted() {
        return ColorDotted;
    }

    public void setColorDotted(int colorDotted) {
        ColorDotted = colorDotted;
    }


    public float getSizeTextXY() {
        if(ScreenUtils.getScreenWidth(MyApplication.getMyApplicationContext())>720)
            return sizeTextXY1;
        else
            return sizeTextXY2;
    }

    public void setSizeTextXY(float sizeTextXY1) {
        this.sizeTextXY1 = sizeTextXY1;
    }

    public float getSizePoint() {
        return sizePoint;
    }

    public void setSizePoint(float sizePoint) {
        this.sizePoint = sizePoint;
    }

    public float getSizeLine() {
        return sizeLine;
    }

    public void setSizeLine(float sizeLine) {
        this.sizeLine = sizeLine;
    }
}
