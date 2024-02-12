package com.xiaosheng.androidcerthelper.utils.thread;

import java.util.concurrent.ThreadFactory;

/**
 * @Author xiaosheng
 * @Date 2022/11/3 下午12:38
 * @Desc 创建线程池的工厂对象
 */
public class MyThreadFactory implements ThreadFactory {

    /**
     * 该方法用来创建线程
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread newThread = new Thread(r);
        return newThread;
    }
}
