package com.yip.yipjnidemo;

import android.util.Log;

/**
 * Created by Administrator on 2016/2/22.
 */
public class JniText {
    static {
        System.loadLibrary("jnitext");   //defaultConfig.ndk.moduleName
    }
    public native int getCLangString();
}
