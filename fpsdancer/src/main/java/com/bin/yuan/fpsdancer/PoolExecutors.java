package com.bin.yuan.fpsdancer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yuanbin on 2018/3/28.
 */

public class PoolExecutors {

    static Executor executor = Executors.newSingleThreadExecutor();

    public static void call(Runnable runnable){
        executor.execute(runnable);
    }
}
