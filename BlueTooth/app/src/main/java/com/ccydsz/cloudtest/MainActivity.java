package com.ccydsz.cloudtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.ccydsz.cloudtest.base.BaseActivity;
import com.hjm.bottomtabbar.BottomTabBar;
import com.orhanobut.logger.Logger;
import com.ccydsz.cloudtest.activity.DataFragment;
import com.ccydsz.cloudtest.activity.HomeFragment;
import com.ccydsz.cloudtest.activity.LoginActivity;
import com.ccydsz.cloudtest.activity.MeFragment;
import com.ccydsz.cloudtest.activity.SensorFragment;
import com.ccydsz.cloudtest.base.BaseMvpActivity;
import com.ccydsz.cloudtest.manager.BlueToothManager;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends BaseActivity {
    private static MainActivity context;
    @BindView(R.id.bottom_tab_bar)
    public BottomTabBar mBottomTabBar;
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        BlueToothManager.getInstance().createBluetoothClient(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    BlueToothManager.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//判断是否需要 向用户解释，为什么要申请该权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
//                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
            }
        }else {

        }
        Realm  mRealm= Realm.getDefaultInstance();
        AppConfigDBModel appConfigDBModel = mRealm.where(AppConfigDBModel.class)
                .equalTo("id", getPackageName()).findFirst();

        if (appConfigDBModel.isUserDidLogin()){
            UploadFileManager.getInstance().config();
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }


    }

    public static BaseActivity getMainActivity() {
        return context;
    }
    @Override
    public void setupUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNavigationViewModel.setTitle("首页");
        mNavigationViewModel.setRightIconNameId(R.mipmap.add);
        updateNavigationView();

        mBottomTabBar
                .init(getSupportFragmentManager(),720, 1280)
                .setChangeColor(getResources().getColor(R.color.colorPrimary),Color.DKGRAY)
                .addTabItem("首页",R.mipmap.icon_tabbar_home, HomeFragment.class)
                .addTabItem("客观数据",R.mipmap.ic_drive_analy, DataFragment.class)
                .addTabItem("陀螺仪",R.mipmap.ic_tabbar_tly, SensorFragment.class)
                .addTabItem("我的",R.mipmap.ic_tabbar_me, MeFragment.class)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        currentIndex = position;
                        switch (currentIndex){
                            case 0:
                                mNavigationViewModel.setTitle("首页");
                                mNavigationViewModel.setRightIconNameId(R.mipmap.add);
                                break;
                            case 1:
                                mNavigationViewModel.setTitle("客观数据");
                                mNavigationViewModel.setRightIconNameId(0);
                                break;
                            case 2:
                                mNavigationViewModel.setTitle("陀螺仪");
                                mNavigationViewModel.setRightIconNameId(0);
                                break;
                            case 3:
                                mNavigationViewModel.setTitle("我的");
                                mNavigationViewModel.setRightIconNameId(0);
                                break;
                        }
                        updateNavigationView();
                    }//添加选项卡切换监听
                }).setCurrentTab(0);




    }

    @Override
    public void rightNavigationViewAction(View view) {
        super.rightNavigationViewAction(view);
        switch (currentIndex){
            case 0:

            case 1:

            case 2:

            case 3:

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            moveTaskToBack(true);
        }
        return false;
    }




}
