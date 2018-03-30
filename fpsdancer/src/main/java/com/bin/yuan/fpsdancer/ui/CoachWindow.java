package com.bin.yuan.fpsdancer.ui;

import android.animation.Animator;
import android.app.Service;
import android.graphics.PixelFormat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bin.yuan.fpsdancer.Calculation;
import com.bin.yuan.fpsdancer.FPSConfig;

/**
 * Created by yuanbin on 2018/3/30.
 */

public class CoachWindow implements ICoach{

    private FPSConfig fpsConfig;
    private View meterView;
    private  WindowManager windowManager;
    private int shortAnimationDuration = 200, longAnimationDuration = 700;

    private CoachAdapter coachAdapter;

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            hide(false);
            return super.onDoubleTap(e);
        }
    };

    /***
     *
     * @param config
     */
    public CoachWindow(FPSConfig config) {
        fpsConfig = config;
    }


    private void addViewToWindow(View view) {

        int permissionFlag = PermissionCompat.getFlag();

        WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                permissionFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        if (fpsConfig.xOrYSpecified) {
            paramsF.x = fpsConfig.startingXPosition;
            paramsF.y = fpsConfig.startingYPosition;
            paramsF.gravity = FPSConfig.DEFAULT_GRAVITY;
        } else if (fpsConfig.gravitySpecified) {
            paramsF.x = 0;
            paramsF.y = 0;
            paramsF.gravity = fpsConfig.startingGravity;
        } else {
            paramsF.gravity = FPSConfig.DEFAULT_GRAVITY;
            paramsF.x = fpsConfig.startingXPosition;
            paramsF.y = fpsConfig.startingYPosition;
        }

        windowManager.addView(view, paramsF);

        GestureDetector gestureDetector = new GestureDetector(view.getContext(), simpleOnGestureListener);

        view.setOnTouchListener(new DancerTouchListener(paramsF, windowManager, gestureDetector));

        view.setHapticFeedbackEnabled(false);

        show();
    }

    @Override
    public void updateData(Calculation.Metric metric, Long fsp) {
        if (coachAdapter != null){
            coachAdapter.updateData(meterView,metric,fsp);
        }
    }

    @Override
    public void destroy() {
        meterView.setOnTouchListener(null);
        hide(true);
    }

    @Override
    public void show() {
        meterView.setAlpha(0f);
        meterView.setVisibility(View.VISIBLE);
        meterView.animate()
                .alpha(1f)
                .setDuration(longAnimationDuration)
                .setListener(null);
    }

    @Override
    public void hide(final boolean remove) {
        meterView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        meterView.setVisibility(View.GONE);
                        if (remove) {
                            windowManager.removeView(meterView);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }

    @Override
    public void setAdapter(CoachAdapter coachAdapter) {
        this.coachAdapter = coachAdapter;
        meterView = coachAdapter.getView();
        coachAdapter.updateData(meterView, Calculation.Metric.GOOD,60L);
        windowManager = (WindowManager) meterView.getContext().getSystemService(Service.WINDOW_SERVICE);
        addViewToWindow(meterView);
    }
}
