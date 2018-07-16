package suncere.androidapp.lib.mvp.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * Created by Hjo on 2017/6/23.
 */
public class CookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

           Response CookieRespone= null;
        try {
            CookieRespone = chain.proceed(chain.request());
            if (! CookieRespone.headers("Set-Cookie").isEmpty()){
                String cookie =  CookieRespone.header("Set-Cookie").substring(0,CookieRespone.header("Set-Cookie").indexOf(";"));
                String url= CookieRespone.request().url().toString();

                if (url.contains("AppLogOn")){
                    SharedPreferences sharedPreferences = MyApplication.getMyApplicationContext().getSharedPreferences("CookieXML", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("cookie",cookie);
                    editor.commit();
                }
                Log.e("cookie","cookie="+cookie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return CookieRespone;
    }

}


