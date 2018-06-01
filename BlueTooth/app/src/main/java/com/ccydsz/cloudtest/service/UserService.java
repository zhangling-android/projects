package com.ccydsz.cloudtest.service;

import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.UserModel;
import com.ccydsz.cloudtest.model.rt.UserLoginModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserService {
    @FormUrlEncoded
    @POST("/api/user/login")
    Observable<ResponseModel<UserModel>> login(@FieldMap Map<String, String> params);
}
