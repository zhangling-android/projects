package com.ccydsz.cloudtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;
import com.ccydsz.cloudtest.receiver.NetworkConnectChangedReceiver;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ysec on 2018/3/20.
 */

public class Application extends android.app.Application{
    private static Application context;

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        configRealm();
        configLoger();
        registerNetworkReceiver(new NetworkConnectChangedReceiver(),new IntentFilter());
        configLeakCanary();



    }

    private void configLoger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(null) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Steven.Zhang's Log")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Logger.addLogAdapter(new DiskLogAdapter());
    }

    /**
     * 获取全局上下文*/
    public static Application getApplication() {
        return context;
    }

    public void registerNetworkReceiver(NetworkConnectChangedReceiver receiver, IntentFilter filter) {
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver,filter);
    }

    public void configRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("BlueTooth.realm") //文件名
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);
        configAppConfigModel();
    }

    public void configAppConfigModel(){
        Realm  mRealm= Realm.getDefaultInstance();
        AppConfigDBModel appConfigDBModel = mRealm.where(AppConfigDBModel.class)
                .equalTo("id", getPackageName()).findFirst();
        if (appConfigDBModel == null){
            final AppConfigDBModel appConfigDBModel1 = new AppConfigDBModel();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(appConfigDBModel1);

                }
            });
        }
    }

    public void configLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }
}
