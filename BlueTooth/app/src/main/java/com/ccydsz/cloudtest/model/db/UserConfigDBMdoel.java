package com.ccydsz.cloudtest.model.db;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(String selectedCar) {
        this.selectedCar = selectedCar;
    }

    public double getSelectedFrequency() {
        return selectedFrequency;
    }

    public void setSelectedFrequency(double selectedFrequency) {
        this.selectedFrequency = selectedFrequency;
    }

    public double getSelectedSensorFrequency() {
        return selectedSensorFrequency;
    }

    public void setSelectedSensorFrequency(double selectedSensorFrequency) {
        this.selectedSensorFrequency = selectedSensorFrequency;
    }

    public boolean isAllow4GUpload() {
        return isAllow4GUpload;
    }

    public void setAllow4GUpload(boolean allow4GUpload) {
        isAllow4GUpload = allow4GUpload;
    }

    public boolean isUploadOBDData() {
        return isUploadOBDData;
    }

    public void setUploadOBDData(boolean uploadOBDData) {
        isUploadOBDData = uploadOBDData;
    }

    public boolean isUploadSensorData() {
        return isUploadSensorData;
    }

    public void setUploadSensorData(boolean uploadSensorData) {
        isUploadSensorData = uploadSensorData;
    }

    public boolean isUploadGPSData() {
        return isUploadGPSData;
    }

    public void setUploadGPSData(boolean uploadGPSData) {
        isUploadGPSData = uploadGPSData;
    }

    public boolean isOpenEngineOilChangeNotification() {
        return isOpenEngineOilChangeNotification;
    }

    public void setOpenEngineOilChangeNotification(boolean openEngineOilChangeNotification) {
        isOpenEngineOilChangeNotification = openEngineOilChangeNotification;
    }

    public String getBluetoothServerUUID() {
        return bluetoothServerUUID;
    }

    public void setBluetoothServerUUID(String bluetoothServerUUID) {
        this.bluetoothServerUUID = bluetoothServerUUID;
    }

    public String getBluetoothCharacteristicUUID() {
        return bluetoothCharacteristicUUID;
    }

    public void setBluetoothCharacteristicUUID(String bluetoothCharacteristicUUID) {
        this.bluetoothCharacteristicUUID = bluetoothCharacteristicUUID;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    private boolean isOpenEngineOilChangeNotification;
    private String bluetoothServerUUID;
    private String  bluetoothCharacteristicUUID;
    private String bluetoothName;
}
