package com.ccydsz.cloudtest.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ccydsz.cloudtest.model.ResponseModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public class BaseMvpFragment<CV extends View,D,V extends Mvp.View<D>, P extends Mvp.Presenter<D,V>> extends MvpLceFragment<CV,ResponseModel<D>,V,P> implements Mvp.View<D>{
    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    public void setData(ResponseModel<D> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage, boolean pullToRefresh) {
        super.showError(new Throwable(errorMessage),pullToRefresh);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(),message, BaseActivity.toastDuration5).show();
    }
}
