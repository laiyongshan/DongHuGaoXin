package androidapp.donghugaoxin.suncere.com.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import suncere.androidapp.lib.mvp.ui.MyApplication;


/**
 * Created by Hjo on 2017/5/17.
 */

public class ToolUtils {
    public static  Animation getRefreshAnimation( ){
        Animation operatingAnim;//动画
        operatingAnim = AnimationUtils.loadAnimation(MyApplication.getMyApplicationContext(), suncere.androidapp.lib.R.anim.tip);
        operatingAnim.setDuration(1000);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        return  operatingAnim;
    }

    public void setNameAndPss(String name,String ps){
        SharedPreferences.Editor editor=getSharedPreferences("NamePssXML").edit();
        editor.putString("Name",name);
        editor.putString("Ps",ps);
        editor.commit();
    }

    public String[] getNameAndPss(){
        SharedPreferences sharedPreferences=getSharedPreferences("NamePssXML");
        String userName=sharedPreferences.getString("Name",null);
        String ps=sharedPreferences.getString("Ps",null);
        String [] str=new String [2];
        str[0]=userName;
        str[1]=ps;
        return str;
    }

    private   SharedPreferences  getSharedPreferences(String XMLName){
        SharedPreferences sharedPreferences= MyApplication.getMyApplicationContext().getSharedPreferences(XMLName, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public void setLoginTime(){
        SharedPreferences.Editor editor=getSharedPreferences("LoginTimeXML").edit();
        editor.putString("time",getNowTime());
        editor.commit();
    }

    public String getLoginTime(){
        SharedPreferences sharedPreferences=getSharedPreferences("LoginTimeXML");
        String time=sharedPreferences.getString("time","");
        return time;
    }

    //保存用户的登录状态
    public void setIsLogin(boolean isLogin){
        SharedPreferences.Editor editor=getSharedPreferences("IsLoginXML").edit();
        editor.putBoolean("isLogin",isLogin);
        editor.commit();
    }

    //获取用户的登录状态
    public  boolean  getIsLogin(){
        SharedPreferences sharedPreferences=getSharedPreferences("IsLoginXML");
        boolean isLogin=sharedPreferences.getBoolean("isLogin",false);
        return isLogin;
    }


    //设置用户类型
    public void setUserType(String userType){
        SharedPreferences.Editor editor=getSharedPreferences("UserTypeXML").edit();
        editor.putString("userType",userType);
        editor.commit();
    }

    //获取用户类型
    public String getUserType(){
        SharedPreferences sharedPreferences=getSharedPreferences("UserTypeXML");
        String userType=sharedPreferences.getString("userType","");
        return userType;
    }


    /**
     * 获取当前时间
     * */
    public static  String getNowTime() {
        Calendar cal =   Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    /**
     * // 获取上一个小时
     * @return
     */
    public  static String getLastTimeHour() {  // 上一个小时
        Calendar cal =   Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        cal.set(Calendar.HOUR_OF_DAY,hour-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }
    public  static String getLastDay() { // 昨天
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }

    public  static String getLastMonth() {  // 上个月
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }
    public  static String getLastYear() {  // 去年
        Calendar cal =   Calendar.getInstance();
        cal.add(Calendar.YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String nowDate = sdf.format(cal.getTime());
        return nowDate;
    }


    /**
     * 将字符串转为时间  再将时间转为固定格式   返回特定字符串
     * @param str            需要转化的字符串
     * @param dateFormatStr  当前字符串的时间格式  如：yyyy-MM-dd HH:mm:ss
     * @param formatStr      需要转化成的时间格式  如：MM_dd
     * @return
     */
    public static String stringToData(String str,String dateFormatStr,String formatStr){
        String time="更新于---- -- -- --时";
        if (str!=null) {
            str=str.replace("T"," ");
            int index=str.indexOf(".");
            if (index!=-1)
                str=str.substring(0,index);
            DateFormat sdf=new SimpleDateFormat(dateFormatStr);
            Date date = null;
            try {
                date=sdf.parse(str);
            } catch (Exception e) {
                // TODO: handle exception
            }
            SimpleDateFormat s = new SimpleDateFormat(formatStr);
            time=s.format(date).toString();
        }
        return time;
    }

    public static boolean isHan(String str){
        String reg = "[\\u4e00-\\u9fa5]+";
        return str.matches(reg);
    }

}
