package com.bin.yuan.fpsdancer;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Choreographer;

import com.bin.yuan.fpsdancer.data.Addition;
import com.bin.yuan.fpsdancer.data.IStatistics;
import com.bin.yuan.fpsdancer.ui.CoachAdapter;
import com.bin.yuan.fpsdancer.ui.CoachWindow;
import com.bin.yuan.fpsdancer.ui.ICoach;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by yuanbin on 2018/3/26.
 */

public class DancerBuilder {

    private FPSConfig mFpsConfig;

    private ICoach mCoach;

    private Foreground mForegound;

    private FPSFrameCallback fpsFrameCallback;

    private IStatistics mStatistics;

    private Application mApplication;

    private ActivityInfoManager mActivityInfoManager;

    private Addition mAddition;

    protected DancerBuilder(Application application){
        this.mApplication = application;
        mFpsConfig = new FPSConfig();
    }

    /***
     * 设置Coach
     * @param coach
     * @return
     */
    public DancerBuilder setCoach(boolean coach){
        if (coach) {
            this.mCoach = new CoachWindow(mFpsConfig);
        }
        return this;
    }


    public DancerBuilder setStatistics(IStatistics statistics){
        this.mStatistics = statistics;
        return this;
    }

    /***
     * 设置保存路径
     * @param str
     * @return
     */
    public DancerBuilder setSavePath(String str){
        if (mStatistics != null){
            mStatistics.setSavePath(str);
        }
        return this;
    }

    /***
     * 设置adapter
     * @param coachAdapter
     * @return
     */
    public DancerBuilder setCoachAdapter(CoachAdapter coachAdapter){
        if (coachAdapter == null || mCoach == null)return this;
        if (overlayPermRequest(mApplication)) {
            //once permission is granted then you must call show() again
            return this;
        }
        mCoach.setAdapter(coachAdapter);
        return this;
    }
    /***
     * 设置sample size
     * @param size
     * @return
     */
    public DancerBuilder setSampleSize(int size){
        if (mStatistics != null){
            mStatistics.setSimpleSize(size);
        }
        return this;
    }

    public DancerBuilder setSampleSize(Addition addition){
        if (mStatistics != null){
            mAddition = addition;
            mStatistics.setAddition(addition);
        }
        return this;
    }
    /***
     * 终止
     */
    public void terminate(){
        if (mStatistics != null){
            mStatistics.saveSamples();
        }
    }
    /***
     * build
     */
    public void build(){
        if (mApplication == null){
            throw new NullPointerException("application is null!");
        }
        createCoachIfNeed();
        createStatisticsIfNeed();
        fpsFrameCallback = new FPSFrameCallback(mFpsConfig);
        fpsFrameCallback.setSampleCallback(new FPSFrameCallback.SampleCallback() {
            @Override
            public void handleSamples(FPSConfig fpsConfig, List<Long> samples) {
                List<Integer> droppedSet = Calculation.getDroppedSet(fpsConfig, samples);
                AbstractMap.SimpleEntry<Calculation.Metric, Long> answer = Calculation.calculateMetric(fpsConfig, samples, droppedSet);
                /***
                 * 更新coach数据
                 */
                if (mCoach != null) mCoach.updateData(answer.getKey(),answer.getValue());

                /***
                 * 收集fps数据
                 */

                if(mStatistics != null && mActivityInfoManager != null){
                    ActivityInfo activityInfo = mActivityInfoManager.getCurrentActivityInfo();
                    mStatistics.collectData(activityInfo,answer.getKey(),answer.getValue());
                }
            }
        });
        Choreographer.getInstance().postFrameCallback(fpsFrameCallback);
    }

    /***
     *
     */
    private void createStatisticsIfNeed(){
        if (mStatistics == null)return;
        mApplication.registerActivityLifecycleCallbacks(mActivityInfoManager = new ActivityInfoManager());
        mActivityInfoManager.setAppTerminateCallback(new ActivityInfoManager.AppTerminateCallback() {
            @Override
            public void terminate() {
                mStatistics.saveSamples();
            }
        });
    }

    /***
     * 创建Coach
     */
    private void createCoachIfNeed(){
        //创建Foreground
        if (mCoach == null)return;
        mForegound = Foreground.init(mApplication);
        mForegound.addListener(new Foreground.Listener() {
            @Override
            public void onBecameForeground() {
                if (mCoach != null)
                mCoach.show();
            }

            @Override
            public void onBecameBackground() {
                if (mCoach != null)
                mCoach.hide(false);
            }
        });
    }

    /**
     * request overlay permission when api >= 23
     * @param context
     * @return
     */
    private boolean overlayPermRequest(Context context) {
        boolean permNeeded = false;
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(context))
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                permNeeded = true;
            }
        }
        return permNeeded;
    }
}
