package com.zhangling.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @POST("/springboot1.0/user/login")
    Observable<ResponseModel<User>> getUser(@Body User user);
}
