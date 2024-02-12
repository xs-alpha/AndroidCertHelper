package com.xiaosheng.androidcerthelper.utils;

import android.util.Log;


public class LogUtils {
    public static void log(String msg){
        Log.d(UserConstants.TAG,msg);
    }
    public static void logi(String msg){
        Log.i(UserConstants.TAG,msg);
    }
    public static void loge(String msg){
        Log.e(UserConstants.TAG,msg);
    }
}
