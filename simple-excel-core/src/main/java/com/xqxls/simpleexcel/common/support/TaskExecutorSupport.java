package com.xqxls.simpleexcel.common.support;

import com.xqxls.exception.ApiException;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
public class TaskExecutorSupport {

    /**
     * 导入线程池
     */
    public static final ThreadPoolExecutor importExecutor;

    /**
     * 导出线程池
     */
    public static final ThreadPoolExecutor exportExecutor;

    /**
     * 定时线程池
     */
    public static final ScheduledExecutorService scheduledExecutor;


    static {
        importExecutor = new ThreadPoolExecutor(
                //默认线程池数量
                Runtime.getRuntime().availableProcessors() * 2,
                //最大线程池数量 cpu核心数 * 3
                Runtime.getRuntime().availableProcessors() * 3,
                //非核心线程池的保持时间 10s
                10, TimeUnit.SECONDS,
                //阻塞队列数量 最多5条数据
                new LinkedBlockingQueue<>(5),
                //定义线程工场
                new BasicThreadFactory.Builder().namingPattern("simpleExcel-im-pool-%d").daemon(true).build(),
                // 当超过了阻塞数量达到了线程边界和队列容量 丢弃并报错
                new SimpleExcelAbortPolicy());

        //考虑到导入场景触发概率比较低，核心线程数没有必要hold住
        importExecutor.allowCoreThreadTimeOut(true);


        exportExecutor = new ThreadPoolExecutor(
                //默认线程池数量
                Runtime.getRuntime().availableProcessors() * 2,
                //最大线程池数量 cpu核心数 * 3
                Runtime.getRuntime().availableProcessors() * 3,
                //非核心线程池的保持时间 10s
                10, TimeUnit.SECONDS,
                //阻塞队列数量 最多5条数据
                new LinkedBlockingQueue<>(5),
                //定义线程工场
                new BasicThreadFactory.Builder().namingPattern("simpleExcel-ep-pool-%d").daemon(true).build(),
                // 当超过了阻塞数量达到了线程边界和队列容量 丢弃并报错
                new SimpleExcelAbortPolicy());

        //考虑到导入场景触发概率比较低，核心线程数没有必要hold住
        exportExecutor.allowCoreThreadTimeOut(true);


        scheduledExecutor = Executors.newSingleThreadScheduledExecutor(
                //定义线程工场
                new BasicThreadFactory.Builder().namingPattern("simpleExcel-schedule-pool-%d").daemon(true).build());
    }


    public static class SimpleExcelAbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an {@code AbortPolicy}.
         */
        public SimpleExcelAbortPolicy() {
        }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            //todo 日志打印
            throw new ApiException("当前存在较多任务正在执行，系统繁忙，请稍后重试！");
        }
    }


}
