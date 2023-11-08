package com.xqxls.simpleexcel.exporter.handler;

import com.xqxls.simpleexcel.SimpleExcelContext;
import com.xqxls.simpleexcel.common.base.SimpleExcel;
import com.xqxls.simpleexcel.common.chain.ChainProcessResult;
import com.xqxls.simpleexcel.common.chain.GenericHandler;
import com.xqxls.simpleexcel.common.support.TaskExecutorSupport;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.res.SimpleExcelExportResult;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/23
 */
@Component
public class SimpleExcelExportHandler implements GenericHandler<SimpleExcelExportChainContext> {

    @Override
    public ChainProcessResult doHandler(SimpleExcelExportChainContext simpleExcelExportChainContext) {

        SimpleExcelExportSupport simpleExcelExportSupport = simpleExcelExportChainContext.getExportSupport();

        SimpleExcelExportReq simpleExcelExportReq = simpleExcelExportChainContext.getSimpleExcelExportReq();

        BiFunction<Long, Integer, SimpleExcelExportResult> loopExportFunction = (startId, batchSize) -> {

            //导出前，组件前置处理
            simpleExcelExportSupport.singleHandleBefore();

            //获取导出数据
            SimpleExcelExportResult simpleExcelExportResult = fetchExportData(simpleExcelExportChainContext, startId, batchSize);

            //导出后，组件后置处理
            simpleExcelExportSupport.singleHandleAfter(simpleExcelExportResult.getData());

            return simpleExcelExportResult;
        };

        SimpleExcelContext context = SimpleExcelContext.getContext();

        Runnable runnable = () -> {
            //这里可能是异步调用，当前线程保留副本
            SimpleExcelContext.setContext(context);

            try {
                //导出准备开始，组件处理
                simpleExcelExportSupport.init();

                //导出准备开始，用户处理器生命周期【初始化】调用
                Optional.ofNullable(simpleExcelExportChainContext.getHandle()).ifPresent(SimpleExcel::init);

                //真正的导出入口
                doExport(simpleExcelExportReq, loopExportFunction);

                //导出完成，用户处理器生命周期【完成】调用
                Optional.ofNullable(simpleExcelExportChainContext.getHandle()).ifPresent(SimpleExcel::complete);

                //导出完成，组件处理
                simpleExcelExportSupport.onComplete();
            } catch (Exception e) {

                //导出异常，组件处理
                simpleExcelExportSupport.onException(e);
            } finally {

                //导出结束，组件处理
                simpleExcelExportSupport.onFinally();
            }
        };

        //运行任务
        if (simpleExcelExportReq.getAccessTaskCenter()) {
            TaskExecutorSupport.exportExecutor.execute(runnable);
        } else {
            runnable.run();
        }

        return ChainProcessResult.CONTINUE;
    }


    /**
     * 导出入口
     *
     * @param simpleExcelExportReq
     * @param loopExportFunction
     */
    private void doExport(SimpleExcelExportReq simpleExcelExportReq, BiFunction<Long, Integer, SimpleExcelExportResult> loopExportFunction) {

        //起始id
        Long startId = 0L;

        //游标定义
        SimpleExcelExportResult cursor;


        do {

            cursor = loopExportFunction.apply(startId, simpleExcelExportReq.getBatchSize());

            startId = cursor.getNextStartId();

        } while (cursor.hasNext());

    }


    /**
     * 获取数据
     *
     * @param simpleExcelExportChainContext
     * @param startId
     * @param batchSize
     * @return
     */
    private SimpleExcelExportResult fetchExportData(SimpleExcelExportChainContext simpleExcelExportChainContext, Long startId, Integer batchSize) {

        SimpleExcelExportReq simpleExcelExportReq = simpleExcelExportChainContext.getSimpleExcelExportReq();

        //如果传入了数据提供者，则不需要去handle获取数据
        if (simpleExcelExportReq.getFetchData() != null) {
            List list = simpleExcelExportReq.getFetchData().get();
            //只运行一次
            return SimpleExcelExportResult.success(list, null);
        }


        return simpleExcelExportChainContext.getHandle().exportData(startId, batchSize);
    }


}
