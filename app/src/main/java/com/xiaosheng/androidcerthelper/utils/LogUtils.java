package com.xiaosheng.androidcerthelper.utils;

import android.util.Log;

import com.xiaosheng.androidcerthelper.constants.Constants;


public class LogUtils {
    public static void log(String msg){
        Log.d(Constants.TAG,msg);
    }
    public static void logi(String msg){
        Log.i(Constants.TAG,msg);
    }
    public static void loge(String msg){
        Log.e(Constants.TAG,msg);
    }
}
