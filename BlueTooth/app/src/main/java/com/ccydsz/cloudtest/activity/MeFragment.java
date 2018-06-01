package com.ccydsz.cloudtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.ccydsz.cloudtest.Application;
import com.ccydsz.cloudtest.MainActivity;
import com.ccydsz.cloudtest.R;
import com.ccydsz.cloudtest.base.BaseMvpFragment;
import com.ccydsz.cloudtest.manager.APIManager;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.CarModel;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;
import com.ccydsz.cloudtest.model.db.UserDBModel;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class MeFragment  extends BaseMvpFragment<ConstraintLayout,ResponseModel.NullModel,HomeMvp.View<ResponseModel.NullModel>,HomeMvp.Presenter<ResponseModel.NullModel,HomeMvp.View<ResponseModel.NullModel>>> implements HomeMvp.View<ResponseModel.NullModel> {
    @BindView(R.id.logoutButton)
    public Button logoutButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Realm mRealm= Realm.getDefaultInstance();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //先查找后得到User对象
                        AppConfigDBModel appConfigDBModel = mRealm.where(AppConfigDBModel.class).findFirst();
                        appConfigDBModel.setUserDidLogin(false);
                    }
                });
                APIManager.getInstance().cookieJar.clear();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }

    @Override
    public HomeMvp.Presenter<ResponseModel.NullModel, HomeMvp.View<ResponseModel.NullModel>> createPresenter() {
        return new HomeMvp.PresenterImpl();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        Application.getApplication().getRefWatcher().watch(this);

    }
}
