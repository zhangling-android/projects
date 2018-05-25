package com.zhangling.bluetooth;

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
import com.zhangling.bluetooth.manager.UploadFileManager;
import com.zhangling.bluetooth.model.db.AppConfigDBModel;
import com.zhangling.bluetooth.receiver.NetworkConnectChangedReceiver;

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
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        configRealm();
        configLoger();
        registerNetworkReceiver(new NetworkConnectChangedReceiver(),new IntentFilter());




    }

    private void configLoger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Hides internal method calls up to offset. Default 5
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
}
