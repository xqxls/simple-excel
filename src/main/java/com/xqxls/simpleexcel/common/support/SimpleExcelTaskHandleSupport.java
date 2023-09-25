package com.xqxls.simpleexcel.common.support;

import com.alibaba.fastjson.JSONArray;
import com.xqxls.simpleexcel.common.ErrorMessage;
import com.xqxls.simpleexcel.common.base.ctx.SimpleExcelBaseChainContext;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import com.xqxls.simpleexcel.persist.dao.SimpleExcelTaskMapper;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @USER: xqxls
 * @DATE: 2023/4/26
 * 提供部分任务调度的能力
 */
public abstract class SimpleExcelTaskHandleSupport implements SimpleExcelLifeCycle {


    protected SimpleExcelBaseChainContext simpleExcelBaseChainContext;


    /**
     * 当前任务状态
     */
    protected SimpleExcelTask curTask;


    /**
     * 任务mapper类
     */
    private SimpleExcelTaskMapper taskMapper;


    /**
     * 落盘逻辑
     */
    private Runnable fallingDb;


    public SimpleExcelTaskHandleSupport(SimpleExcelBaseChainContext context, SimpleExcelTaskCalculateSupport taskCalculateSupport, SimpleExcelTaskMapper taskMapper) {
        this.simpleExcelBaseChainContext = context;
        this.curTask = context.getCurTask();
        this.taskMapper = taskMapper;
        if (curTask != null) {
            curTask.setToastClose(null);
            this.fallingDb = () -> {
                taskCalculateSupport.calculateRemainingCompletionTime();
                //倒序插入
                curTask.setFailedMessage(JSONArray.toJSONString(curTask
                        .getInnerFailedMessageList()
                        .stream()
                        .sorted(Comparator.comparing(ErrorMessage::getRowIndex).reversed())
                        .collect(Collectors.toList()))
                );
                curTask.setUpdateTime(LocalDateTime.now());
                taskMapper.updateByPrimaryKeySelective(curTask);
            };

        }

    }


    @Override
    public void init() {
        //TODO 开始导入了，需要知道入口的全路径 加日志

        init0();

        if (curTask != null) {

            //1、更新状态为运行状态
            curTask.setStatus(SimpleExcelConstant.Task.Status.RUNNING);

            //2、task信息定时落库
            ScheduledFuture taskScheduledFuture = TaskExecutorSupport.scheduledExecutor.scheduleAtFixedRate(fallingDb, 0, SimpleExcelConstant.TASK_STORAGE_INTERVAL, TimeUnit.SECONDS);

            simpleExcelBaseChainContext.setTaskScheduledFuture(taskScheduledFuture);
        }

    }

    /**
     * 子类实现
     */
    protected abstract void init0();


    @Override
    public void onComplete() {
        if (curTask != null) {
            curTask.setStatus(SimpleExcelConstant.Task.Status.COMPLETE);
            //这里处理完成，预估数量会变成精准数量
            curTask.setEstimateCount(curTask.getTotalCount());
        }
    }


    @Override
    public void onFinally() {


        try {
            //设置默认租户
            onFinally0();

        } catch (Exception e) {

            throw e;

        } finally {

        }

        //1定时任务停止
        if (simpleExcelBaseChainContext.getTaskScheduledFuture() != null) {
            simpleExcelBaseChainContext.getTaskScheduledFuture().cancel(true);
        }


        //2、任务状态处理
        if (curTask != null) {
            //3.1、如果存在错误日志，说明失败了
            if (curTask.getFailedFileId() != null) {
                curTask.setStatus(SimpleExcelConstant.Task.Status.FAIL);
            }
            //3.2、如果是待写入状态，到这里就已经成功了
            if (SimpleExcelConstant.Task.Status.COMPLETE.equals(curTask.getStatus())) {
                curTask.setStatus(SimpleExcelConstant.Task.Status.SUCCESS);
            }

            //3、最后落库
            if (fallingDb != null) {
                curTask.setEndTime(LocalDateTime.now());
                fallingDb.run();
            }
        }

    }


    /**
     * 子类实现
     */
    protected abstract void onFinally0();
}
