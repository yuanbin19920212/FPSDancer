package com.bin.yuan.fpsdancer.data;

import android.util.Log;

import com.bin.yuan.fpsdancer.ActivityInfo;
import com.bin.yuan.fpsdancer.Calculation;
import com.bin.yuan.fpsdancer.FileUtil;
import com.bin.yuan.fpsdancer.PoolExecutors;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbin on 2018/3/27.
 */

public class FPSStatistics implements IStatistics {

    private Gson gson = new Gson();

    private List<JsonObject> samples = new ArrayList<>();

    private int simpleSize = 18;

    private String savePath = "fps";


    public void setSimpleSize(int simpleSize) {
        this.simpleSize = simpleSize;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /***
     *
     * @param activityInfo
     * @param metric
     * @param fsp
     */
    @Override
    public void collectData(ActivityInfo activityInfo, Calculation.Metric metric, Long fsp) {
        if (activityInfo == null)return;
        if (metric == Calculation.Metric.GOOD) return;
        Log.e("developer",gson.toJson(activityInfo));
        Log.e("Metric-------------",metric.name());
        Log.e("fps-------------",fsp+"");
        JsonObject jsonObject = makeSample(activityInfo,metric,fsp);
        samples.add(jsonObject);
        if (samples.size() >= simpleSize){
           saveSamples();
        }
    }

    @Override
    public void saveSamples() {
        final String sampleStr = gson.toJson(samples);
        final int size = samples.size();
        samples.clear();
        PoolExecutors.call(new Runnable() {
            @Override
            public void run() {
                FileUtil.saveFpsTxt(sampleStr,size,savePath);
            }
        });
    }


    /***
     *
     * @param activityInfo
     * @param metric
     * @param fsp
     * @return
     */
    private JsonObject makeSample(ActivityInfo activityInfo, Calculation.Metric metric, Long fsp){
        JsonObject jsonObject;
        if (activityInfo.isUseFormatStr()){
            jsonObject = gson.fromJson(activityInfo.getFormatStr(),JsonObject.class);
            jsonObject.addProperty("id",activityInfo.getId());
            jsonObject.addProperty("name",activityInfo.getName());
            jsonObject.addProperty("className",activityInfo.getClassName());
            jsonObject.addProperty("classSimpleName",activityInfo.getClassSimpleName());
        }else {
            jsonObject = new JsonObject();
            jsonObject.addProperty("id",activityInfo.getId());
            jsonObject.addProperty("name",activityInfo.getName());
            jsonObject.addProperty("className",activityInfo.getClassName());
            jsonObject.addProperty("classSimpleName",activityInfo.getClassSimpleName());
            jsonObject.addProperty("departmentId",activityInfo.getDepartmentId());
            jsonObject.addProperty("departmentName",activityInfo.getDepartmentName());
            jsonObject.addProperty("description",activityInfo.getDescription());
        }

        jsonObject.addProperty("metric",metric.name());
        jsonObject.addProperty("fsp",fsp);
        jsonObject.addProperty("time",System.currentTimeMillis());

        return jsonObject;
    }
}
