package com.xqxls.simpleexcel;

import com.xqxls.exception.ApiException;
import com.xqxls.simpleexcel.exporter.SimpleExcelExportProcessor;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.res.SimpleExcelExportRes;


/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
public class SimpleExcelUtils {


    private static SimpleExcelExportProcessor simpleExcelExportProcessor;


    /**
     * 导出
     */
    public static SimpleExcelExportRes doExport(SimpleExcelExportReq req) {

        if (simpleExcelExportProcessor == null) {
            throw new ApiException("simpleExcelExportProcessor 初始化失败，请检查配置");
        }

        return simpleExcelExportProcessor.doExport(req);
    }


    public static void registerSimpleExcelExportProcessor(SimpleExcelExportProcessor simpleExcelExportProcessor) {
        SimpleExcelUtils.simpleExcelExportProcessor = simpleExcelExportProcessor;
    }

}
