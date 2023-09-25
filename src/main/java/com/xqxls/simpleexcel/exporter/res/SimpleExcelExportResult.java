package com.xqxls.simpleexcel.exporter.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Data
@AllArgsConstructor
public class SimpleExcelExportResult<T> {


    /**
     * 是否强制继续执行
     */
    private Boolean next;


    /**
     * 下次起始id
     */
    private Long nextStartId;

    /**
     * 数据结果集
     */
    private List<T> data = new ArrayList<>();


    public static <T> SimpleExcelExportResult<T> success(List<T> data, Long nextStartId) {
        return new SimpleExcelExportResult<>(false, nextStartId, data);
    }

    public static <T> SimpleExcelExportResult<T> success(Boolean next, List<T> data, Long nextStartId) {
        return new SimpleExcelExportResult<>(next, nextStartId, data);
    }


    public boolean hasNext() {
        return next || (nextStartId != null && CollectionUtils.isNotEmpty(data));
    }

}
