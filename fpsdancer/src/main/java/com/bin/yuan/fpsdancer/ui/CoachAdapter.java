package com.bin.yuan.fpsdancer.ui;

import android.view.View;

import com.bin.yuan.fpsdancer.Calculation;

/**
 * Created by yuanbin on 2018/3/30.
 */

public abstract class CoachAdapter {

   public abstract View getView();
    /***
     * 更新数据
     * @param view
     * @param metric
     * @param fsp
     */
   public abstract void updateData(View view ,Calculation.Metric metric, Long fsp);
}
