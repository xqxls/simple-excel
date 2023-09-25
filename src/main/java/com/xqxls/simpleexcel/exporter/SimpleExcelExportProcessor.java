package com.xqxls.simpleexcel.exporter;

import com.xqxls.exception.ApiException;
import com.xqxls.simpleexcel.SimpleExcelContext;
import com.xqxls.simpleexcel.SimpleExcelUtils;
import com.xqxls.simpleexcel.exception.SimpleExcelException;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportChain;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportChainContext;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.res.SimpleExcelExportRes;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @USER: xqxls
 * @DATE: 2023/4/10
 */
@Component
@Slf4j
public class SimpleExcelExportProcessor {


    @Resource
    private SimpleExcelExportChain simpleExcelExportChain;


    @Autowired
    private HttpServletResponse response;


    /**
     * 导入
     */
    public SimpleExcelExportRes doExport(SimpleExcelExportReq req) {

        //1、构建上下文
        SimpleExcelExportChainContext context = SimpleExcelExportChainContext
                .builder()
                .simpleExcelExportReq(req)
                .build();

        //2、调用、异常处理
        try {

            simpleExcelExportChain.execute(context);
        } catch (Exception e) {

            log.error("simpleExcelExportChain execute error when doExport : req : " + req, e);

            //重置响应头
            resetResponse();

            if (e instanceof SimpleExcelException) {
                throw new ApiException(e.getMessage());
            }

            throw e;
        } finally {

            //3、清理资源
            SimpleExcelContext.removeContext();
        }

        //4、返回任务id

        return Optional.ofNullable(context)
                .map(SimpleExcelExportChainContext::getCurTask)
                .map(SimpleExcelTask::getId)
                .map(taskId -> {
                    SimpleExcelExportRes simpleExcelExportRes = new SimpleExcelExportRes();
                    simpleExcelExportRes.setTaskId(taskId);
                    return simpleExcelExportRes;
                })
                .orElseGet(SimpleExcelExportRes::new);
    }


    @PostConstruct
    public void init() {
        SimpleExcelUtils.registerSimpleExcelExportProcessor(this);
    }


    /**
     * 重制响应头
     */
    public void resetResponse() {
        if(!response.isCommitted()){
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
        }
    }
}
