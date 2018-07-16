package suncere.androidapp.lib.customview.kjchart;

public enum AQIToolObtainEnum {//获得类型
    Health(0),
    Index(1),
    Color(2),
    Quality(3),
    AbbreviationQuality(4),
    Sugges(5);


    AQIToolObtainEnum(int value)
    {
        this.value=value;
    }

    public int Value()
    {
        return value;
    }

    private int value;
}
