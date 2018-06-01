package com.ccydsz.cloudtest.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ccydsz.cloudtest.base.BaseActivity;
import com.ccydsz.cloudtest.base.Mvp;
import com.ccydsz.cloudtest.model.db.UserDBModel;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.orhanobut.logger.Logger;
import com.ccydsz.cloudtest.R;
import com.ccydsz.cloudtest.base.BaseMvpActivity;
import com.ccydsz.cloudtest.manager.APIManager;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.UserModel;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;
import com.ccydsz.cloudtest.model.rt.UserLoginModel;
import com.ccydsz.cloudtest.service.UserService;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

public class LoginActivity extends BaseMvpActivity<ConstraintLayout,UserModel,UserMvp.View<UserModel>,UserMvp.Presenter<UserModel,UserMvp.View<UserModel>>> implements UserMvp.View<UserModel>{
    @BindView(R.id.login_button)
    public Button loginButton;
    @BindView(R.id.account)
    public EditText account;
    @BindView(R.id.password)
    public EditText password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mNavigationViewModel.setTitle("登录");
        updateNavigationView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginModel userLoginModel = new UserLoginModel();
                userLoginModel.setAccount(account.getText().toString());
                userLoginModel.setPassword(password.getText().toString());
                Map<String,String> datas = new HashMap<>();
                datas.put("account",userLoginModel.getAccount());
                datas.put("password",userLoginModel.getPassword());
                LoginActivity.this.getPresenter().login(datas,false);
            }
        });
    }

    @NonNull
    @Override
    public UserMvp.Presenter createPresenter() {
        return new UserMvp.PresenterImpl();
    }

    @Override
    public void showError(String errorMessage, boolean pullToRefresh) {

    }

    @Override
    public void setData(final ResponseModel<UserModel> data) {
        super.setData(data);
        if (data.getResult()){
            final Realm mRealm= Realm.getDefaultInstance();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //先查找后得到User对象
                    AppConfigDBModel appConfigDBModel = mRealm.where(AppConfigDBModel.class).findFirst();
                    appConfigDBModel.setUserDidLogin(true);
                    if (appConfigDBModel.getUserDBModels() == null){
                        appConfigDBModel.setUserDBModels(new RealmList<UserDBModel>());
                    }
                    boolean isContain = false;
                    for (UserDBModel userDBModel:appConfigDBModel.getUserDBModels()){
                        if (userDBModel.getAccount().equals(data.getData().getAccount())){
                            isContain = true;
                            break;
                        }
                    }
                    if (!isContain){
                        appConfigDBModel.getUserDBModels().add(JSON.parseObject(JSON.toJSONString(data.getData()),UserDBModel.class));
                    }

                }
            });
            UploadFileManager.getInstance().config();
            this.finish();
        }else {
            showToast(data.getMessage());
        }

    }

}
