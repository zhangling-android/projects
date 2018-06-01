package com.ccydsz.cloudtest.model.db;

import com.ccydsz.cloudtest.Application;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class AppConfigDBModel implements RealmModel {
    @PrimaryKey
    private String id = Application.getApplication().getPackageName();
    public RealmList<UserDBModel> userDBModels;
    private boolean isUserDidLaunch = false;
    private boolean isUserDidLogin = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<UserDBModel> getUserDBModels() {
        return userDBModels;
    }

    public void setUserDBModels(RealmList<UserDBModel> userDBModels) {
        this.userDBModels = userDBModels;
    }

    public boolean isUserDidLaunch() {
        return isUserDidLaunch;
    }

    public void setUserDidLaunch(boolean userDidLaunch) {
        isUserDidLaunch = userDidLaunch;
    }

    public boolean isUserDidLogin() {
        return isUserDidLogin;
    }

    public void setUserDidLogin(boolean userDidLogin) {
        isUserDidLogin = userDidLogin;
    }

    public double getUserLocationLatitude() {
        return userLocationLatitude;
    }

    public void setUserLocationLatitude(double userLocationLatitude) {
        this.userLocationLatitude = userLocationLatitude;
    }

    public double getUserLocationLongitude() {
        return userLocationLongitude;
    }

    public void setUserLocationLongitude(double userLocationLongitude) {
        this.userLocationLongitude = userLocationLongitude;
    }

    private double userLocationLatitude;
    private double userLocationLongitude;
}
