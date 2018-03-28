package com.bin.yuan.fpsdancer;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by yuanbin on 2018/3/28.
 */

public class FileUtil {

    public static void saveFpsTxt(String data,String path){
        File file = new File(Environment.getExternalStorageDirectory()+"/"+path);
        if (!file.exists()){
            file.mkdirs();
        }
        File fpsFile = new File(file.getAbsolutePath()+"/"+System.currentTimeMillis()+".txt");
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(fpsFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream,"utf-8");
            outputStreamWriter.write(data,0,data.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStreamWriter != null)
                outputStreamWriter.close();
                if (fileOutputStream != null)
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
