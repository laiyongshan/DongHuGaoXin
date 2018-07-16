package androidapp.donghugaoxin.suncere.com.Utils;

import android.graphics.Color;

import androidapp.donghugaoxin.suncere.com.R;

/**
 * Created by Hjo on 2017/11/17 15:47.
 */

public class ColorTools  {



    private static int[]mapItmeBg=new int []{
            R.drawable.map_pollutants_1,
            R.drawable.map_pollutants_2,
            R.drawable.map_pollutants_3,
            R.drawable.map_pollutants_4,
            R.drawable.map_pollutants_5,
            R.drawable.map_pollutants_6,
            R.drawable.map_pollutants_0

    };
    public static int getStationTypeAQIBgFromLevel(String level){
        int i;
        try{
            i=Integer.valueOf(level);
        }catch (Exception e){
            return mapItmeBg[6];
        }
        return mapItmeBg[i-1==-1 ? 6 : (i-1)] ;
    }



    private static int[] Colors = new int[]{
            Color.rgb(0,228,40), //PM2.5
            Color.rgb(202,202,11),// CO
            Color.rgb(255,126,0), // NO2
            Color.rgb(255,0,0),  // PM10
            Color.rgb(128,133,232),// SO2
            Color.rgb(126,0,35), // O3
            Color.rgb(119,124,227),  //  CO_8h
            Color.rgb(76,172,197), // 无首要污染物

    };

    public static  int getPollutantNameLevel(String Quality) {
        if (Quality.contains("PM2.5")||Quality.contains("PM2_5")) {
            return 0;
        } else if (Quality.contains("CO")) {
            return 1;
        } else if (Quality.contains("NO2")) {
            return 2;
       } else if (Quality.contains("PM10")) {
            return 3;
        } else if (Quality.contains("SO2")) {
            return 4;
        } else if (Quality.contains("O3")) {
            return 5;
        } else if (Quality.contains(" CO_8h")) {
            return 6;
        } else {
            return 7;
        }
    }
    public  static int getPollutantNameColor(String PollutantName){
        int level = getPollutantNameLevel(PollutantName);
        return Colors[level];
    }


    private static  int getWarnLevel(String Quality) {
        if (Quality.contains("一般")) {
            return 1;
        } else if (Quality.contains("中等")) {
            return 2;
        } else if (Quality.contains("严重")) {
            return 3;
        }else{
            return 0;
        }
    }

}
