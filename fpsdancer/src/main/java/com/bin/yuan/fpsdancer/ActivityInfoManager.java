package com.bin.yuan.fpsdancer;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.bin.yuan.fpsdancer.annotation.Developer;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Created by yuanbin on 2018/3/27.
 */

public class ActivityInfoManager implements Application.ActivityLifecycleCallbacks {

    private HashMap<Class,ActivityInfo> mActivityInfoCaches = new HashMap<>();


    private ActivityInfo mCurrentActivityInfo;


    public ActivityInfo getCurrentActivityInfo() {
        return mCurrentActivityInfo;
    }

    private ActivityInfo getActivityInfo(Developer developer){
        if (developer == null){
            throw new NullPointerException("developer can not be null!");
        }
        ActivityInfo activityInfo = new ActivityInfo();

        activityInfo.id = developer.id();
        activityInfo.name = developer.name();
        activityInfo.departmentId = developer.departmentId();
        activityInfo.departmentName = developer.departmentName();
        activityInfo.description = developer.description();
        activityInfo.useFormatStr = developer.useFormatStr();
        activityInfo.formatStr = developer.formatStr();
        return activityInfo;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Class cl = activity.getClass();
        Annotation annotation = cl.getAnnotation(Developer.class);
        if (annotation != null&&annotation.annotationType().isAssignableFrom(Developer.class)) {
            if (mActivityInfoCaches.containsKey(cl)){
                //修改当前页面开发者信息
                mCurrentActivityInfo = mActivityInfoCaches.get(cl);
            }else {
                Developer developer = (Developer) annotation;
                ActivityInfo activityInfo = getActivityInfo(developer);
                activityInfo.className = cl.getName();
                activityInfo.classSimpleName = cl.getSimpleName();
                mCurrentActivityInfo = activityInfo;
                mActivityInfoCaches.put(cl,mCurrentActivityInfo);
            }
        }else {
            //设置默认的developer
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mCurrentActivityInfo = null;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
