package suncere.androidapp.lib.customview.kjchart;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AQIToolKJ {

    public int[] Colors = new int[]{
            Color.rgb(22,189,62),
            Color.rgb(218,195,0),
            Color.rgb(237,143,40),
            Color.rgb(218,38,38),
            Color.rgb(156,38,155),
            Color.rgb(140,27,62),
            Color.rgb(109,105,105),
    };

    public String[] Healths = new String[]{
            "空气质量令人满意，基本无空气污染",
            "空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响",
            "易感人群症状有轻度加剧，健康人群出现刺激症状",
            "进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响",
            "心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状",
            "健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病",
            "—"
    };
    public String[] Sugges = new String[]{
            "各类人群可正常活动",
            "极少数异常敏感人群应减少户外活动",
            "儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼",
            "儿童、老年人及心脏病、呼吸系统疾病患者应避免长时间、高强度的户外锻炼，一般人群适量减少户外活动",
            "儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动",
            "儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动",
            "—"
    };
    public String[] Qualitys = new String[]{"优","良","轻度污染","中度污染","重度污染","严重污染","—"};
    public String[] AbbreviationQualitys = new String[]{"优","良","轻度","中度","重度","严重","—"};


    public int getAQILevel(String AQI){
        float i = 0;
        try {
            i = Float.valueOf(AQI);
        }catch (Exception e){
            return 6;
        }

        if (0<=i && i<=50) {
            return 0;
        }else if (50<i && i<=100){
            return 1;
        }else if (100<i && i<=150){
            return 2;
        }else if (150<i && i<=200){
            return 3;
        }else if (200<i && i<=300){
            return 4;
        }else if (i>300){
            return 5;
        }else{
            return 6;
        }

    }
    public int getQualityLevel(String Quality){

        if (Quality.equals("优")) {
            return 0;
        }else if (Quality.equals("良")){
            return 1;
        }else if (Quality.equals("轻度污染")){
            return 2;

        }else if (Quality.equals("中度污染")){
            return 3;

        }else if (Quality.equals("重度污染")){
            return 4;
        }else if (Quality.equals("严重污染")){
            return 5;
        }else{
            return 6;
        }

    }

    public static Object AccordingTo_InComeEnumAndValue_ObtainValue(AQIToolInComeEnum InCome, String InComeValue, AQIToolObtainEnum Obtain){

        AQIToolKJ aqiTool = new AQIToolKJ();
        if (InCome == AQIToolInComeEnum.AQI){
            if (Obtain == AQIToolObtainEnum.Color){
                return aqiTool.getAQIColor(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.Health){
                return aqiTool.getAQIHealth(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.Sugges){
                return aqiTool.getAQISugge(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.Quality){
                return aqiTool.getAQIQuality(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.AbbreviationQuality){
                return aqiTool.getAQIAbbreviationQualitys(InComeValue);
            }else {
                return null;
            }
        }else if (InCome == AQIToolInComeEnum.Quality){
            if (Obtain == AQIToolObtainEnum.Color){
                return aqiTool.getQualityColor(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.Health){
                return aqiTool.getQualityHealth(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.Sugges){
                return aqiTool.getQualitySugge(InComeValue);
            }else if (Obtain == AQIToolObtainEnum.AbbreviationQuality){
                return aqiTool.getQualityAbbreviationQualitys(InComeValue);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }


    /***AQI***/
    public int getAQIColor(String AQI){
        int level = getAQILevel(AQI);
        return Colors[level];
    }
    public String getAQIHealth(String AQI){
        int level = getAQILevel(AQI);
        return Healths[level];
    }
    public String getAQISugge(String AQI){
        int level = getAQILevel(AQI);
        return Sugges[level];
    }
    public String getAQIQuality(String AQI){
        int level = getAQILevel(AQI);
        return Qualitys[level];
    }
    public String getAQIAbbreviationQualitys(String AQI){
        int level = getAQILevel(AQI);
        return AbbreviationQualitys[level];
    }

    /***Quality***/
    public int getQualityColor(String Quality){
        int level = getQualityLevel(Quality);
        return Colors[level];
    }
    public String getQualityHealth(String Quality){
        int level = getQualityLevel(Quality);
        return Healths[level];
    }
    public String getQualitySugge(String Quality){
        int level = getQualityLevel(Quality);
        return Sugges[level];
    }
    public String getQualityAbbreviationQualitys(String Quality){
        int level = getQualityLevel(Quality);
        return AbbreviationQualitys[level];
    }

    /***date***/
    public static String DateFormat(String dateString, String format) {
        dateString = dateString.replace("T", " ");
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            str = sdf2.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }





}
