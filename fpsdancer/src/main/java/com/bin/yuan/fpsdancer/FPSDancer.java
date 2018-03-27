package com.bin.yuan.fpsdancer;

import android.app.Application;

/**
 * Created by yuanbin on 2018/3/26.
 */

public class FPSDancer {
    public static DancerBuilder create(Application application){
        return new DancerBuilder(application);
    }
}
