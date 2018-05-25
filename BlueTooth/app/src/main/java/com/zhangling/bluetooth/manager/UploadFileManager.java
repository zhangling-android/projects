package com.zhangling.bluetooth.manager;

import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.zhangling.bluetooth.Application;
import com.zhangling.bluetooth.model.ResponseModel;
import com.zhangling.bluetooth.service.OBDService;
import com.zhangling.bluetooth.util.ZLUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadFileManager {
    static String gpsDataKey = "GpsInfo";
    static String obdDataKey = "ObdInfo";
    static String angularSpeedDataKey = "GyroscopeAngular";
    static String acceleratedDataKey = "GyroscopeAcc";
    private static volatile UploadFileManager instance=null;
    static private int uploadFileMaxCount = 10;
    static private boolean enableOfAll = true;
    static private boolean enableOfNewwork = false;
    private UploadFileManager (){

    }
    public static  UploadFileManager getInstance(){
        if(instance==null){
            synchronized(UploadFileManager.class){
                instance=new UploadFileManager ();
            }
        }
        return instance;
    }



    public void config(){
        RxBusManager.getInstance().tObservable(RxBusManager.NetworkStatus,Boolean.class).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                enableOfNewwork = aBoolean.booleanValue();
            }
        });
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!enableOfAll){
                    return;
                }
                if (!enableOfNewwork){
                    return;
                }
                OBDService obdService = APIManager.getInstance().getRetrofit().create(OBDService.class);//105913
                List<String> fileNames = Arrays.asList(new File(Application.getApplication().getFilesDir().getPath() + "/" + ClassBlueToothManager.OBDFileName).list());
                if (fileNames.size()<=1){
                    return;
                }
                List<MultipartBody.Part> parts = new ArrayList<>();
                Collections.sort(fileNames, new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        try {
                            return -ZLUtil.HHmmssFormat.parse(o1.replace(".txt","")).compareTo(ZLUtil.HHmmssFormat.parse(o2.replace(".txt","")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                for (int i = 1;i< fileNames.size();i++){
                    if (i<=UploadFileManager.uploadFileMaxCount){
                        File file = new File(Application.getApplication().getFilesDir().getPath() + "/" + ClassBlueToothManager.OBDFileName+ "/" +fileNames.get(i));
                        // 根据类型及File对象创建RequestBody（okhttp的类）
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
                        MultipartBody.Part part = MultipartBody.Part.
                                createFormData(UploadFileManager.obdDataKey, file.getName(), requestBody);
                        // 添加进集合
                        parts.add(part);
                        file.delete();
                    }
                }
                //设置Content-Disposition:form-data; name="photo"; filename="xuezhiqian.png"
//                MultipartBody.Part data = MultipartBody.Part.createFormData("photo", file.getName(), photoRequestBody);
                //添加参数用户名和密码，并且是文本类型
                RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), "456");
                RequestBody carId = RequestBody.create(MediaType.parse("text/plain"), "456");
                Map<String,RequestBody> params = new HashMap<>();
//                datas.put(UploadFileManager.obdDataKey,fileRequestBody);
                params.put("uid",uid);
                params.put("carId",carId);
                obdService.uploadFile(params,parts).subscribeOn(Schedulers.io()).subscribe(new Observer<ResponseModel<ResponseModel.NullModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseModel<ResponseModel.NullModel> nullModelResponseModel) {
                        Logger.i("%s",String.valueOf(nullModelResponseModel.getResult()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i("%s",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("onComplete");
                    }
                });
            }
        };
        timer.schedule(task,0,10000);
    }
}
