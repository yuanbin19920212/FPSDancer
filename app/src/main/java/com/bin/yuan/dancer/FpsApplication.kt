package com.bin.yuan.dancer

import android.app.Application
import com.bin.yuan.fpsdancer.FPSDancer
import com.bin.yuan.fpsdancer.data.FPSStatistics

/**
 * Created by yuanbin on 2018/3/27.
 */
class FpsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FPSDancer.create(this)
                .setStatistics(FPSStatistics())
                .setSampleSize(5)
                .setSavePath("fpsdancer")
                .build()
    }
}