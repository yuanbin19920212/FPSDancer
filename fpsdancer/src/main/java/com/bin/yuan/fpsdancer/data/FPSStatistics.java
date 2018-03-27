package com.bin.yuan.fpsdancer.data;

import android.util.Log;

import com.bin.yuan.fpsdancer.ActivityInfo;
import com.bin.yuan.fpsdancer.Calculation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by yuanbin on 2018/3/27.
 */

public class FPSStatistics implements IStatistics {

    private Gson gson = new Gson();

    /***
     *
     * @param activityInfo
     * @param metric
     * @param fsp
     */
    @Override
    public void collectData(ActivityInfo activityInfo, Calculation.Metric metric, Long fsp) {
        if (activityInfo == null)return;

        Log.e("developer",gson.toJson(activityInfo));
        Log.e("Metric-------------",metric.name());
        Log.e("fps-------------",fsp+"");
    }
}
