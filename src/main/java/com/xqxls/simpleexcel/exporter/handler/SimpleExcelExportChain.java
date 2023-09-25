package com.xqxls.simpleexcel.exporter.handler;

import com.xqxls.simpleexcel.common.chain.AbstractChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Component
public class SimpleExcelExportChain extends AbstractChain<SimpleExcelExportChainContext> {


    @Resource
    private SimpleExcelExportParamCheckHandler simpleExcelExportParamCheckHandler;

    @Resource
    private SimpleExcelExportPrepareHandler simpleExcelExportPrepareHandler;

    @Resource
    private SimpleExcelExportHandler simpleExcelExportHandler;


    @Override
    public void init() {
        genericHandlerList.add(simpleExcelExportParamCheckHandler);
        genericHandlerList.add(simpleExcelExportPrepareHandler);
        genericHandlerList.add(simpleExcelExportHandler);
    }
}
