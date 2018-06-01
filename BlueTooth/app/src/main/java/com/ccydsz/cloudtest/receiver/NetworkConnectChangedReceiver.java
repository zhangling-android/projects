package com.ccydsz.cloudtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.orhanobut.logger.Logger;
import com.ccydsz.cloudtest.manager.RxBusManager;

import io.reactivex.functions.Consumer;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            Network[] activeNetworks = manager.getAllNetworks();
            boolean enable = false;
            for (Network network:activeNetworks){
                NetworkInfo networkInfo = manager.getNetworkInfo(network);
                if (networkInfo.isConnected()){
                    enable = true;
                    break;
                }
            }
            Logger.i("%s",String.valueOf(enable));
            RxBusManager.getInstance().send(RxBusManager.NetworkStatus,new Boolean(enable));
        }
    }
}
