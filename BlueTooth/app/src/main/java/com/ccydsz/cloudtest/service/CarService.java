package com.ccydsz.cloudtest.service;

import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.CarModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarService {
    @GET("/api/test/carList")
    public Observable<ResponseModel<List<CarModel>>> loadCars();
}
