package com.ccydsz.cloudtest.manager;

import com.ccydsz.cloudtest.Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogManager {
    private static volatile LogManager instance=null;
    private LogManager (){

    }
    public static  LogManager getInstance(){
        if(instance==null){
            synchronized(LogManager.class){
                instance=new LogManager ();
            }
        }
        return instance;
    }
    public void createFile(String fileName){
//      getExternalFilesDir()方法可以获取到   SDCard/Android/data/youPackageName/files/ 目录，一般放一些长时间保存的数据
//      getFilesDir() 该方法返回/data/data/youPackageName/files的File对象。
        File file = new File(Application.getApplication().getExternalFilesDir(null),fileName);
        if (!file.exists()) {
            boolean result; // 文件是否创建成功
            try {
                result = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (!result) {
                return;
            }
        }
    }

    public void writeLog(String log,String fileName) {
        fileName = addSuffix(fileName);
        log = log + " \n";
        FileOutputStream outputStream;
        createFile(fileName);
        try {
            outputStream = new FileOutputStream(Application.getApplication().getExternalFilesDir(null).getPath() + "/" + fileName,true ); //Application.getApplication().openFileOutput(fileName, Application.getApplication().MODE_APPEND);
            outputStream.write(log.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String addSuffix(String fileName){
        if (!fileName.endsWith(".log")){
            fileName = fileName + ".log";
        }
        return fileName;
    }


}
