package com.ccydsz.cloudtest.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ccydsz.cloudtest.activity.UserMvp;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.BaseModel;
import com.ccydsz.cloudtest.model.UI.UserModel;
import com.ccydsz.cloudtest.view.NavigationView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.Map;

/**
 * Created by ysec on 2017/12/12.
 */
public class BaseMvpActivity<CV extends View,D,V extends Mvp.View<D>, P extends Mvp.Presenter<D,V>> extends MvpLceActivity<CV,ResponseModel<D>,V,P> implements Mvp.View<D>,Activity {
    public NavigationView.Model mNavigationViewModel;
    public NavigationView mNavigationView;
    @Override
    public void setupUI() {

    }
    @Override
    public void setupNavigationView() {
        if (mNavigationViewModel == null) {
            mNavigationViewModel = new NavigationView.Model();
        }
        if (mNavigationView == null) {
            NavigationView navView = new NavigationView(this);
            mNavigationView = navView;
            getWindow().addContentView(navView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        updateNavigationView();

    }

    @Override
    public void updateNavigationView() {
        mNavigationView.setTitleText(mNavigationViewModel.getTitle());
        if (mNavigationViewModel.getRightIconName() != null || mNavigationViewModel.getRightIconNameId() >0) {

            if (mNavigationViewModel.getRightIconNameId() >0) {
                mNavigationView.setRightImageBtn(mNavigationViewModel.getRightIconNameId());
            }else {

            }

            mNavigationView.setRightImageButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rightNavigationViewAction(view);
                }
            });
        }else {

        }

        if (mNavigationViewModel.getLeftIconName() != null || mNavigationViewModel.getLeftIconNameId() >0) {

            if (mNavigationViewModel.getLeftIconNameId() >0) {
                mNavigationView.setLeftImageBtn(mNavigationViewModel.getLeftIconNameId());
            }else {

            }

            mNavigationView.setLeftButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    leftNavigationViewAction(view);
                }
            });
        }else {

        }

        if (mNavigationViewModel.getBackgroundColorId()>0) {
            mNavigationView.setBackgroundColor(mNavigationViewModel.getBackgroundColorId());
        }
    }

    @Override
    public void leftNavigationViewAction(View view) {

    }

    @Override
    public void rightNavigationViewAction(View view) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setupNavigationView();
        setupUI();
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);

    }

    @NonNull
    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }


    @Override
    public void setData(ResponseModel<D> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }


    @Override
    public void showError(String errorMessage, boolean pullToRefresh) {
        super.showError(new Throwable(errorMessage),pullToRefresh);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void showToast(String message) {
        Toast.makeText(this,message, BaseActivity.toastDuration5).show();
    }
}



