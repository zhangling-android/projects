package com.ccydsz.cloudtest.service;

import com.ccydsz.cloudtest.ZLGlobal;
import com.ccydsz.cloudtest.model.ResponseModel;
import com.ccydsz.cloudtest.model.UI.OBDModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface OBDService {
    @Multipart
    @POST("/can/uploadDataFile")
    Observable<ResponseModel<ResponseModel.NullModel>> uploadFile(@PartMap Map<String, RequestBody> params,@Part List<MultipartBody.Part> parts);

    @POST("/can/uploadData")
    Observable<ResponseModel<ResponseModel.NullModel>> uploadData(@Body OBDModel obdModel);
}
