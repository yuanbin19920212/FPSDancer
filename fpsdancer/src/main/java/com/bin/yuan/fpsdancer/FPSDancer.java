package com.bin.yuan.fpsdancer;

import android.app.Application;

/**
 * Created by yuanbin on 2018/3/26.
 */

public class FPSDancer {

    private static DancerBuilder dancerBuilder;

    public static DancerBuilder create(Application application) {
        if (dancerBuilder == null) {
            dancerBuilder = new DancerBuilder(application);
        }
        return dancerBuilder;
    }

    public static DancerBuilder getInstance() {
        if (dancerBuilder == null){
            throw new NullPointerException("You should first create dancerBuilder");
        }
        return dancerBuilder;
    }

    public static void terminateFPSDancer() {
        if (dancerBuilder != null) {
            dancerBuilder.terminate();
        }
    }
}
