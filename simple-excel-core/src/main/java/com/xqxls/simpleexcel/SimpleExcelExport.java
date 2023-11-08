package com.xqxls.simpleexcel;

import com.xqxls.simpleexcel.common.base.SimpleExcel;
import com.xqxls.simpleexcel.exporter.res.SimpleExcelExportResult;


/**
 * @USER: com.xqxls
 * @DATE: 2023/4/23
 */
public interface SimpleExcelExport<T> extends SimpleExcel {


    /**
     * 导出实现
     *
     * @param startId   开始id 首次传入0
     * @param batchSize 分批数量
     *                  防止深度分页
     * @return
     */
    SimpleExcelExportResult<T> exportData(Long startId, Integer batchSize);


    /**
     * 获取总数量，只会调用一次
     *
     * @return
     */
    default Long fetchTotalCount() {
        return null;
    }
}
