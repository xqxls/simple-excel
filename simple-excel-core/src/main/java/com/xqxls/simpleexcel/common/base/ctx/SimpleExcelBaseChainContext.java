package com.xqxls.simpleexcel.common.base.ctx;

import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;


/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
@Data
@SuperBuilder
public class SimpleExcelBaseChainContext {

    /**
     * 当前任务进度数据
     */
    private SimpleExcelTask curTask;

    /**
     * 唯一标识
     * 默认是taskId
     * <p>
     * 如果不存在task，则用uuid
     */
    private String trackId;


    /**
     * 导出导入对应的实体类
     */
    private Class<?> model;


    /**
     * 定时落库Future
     * 保留用于中止该定时任务
     */
    private ScheduledFuture taskScheduledFuture;


    /**
     * 是否是动态头模式
     */
    @Builder.Default
    private Boolean dynamicHead = false;


    /**
     * 任务开始时间
     */
    @Builder.Default
    private LocalDateTime startTime = LocalDateTime.now();


    /**
     * 程序异常
     */
    private Exception lastProgramException;

}
