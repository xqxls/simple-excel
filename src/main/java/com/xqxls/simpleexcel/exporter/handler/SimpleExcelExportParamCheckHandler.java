package com.xqxls.simpleexcel.exporter.handler;

import com.xqxls.simpleexcel.SimpleExcelExport;
import com.xqxls.simpleexcel.common.base.handler.SimpleExcelParamCheckBaseHandler;
import com.xqxls.simpleexcel.common.chain.ChainProcessResult;
import com.xqxls.simpleexcel.exception.SimpleExcelException;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Component
@Slf4j
public class SimpleExcelExportParamCheckHandler extends SimpleExcelParamCheckBaseHandler<SimpleExcelExportChainContext> {

    @Override
    public ChainProcessResult doHandler(SimpleExcelExportChainContext context) {

        SimpleExcelExportReq simpleExcelExportReq = context.getSimpleExcelExportReq();

        //1、基本参数校验
        if (simpleExcelExportReq == null) {
            throw new SimpleExcelException("传入导入参数不能为空");
        }

        if (StringUtils.isBlank(simpleExcelExportReq.getFilename())) {
            throw new SimpleExcelException("导出的文件名不能为空");
        }


        if (simpleExcelExportReq.getFetchData() == null && simpleExcelExportReq.getHandle() == null) {
            throw new SimpleExcelException("传入的fetchDataSupplier和handle，必须保证有唯一种数据获取方式");
        }

        if (simpleExcelExportReq.getFetchData() != null && simpleExcelExportReq.getHandle() != null) {
            throw new SimpleExcelException("传入的fetchDataSupplier和handle，必须保证有唯一种数据获取方式");
        }

        if (simpleExcelExportReq.getAccessTaskCenter() && StringUtils.isBlank(simpleExcelExportReq.getPageId())) {
            throw new SimpleExcelException("传入的pageId不能为空");
        }

        if (simpleExcelExportReq.getHandle() == null && simpleExcelExportReq.getFetchDataClass() == null) {
            throw new SimpleExcelException("传入的fetchDataClass不能为空");
        }

        context.setModel(simpleExcelExportReq.getFetchDataClass());


        //2、动态头处理

        //3、处理器处理
        if (simpleExcelExportReq.getHandle() != null) {

            SimpleExcelExport handle = (SimpleExcelExport) applicationContext.getBean(simpleExcelExportReq.getHandle());

            if (handle == null) {
                throw new SimpleExcelException("spring容器中不存在类型：" + simpleExcelExportReq.getHandle().getSimpleName());
            }

            Class model = ClassUtils.findModel(simpleExcelExportReq.getHandle(), SimpleExcelExport.class);

            context.setHandle(handle);
            context.setModel(model);
        }


        return ChainProcessResult.CONTINUE;
    }


}
