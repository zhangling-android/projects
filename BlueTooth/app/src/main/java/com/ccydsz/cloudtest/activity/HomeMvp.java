package com.ccydsz.cloudtest.activity;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ccydsz.cloudtest.base.Mvp;
import com.ccydsz.cloudtest.manager.APIManager;
import com.ccydsz.cloudtest.manager.ClassBlueToothManager;
import com.ccydsz.cloudtest.manager.RxBusManager;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.BaseModel;
import com.ccydsz.cloudtest.model.UI.CarModel;
import com.ccydsz.cloudtest.service.CarService;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.ccydsz.cloudtest.manager.RxBusManager.DeviceConnectionStatue;

public class HomeMvp {
    public interface View<M> extends Mvp.View<M>{

    }

    public interface Presenter<M,V extends HomeMvp.View<M>> extends Mvp.Presenter<M,V>{
        public void loadAllCars(boolean pullToRefresh);
        public void connectBlueTooth(boolean pullToRefresh, BluetoothDevice device, Mvp.Presenter.OnReslult<String> reslult);
    }


    public static class PresenterImpl<M,V extends HomeMvp.View<M>> extends Mvp.PresenterImpl<M,V> implements Presenter<M,V>{
        @Override
        public void connectBlueTooth(final boolean pullToRefresh, BluetoothDevice device, final Mvp.PresenterImpl.OnReslult<String> reslult) {


            onceViewAttached(new ViewAction<V>() {
                @Override
                public void run(@NonNull V view) {
                    view.showLoading(pullToRefresh);
                }
            });
            boolean isConnected = ClassBlueToothManager.getInstance().connect(device);
            RxBusManager.getInstance().tObservable(DeviceConnectionStatue,String.class).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    reslult.onReslult(s);
                    if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(s)) {
                        onceViewAttached(new ViewAction<V>() {
                            @Override
                            public void run(@NonNull V view) {
                                view.showContent();
                            }
                        });
                    } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(s)) {
                        onceViewAttached(new ViewAction<V>() {
                            @Override
                            public void run(@NonNull V view) {
                                view.showToast("连接失败");
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void loadAllCars(final boolean pullToRefresh) {
            onceViewAttached(new ViewAction<V>() {
                @Override
                public void run(@NonNull V view) {
                    view.showLoading(pullToRefresh);
                }
            });
            APIManager.getInstance().getRetrofit().create(CarService.class).loadCars().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseModel<List<CarModel>>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(final ResponseModel<List<CarModel>> arrayListResponseModel) {
                    setData((M) arrayListResponseModel);
                }

                @Override
                public void onError(final Throwable e) {
                    onceViewAttached(new ViewAction<V>() {
                        @Override
                        public void run(@NonNull V view) {
                            view.showError(e,pullToRefresh);
                        }
                    });
                }

                @Override
                public void onComplete() {
                    onceViewAttached(new ViewAction<V>() {
                        @Override
                        public void run(@NonNull V view) {
                            view.setData((ResponseModel<M>) getData());
                            view.showContent();
                        }
                    });
                }
            });
        }
    }
}
