package com.bin.yuan.fpsdancer;

import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanbin on 2018/3/26.
 */

public class FPSFrameCallback implements Choreographer.FrameCallback {
    private FPSConfig fpsConfig;
    private List<Long> dataSet; //holds the frame times of the sample set
    private boolean enabled = true;
    private long startSampleTimeInNs = 0;

    private SampleCallback sampleCallback;

    public FPSFrameCallback(FPSConfig fpsConfig) {
        this.fpsConfig = fpsConfig;
        dataSet = new ArrayList<>();
    }

    public void setSampleCallback(SampleCallback sampleCallback) {
        this.sampleCallback = sampleCallback;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        //if not enabled then we bail out now and don't register the callback
        if (!enabled) {
            destroy();
            return;
        }

        //initial case
        if (startSampleTimeInNs == 0) {
            startSampleTimeInNs = frameTimeNanos;
        }
        // only invoked for callbacks....
        else if (fpsConfig.frameDataCallback != null) {
            long start = dataSet.get(dataSet.size() - 1);
            int droppedCount = Calculation.droppedCount(start, frameTimeNanos, fpsConfig.deviceRefreshRateInMs);
            fpsConfig.frameDataCallback.doFrame(start, frameTimeNanos, droppedCount);
        }

        //采样完成
        if (isFinishedWithSample(frameTimeNanos)) {
            collectSampleAndSend(frameTimeNanos);
        }

        dataSet.add(frameTimeNanos);

        Choreographer.getInstance().postFrameCallback(this);
    }

    private void collectSampleAndSend(long frameTimeNanos) {
        List<Long> dataSetCopy = new ArrayList<>();
        dataSetCopy.addAll(dataSet);

        if (sampleCallback != null)sampleCallback.handleSamples(fpsConfig,dataSetCopy);

        dataSet.clear();

        startSampleTimeInNs = frameTimeNanos;
    }

    /**
     * returns true when sample length is exceed
     *
     * @param frameTimeNanos current frame time in NS
     * @return
     */
    private boolean isFinishedWithSample(long frameTimeNanos) {
        return frameTimeNanos - startSampleTimeInNs > fpsConfig.getSampleTimeInNs();
    }

    private void destroy() {
        dataSet.clear();
        fpsConfig = null;
    }


    /***
     * 处理样本接口
     */
    public interface SampleCallback{
        void handleSamples(FPSConfig fpsConfig,List<Long> samples);
    }
}
