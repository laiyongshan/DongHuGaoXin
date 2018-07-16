package suncere.androidapp.lib.mvp.api;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * Created by Hjo on 2017/5/10.
 */

public class ApiNetManager{

    //    public static final String  BASE_URL="http://10.10.10.18:8022/api/";
    private static ApiNetManager apiNetManager;

    public static ApiNetManager getInstence(){
        if (apiNetManager==null){
            synchronized (ApiNetManager.class){
                if (apiNetManager==null)
                    apiNetManager=new ApiNetManager();
            }
        }
        return apiNetManager;
    }

    /**
     * 获取 RetrofitSrevice
     * @return
     */
//   public  Retrofit   getRetrofit(){
//       //添加日志信息
//       HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
//       httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//       OkHttpClient okHttpClient=new OkHttpClient.Builder()
//               .addInterceptor(httpLoggingInterceptor)
//               .build();
//
//      Retrofit retrofit=new Retrofit.Builder()
//              .client(okHttpClient)
//              .baseUrl(BASE_URL)
//              .addConverterFactory(GsonConverterFactory.create())
//              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//              .build();
//       return retrofit;
//   }

    public  <E> E  getRetrofitService(Class<E> rClass,String baseUrl){
        //添加日志信息
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CookiesInterceptor())
                .addInterceptor(new AddCookieInterceptor())
                .build();
//        okHttpClient.interceptors().add(new CookiesInterceptor());


        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(rClass);
    }

    public String   getCookie(){
        SharedPreferences sharedPreferences= MyApplication.getMyApplicationContext().getSharedPreferences("CookieXML", Context.MODE_PRIVATE);
        String cookie=sharedPreferences.getString("cookie","");
        return cookie;
    }

}
