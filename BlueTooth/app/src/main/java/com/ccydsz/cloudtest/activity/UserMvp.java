package com.ccydsz.cloudtest.activity;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.ccydsz.cloudtest.base.Mvp;
import com.ccydsz.cloudtest.manager.APIManager;
import com.ccydsz.cloudtest.manager.UploadFileManager;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.CarModel;
import com.ccydsz.cloudtest.model.UI.UserModel;
import com.ccydsz.cloudtest.model.db.AppConfigDBModel;
import com.ccydsz.cloudtest.model.db.UserDBModel;
import com.ccydsz.cloudtest.model.rt.UserLoginModel;
import com.ccydsz.cloudtest.service.UserService;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

public class UserMvp {
    public interface View<M> extends Mvp.View<M>{

    }
    public interface Presenter<M,V extends UserMvp.View<M>> extends Mvp.Presenter<M,V> {
        public void login(Map<String,String> requests,boolean pull2Refresh);
    }

    public static class PresenterImpl<M,V extends UserMvp.View<M>> extends Mvp.PresenterImpl<M,V> implements Presenter<M,V>{

        @Override
        public void login(Map<String, String> requests,boolean pull2Refresh) {
            UserService service = APIManager.getInstance().getRetrofit().create(UserService.class);
            service.login(requests).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseModel<UserModel>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseModel<UserModel> userModelResponseModel) {
                    setData((M) userModelResponseModel);

                }

                @Override
                public void onError(final Throwable e) {
                    Logger.i("%s",e.getMessage());
                    onceViewAttached(new ViewAction<V>() {
                        @Override
                        public void run(@NonNull V view) {
                            view.showError(e,true);
                        }
                    });
                }

                @Override
                public void onComplete() {
                    onceViewAttached(new ViewAction<V>() {
                        @Override
                        public void run(@NonNull V view) {
                            view.showContent();
                            view.setData((ResponseModel<M>) getData());
                        }
                    });
                }
            });
        }
    }



}
