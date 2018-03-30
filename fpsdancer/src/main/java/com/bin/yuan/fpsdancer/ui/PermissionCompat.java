package com.bin.yuan.fpsdancer.ui;

import android.os.Build;
import android.view.WindowManager;

public final class PermissionCompat {

    private PermissionCompat() {
    }

    public static int getFlag() {
        int permissionFlag;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            permissionFlag = WindowManager.LayoutParams.TYPE_PHONE;
        }
        return permissionFlag;
    }
}
