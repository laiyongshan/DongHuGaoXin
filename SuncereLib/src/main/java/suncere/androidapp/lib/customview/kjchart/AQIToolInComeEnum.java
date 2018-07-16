package suncere.androidapp.lib.customview.kjchart;

/**
 * Created by apple on 2017/3/13.
 */

public enum AQIToolInComeEnum {//传入类型
    AQI(0),
    Quality(1);


    AQIToolInComeEnum(int value)
    {
        this.value=value;
    }

    public int Value()
    {
        return value;
    }

    private int value;
}
