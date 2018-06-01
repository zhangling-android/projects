package com.ccydsz.cloudtest.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.ccydsz.cloudtest.Application;
import com.ccydsz.cloudtest.ZLGlobal;
import com.ccydsz.cloudtest.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ccydsz.cloudtest.interceptor.RxAddCookiesInterceptor;
import com.ccydsz.cloudtest.interceptor.RxSaveCookiesInterceptor;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIManager {
    private static volatile APIManager instance=null;
    private APIManager (){

    }
    public static APIManager getInstance(){
        if(instance==null){
            synchronized(APIManager.class){
                instance=new APIManager ();
            }
        }
        return instance;
    }

    private Retrofit mRetrofit;

    public ClearableCookieJar cookieJar =
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Application.getApplication()));

    public Retrofit getRetrofit() {
        if (mRetrofit != null){
            return mRetrofit;
        }
        OkHttpClient okHttpClient = new  OkHttpClient.Builder().cookieJar(cookieJar).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZLGlobal.remoteHost)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mRetrofit = retrofit;
        return retrofit;
    }

    public static class PersistentCookieJar extends com.franmontiel.persistentcookiejar.PersistentCookieJar{
        private CookieCache cache;
        private CookiePersistor persistor;
        public PersistentCookieJar(CookieCache cache, CookiePersistor persistor) {
            super(cache, persistor);
            this.cache = cache;
            this.persistor = persistor;
        }

        @Override
        synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cache.addAll(cookies);
            persistor.saveAll(filterPersistentCookies(cookies));
        }
        private static List<Cookie> filterPersistentCookies(List<Cookie> cookies) {
            List<Cookie> persistentCookies = new ArrayList<>();

            for (Cookie cookie : cookies) {
                persistentCookies.add(cookie);
            }
            return persistentCookies;
        }

    }







}
