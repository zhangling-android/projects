package com.zhangling.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.hjm.bottomtabbar.BottomTabBar;
import com.inuker.bluetooth.library.search.SearchResult;
import com.orhanobut.logger.Logger;
import com.zhangling.bluetooth.activity.DataFragment;
import com.zhangling.bluetooth.activity.HomeActivity;
import com.zhangling.bluetooth.activity.HomeFragment;
import com.zhangling.bluetooth.activity.MeFragment;
import com.zhangling.bluetooth.activity.SensorFragment;
import com.zhangling.bluetooth.adapter.ClassDeviceListAdapter;
import com.zhangling.bluetooth.adapter.DeviceListAdapter;
import com.zhangling.bluetooth.base.BaseActivity;
import com.zhangling.bluetooth.manager.BlueToothManager;
import com.zhangling.bluetooth.manager.ClassBlueToothManager;
import com.zhangling.bluetooth.manager.UploadFileManager;
import com.zhangling.bluetooth.util.ZLUtil;
import com.zhangling.bluetooth.view.DeviceListView;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static MainActivity context;
    @BindView(R.id.bottom_tab_bar)
    public BottomTabBar mBottomTabBar;
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
        UploadFileManager.getInstance().config();
    }

    public static MainActivity getMainActivity() {
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
                        Logger.i("%d,%s",position,name);
                    }//添加选项卡切换监听
                }).setCurrentTab(0);




    }





}
