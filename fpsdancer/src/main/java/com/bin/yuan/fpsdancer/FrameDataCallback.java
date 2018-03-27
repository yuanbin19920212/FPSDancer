package com.bin.yuan.fpsdancer;

/**
 * Created by yuanbin on 11/12/15.
 */
public interface FrameDataCallback {
    void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames);
}
