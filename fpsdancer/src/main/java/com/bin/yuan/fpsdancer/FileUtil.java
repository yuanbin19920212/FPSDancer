package com.bin.yuan.fpsdancer;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * Created by yuanbin on 2018/3/28.
 */

public class FileUtil {

    /**
     * 8
     * 一个日志文件最大数据条数
     */
    private static final int txtSize = 10000;


    private static final String filePrefix = "fpsLog";

    private static final String splitStr = "#";

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    private static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    public synchronized static void saveFpsTxt(String data, int size, String path) {
        if (getFreeDiskSpace() < 10 * 1024) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/" + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.contains(filePrefix)) {
                    String[] strs = name.split("\\.");
                    if (strs.length != 2) return false;
                    strs = strs[0].split(splitStr);
                    String sizeStr = strs[1];
                    int size = Integer.parseInt(sizeStr);
                    if (size <= txtSize) {
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });
        if (files == null || files.length <= 0) {
            createNewFpsTxt(data, size, file);
        } else {
            File f = files[0];
            appendFpsTxt(data, size, f);
        }
    }

    private static void createNewFpsTxt(String data, int size, File file) {
        File fpsFile = new File(file.getAbsolutePath() + "/" + filePrefix+System.currentTimeMillis()+splitStr+ size + ".txt");
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(fpsFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            outputStreamWriter.write(data, 0, data.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    private static void appendFpsTxt(String data, int size, File file) {
        String[] strs = file.getName().split("\\.");
        if (strs.length != 2) return ;
        strs = strs[0].split(splitStr);
        String sizeStr = strs[1];
        int preSize = Integer.parseInt(sizeStr);
        int currentSize = size + preSize;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            long length = raf.length();
            raf.seek(length - 1);
            raf.write(',');//去掉文件尾部的"]",然后添加","
            byte[] bytes = data.getBytes("utf-8");//去掉字符串前端的"["
            raf.write(bytes,1,bytes.length-1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("result",readFileByLines(file));
        file.renameTo(new File(file.getParent()+ "/" + filePrefix+System.currentTimeMillis()+splitStr+ currentSize + ".txt"));
    }

    /**
      * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(File file) {
        BufferedReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                stringBuffer.append(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return stringBuffer.toString();
    }
}
