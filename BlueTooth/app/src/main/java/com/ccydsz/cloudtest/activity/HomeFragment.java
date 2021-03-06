package com.ccydsz.cloudtest.activity;

//import android.app.Fragment;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccydsz.cloudtest.Application;
import com.ccydsz.cloudtest.base.BaseActivity;
import com.ccydsz.cloudtest.base.BaseMvpFragment;
import com.ccydsz.cloudtest.base.Mvp;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.CarModel;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.inuker.bluetooth.library.search.SearchResult;
import com.ccydsz.cloudtest.MainActivity;
import com.ccydsz.cloudtest.R;
import com.ccydsz.cloudtest.adapter.ClassDeviceListAdapter;
import com.ccydsz.cloudtest.adapter.DeviceListAdapter;
import com.ccydsz.cloudtest.manager.BlueToothManager;
import com.ccydsz.cloudtest.manager.ClassBlueToothManager;
import com.ccydsz.cloudtest.manager.RxBusManager;
import com.ccydsz.cloudtest.util.ZLUtil;
import com.ccydsz.cloudtest.view.DeviceListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import io.realm.Realm;

import static com.ccydsz.cloudtest.manager.RxBusManager.DeviceConnectionStatue;
import static com.ccydsz.cloudtest.manager.RxBusManager.DeviceData;
import static com.ccydsz.cloudtest.manager.RxBusManager.SearchClassDevice;
import static com.ccydsz.cloudtest.manager.RxBusManager.SearchDevice;

public class HomeFragment extends BaseMvpFragment<ConstraintLayout,List<CarModel>,HomeMvp.View<List<CarModel>>,HomeMvp.Presenter<List<CarModel>,HomeMvp.View<List<CarModel>>>> implements HomeMvp.View<List<CarModel>> {
//    @BindView(R.id.view)
//    public ConstraintLayout contentView;
    DeviceListView mBlueToothListView;
    SearchResult mSelectedDevice;
    BluetoothDevice mSelectedClassDevice;
    @BindView(R.id.bluetooth_view)
    public RelativeLayout blueToothSuperView;
    @BindView(R.id.bluetooth)
    public ImageButton bluetoothButton;
    @BindView(R.id.car_name)
    public TextView carNameTextView;
    static BlueToothManager.BlueToothType type = BlueToothManager.BlueToothType.CLASS;
    private Unbinder unbinder;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RxBusManager.getInstance().tObservable(SearchDevice,SearchResult.class).subscribe(new Consumer<SearchResult>() {
            @Override
            public void accept(SearchResult searchResult) throws Exception {
                mBlueToothListView.getListAdapter().addDevice(searchResult);
                mBlueToothListView.getListAdapter().notifyDataSetChanged();
            }
        });
        RxBusManager.getInstance().tObservable(SearchClassDevice,BluetoothDevice.class).subscribe(new Consumer<BluetoothDevice>() {
            @Override
            public void accept(BluetoothDevice searchResult) throws Exception {
                mBlueToothListView.getClassListAdapter().addDevice(searchResult);
                mBlueToothListView.getClassListAdapter().notifyDataSetChanged();
            }
        });
        RxBusManager.getInstance().tObservable(DeviceData,String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
//                Logger.i(String.format("蓝牙onNotify的数据: %s",s));
//                LogManager.getInstance().writeLog(s,"obd");
            }
        });






    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        ZLUtil.tinkButtonColor(bluetoothButton, Color.GRAY);
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueToothListView!=null){
                    ((ConstraintLayout)contentView).removeView(mBlueToothListView);
                }
                switch (type){
                    case BLE:
                        BlueToothManager.getInstance().search(3000,3);
                        mBlueToothListView.setListAdapter(new DeviceListAdapter(MainActivity.getMainActivity()));
                        mBlueToothListView.setClassListAdapter(new ClassDeviceListAdapter(MainActivity.getMainActivity()));
                        ConstraintLayout.LayoutParams container = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT
                        );
                        mBlueToothListView.onListItemClick = new DeviceListView.OnListItemClick() {
                            @Override
                            public void onListItemClick(AdapterView<?> l, View v, int position, long id) {
                                SearchResult device = mBlueToothListView.getListAdapter().getDevice(position);
                                mSelectedDevice = device;
                                BlueToothManager.getInstance().connect(device,3,30000,3,20000);
                            }
                        };
                        container.startToStart = blueToothSuperView.getId();
                        container.endToEnd = blueToothSuperView.getId();
                        container.topToBottom = blueToothSuperView.getId();
                        container.height = 300;
                        mBlueToothListView.setLayoutParams(container);
                    case CLASS:
                        ClassBlueToothManager.getInstance().createClassBluetoothClient(MainActivity.getMainActivity());
                        ClassBlueToothManager.getInstance().search();
                        mBlueToothListView = new DeviceListView(MainActivity.getMainActivity());
                        mBlueToothListView.setClassListAdapter(new ClassDeviceListAdapter(MainActivity.getMainActivity()));
                        ConstraintLayout.LayoutParams container2 = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT
                        );
                        mBlueToothListView.onListItemClick = new DeviceListView.OnListItemClick() {
                            @Override
                            public void onListItemClick(AdapterView<?> l, View v, int position, long id) {
                                BluetoothDevice device = mBlueToothListView.getClassListAdapter().getDevice(position);
                                mSelectedClassDevice = device;
                                getPresenter().connectBlueTooth(false, device, new Mvp.PresenterImpl.OnReslult<String>() {
                                    @Override
                                    public void onReslult(String statue) {
                                        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(statue)) {
                                            mBlueToothListView.setVisibility(View.INVISIBLE);
                                        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(statue)) {

                                        }
                                    }
                                });
                            }
                        };
                        container2.startToStart = blueToothSuperView.getId();
                        container2.endToEnd = blueToothSuperView.getId();
                        container2.topToBottom = blueToothSuperView.getId();
                        container2.height = 300;
                        mBlueToothListView.setLayoutParams(container2);
                }

                contentView.addView(mBlueToothListView);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Realm mRealm= Realm.getDefaultInstance();
        AppConfigDBModel appConfigDBModel = mRealm.where(AppConfigDBModel.class)
                .equalTo("id", Application.getApplication().getPackageName()).findFirst();

        if (appConfigDBModel.isUserDidLogin()){
            loadData(true);
        }
    }

    @Override
    public HomeMvp.Presenter createPresenter() {
        return new HomeMvp.PresenterImpl<ResponseModel<List<CarModel>>,HomeMvp.View<ResponseModel<List<CarModel>>>>();
    }


    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "点击";
    }


    @Override
    public void setData(ResponseModel<List<CarModel>> data) {
        carNameTextView.setText(data.getData().get(0).getCarName());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadAllCars(pullToRefresh);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        Application.getApplication().getRefWatcher().watch(this);

    }
}
