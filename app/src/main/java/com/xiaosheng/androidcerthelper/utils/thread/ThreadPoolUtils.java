package com.xiaosheng.androidcerthelper.utils.thread;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author xiaosheng
 * @Date 2022/11/3 下午1:03
 * @Desc 单例的线程池工具类
 */

public class ThreadPoolUtils {

    /**
     * 系统可用计算资源
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 空闲线程存活时间
     */
    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     * 工作队列
     */
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE = new LinkedBlockingQueue<>(128);

    /**
     * 工厂模式
     */
    private static final MyThreadFactory MY_THREAD_FACTORY = new MyThreadFactory();

    /**
     * 饱和策略
     */
    private static final ThreadRejectedExecutionHandler THREAD_REJECTED_EXECUTION_HANDLER = new ThreadRejectedExecutionHandler.CallerRunsPolicy();

    /**
     * 线程池对象
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    /**
     * 声明式定义线程池工具类对象静态变量，在所有线程中同步
     */
    private static volatile ThreadPoolUtils threadPoolUtils = null;


    /**
     * 初始化线程池静态代码块
     */
    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                //核心线程数
                CORE_POOL_SIZE,
                //最大线程数
                MAXIMUM_POOL_SIZE,
                //空闲线程执行时间
                KEEP_ALIVE_SECONDS,
                //空闲线程执行时间单位
                TimeUnit.SECONDS,
                //工作队列（或阻塞队列）
                POOL_WORK_QUEUE,
                //工厂模式
                MY_THREAD_FACTORY,
                //饱和策略
                THREAD_REJECTED_EXECUTION_HANDLER
                );
    }


    /**
     * 线程池工具类空参构造方法
     */
    private ThreadPoolUtils() {}

    /**
     * 获取线程池工具类实例
     * @return
     */
    public static ThreadPoolUtils getNewInstance(){
        if (threadPoolUtils == null) {
            synchronized (ThreadPoolUtils.class) {
                if (threadPoolUtils == null) {
                    threadPoolUtils = new ThreadPoolUtils();
                }
            }
        }
        return threadPoolUtils;
    }


    /**
     * 执行线程任务
     * @param runnable 任务线程
     */
    public void executor(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    /**
     * 获取线程池状态
     * @return 返回线程池状态
     */
    public boolean isShutDown(){
        return THREAD_POOL_EXECUTOR.isShutdown();
    }

    /**
     * 停止正在执行的线程任务
     * @return 返回等待执行的任务列表
     */
    public List<Runnable> shutDownNow(){
        return THREAD_POOL_EXECUTOR.shutdownNow();
    }

    /**
     * 关闭线程池
     */
    public void shutdown(){
        THREAD_POOL_EXECUTOR.shutdown();
    }


    /**
     * 关闭线程池后判断所有任务是否都已完成
     * @return
     */
    public boolean isTerminated(){
        return THREAD_POOL_EXECUTOR.isTerminated();
    }
}
