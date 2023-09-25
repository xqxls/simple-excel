package com.xqxls.simpleexcel.exporter.handler;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.xqxls.simpleexcel.SimpleExcelContext;
import com.xqxls.simpleexcel.common.chain.ChainProcessResult;
import com.xqxls.simpleexcel.common.chain.GenericHandler;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import com.xqxls.simpleexcel.common.support.SimpleExcelTaskCalculateSupport;
import com.xqxls.simpleexcel.common.util.UserUtil;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportDataSupport;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportSupport;
import com.xqxls.simpleexcel.feign.FileInfoControllerFeign;
import com.xqxls.simpleexcel.persist.dao.SimpleExcelTaskMapper;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import com.xqxls.simpleexcel.persist.repository.SimpleExcelTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Component
public class SimpleExcelExportPrepareHandler implements GenericHandler<SimpleExcelExportChainContext> {


    @Resource
    private SimpleExcelTaskMapper taskMapper;


    @Autowired
    private HttpServletResponse response;


    @Resource
    private FileInfoControllerFeign fileInfoFeign;

    @Resource
    private SimpleExcelTaskRepository simpleExcelTaskRepository;


    @Override
    public ChainProcessResult doHandler(SimpleExcelExportChainContext context) {

        SimpleExcelExportReq simpleExcelExportReq = context.getSimpleExcelExportReq();


        /**
         * 到这一步，可以确定用户传参无错误
         * 1、初始化任务
         */
        if (simpleExcelExportReq.getAccessTaskCenter()) {
            SimpleExcelTask task = initTask(context);
            context.setCurTask(task);
        }
        //2、设置自定义参数
        SimpleExcelContext.getContext().set(simpleExcelExportReq.getExtraParam());

        //3、设置响应对象处理
        context.setResponse(response);

        //4、设置唯一追踪id
        context.setTrackId(
                Optional.ofNullable(context.getCurTask())
                        .map(SimpleExcelTask::getId)
                        .map(NumberUtil::toStr)
                        .orElse(UUID.fastUUID().toString()));

        //5、初始化导出数据处理
        SimpleExcelExportDataSupport exportDataSupport = SimpleExcelExportDataSupport.chooseDataSupport(context);
        context.setExportDataSupport(exportDataSupport);

        //6、初始化任务计算处理
        SimpleExcelTaskCalculateSupport taskCalculateSupport = new SimpleExcelTaskCalculateSupport(context);
        context.setTaskCalculateSupport(taskCalculateSupport);

        //7、初始化导出业务处理
        SimpleExcelExportSupport exportSupport = new SimpleExcelExportSupport(context, taskMapper, fileInfoFeign);
        context.setExportSupport(exportSupport);


        return ChainProcessResult.CONTINUE;
    }


    private SimpleExcelTask initTask(SimpleExcelExportChainContext context) {

        SimpleExcelExportReq simpleExcelExportReq = context.getSimpleExcelExportReq();
        //1、构建任务实体
        SimpleExcelTask task = SimpleExcelTask.of();
        task.setPageId(simpleExcelExportReq.getPageId());
        task.setType(SimpleExcelConstant.Task.Type.EXPORTER);
        task.setStatus(SimpleExcelConstant.Task.Status.INIT);
        task.setStartTime(context.getStartTime());
        task.setFileName(simpleExcelExportReq.getFilename());
        task.setParamSnapshot(JSON.toJSONString(simpleExcelExportReq));

        //填充用户信息
        initBaseParamByTask(task);

        //初始化任务
        simpleExcelTaskRepository.insert(task);

        return task;
    }

    private void initBaseParamByTask(SimpleExcelTask task) {
        task.setDefaultVal(UserUtil.getCurrentUser());
    }

}
