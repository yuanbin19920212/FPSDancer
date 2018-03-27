package com.bin.yuan.fpsdancer.ui;

import com.bin.yuan.fpsdancer.Calculation;

/**
 * Created by yuanbin on 2018/3/26.
 */

public interface ICoach {

    /***
     *
     * @param metric
     * @param fsp 采样集合
     */
     void updateData(Calculation.Metric metric, Long fsp);

     void destroy();

     void show();

     void hide ();
}
