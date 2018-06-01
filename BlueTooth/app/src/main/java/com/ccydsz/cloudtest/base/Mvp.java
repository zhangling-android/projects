package com.ccydsz.cloudtest.base;

import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import com.ccydsz.cloudtest.activity.HomeMvp;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.BaseModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public class Mvp {

    public interface View<D> extends MvpLceView<ResponseModel<D>>{
        void showError(String errorMessage,boolean pullToRefresh);
        void showToast(String message);
    }

    public interface Presenter<D,V extends Mvp.View<D>> extends MvpPresenter<V>{
        public interface OnReslult<T>{
            public void onReslult(T resllt);
        }
    }

    public static class PresenterImpl<D,V extends Mvp.View<D>> extends MvpQueuingBasePresenter<V> implements Presenter<D,V>{
        public Presenter.OnReslult getOnReslult() {
            return onReslult;
        }

        public void setOnReslult(Presenter.OnReslult onReslult) {
            this.onReslult = onReslult;
        }

        public D getData() {
            return data;
        }

        public void setData(D data) {
            this.data = data;
        }

        private Presenter.OnReslult onReslult;
        private D data;
    }
}
