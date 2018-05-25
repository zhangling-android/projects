package com.zhangling.bluetooth.model.db;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserConfigDBMdoel  implements RealmModel {
    private String account;
    private String selectedCar;
    private double selectedFrequency;
    private double selectedSensorFrequency;
    private boolean isAllow4GUpload;
    private boolean isUploadOBDData;
    private boolean isUploadSensorData;
    private boolean isUploadGPSData;
    private boolean isOpenEngineOilChangeNotification;
    private String bluetoothServerUUID;
    private String  bluetoothCharacteristicUUID;
    private String bluetoothName;
}
