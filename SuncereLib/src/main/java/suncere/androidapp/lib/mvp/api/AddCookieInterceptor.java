package suncere.androidapp.lib.mvp.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import suncere.androidapp.lib.mvp.ui.MyApplication;

/**
 * Created by Hjo on 2017/6/23.
 */

public class AddCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder=chain.request().newBuilder();
            SharedPreferences sharedPreferences= MyApplication.getMyApplicationContext().getSharedPreferences("CookieXML", Context.MODE_PRIVATE);
            String cookie=sharedPreferences.getString("cookie","");

            builder.addHeader("Cookie",cookie);

        return chain.proceed(builder.build());

    }
}
