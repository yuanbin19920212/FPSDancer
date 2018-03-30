package com.bin.yuan.fpsdancer.data;

import com.bin.yuan.fpsdancer.ActivityInfo;
import com.bin.yuan.fpsdancer.Calculation;

/**
 * Created by yuanbin on 2018/3/27.
 */

public interface IStatistics {

    void collectData(ActivityInfo activityInfo, Calculation.Metric metric, Long fsp);

    void saveSamples();

    void setSimpleSize(int simpleSize);

    void setSavePath(String savePath);

    void setAddition(Addition addition);
}
