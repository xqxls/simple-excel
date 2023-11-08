package com.xqxls.simpleexcel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @USER: com.xqxls
 * @DATE: 2023/1/6
 */
@AllArgsConstructor
@Getter
public enum SimpleExcelErrorCode {

    /**
     * 致命错误
     */
    FATAL_ERROR("FATAL_ERROR", "致命错误"),

    MAX_ROW_LIMIT("MAX_ROW_LIMIT", "超出最大行数限制"),

    // 这里的错需要更换
    IMPORT_INTERRUPT("IMPORT_INTERRUPT", "导入中断"),

    EXCEL_FORMAT_CONVERSION("EXCEL_FORMAT_CONVERSION", "excel格式转换错误"),

    EXPORT_DYNAMIC_HEADER_EMPTY("EXCEL_FORMAT_CONVERSION", "动态表头生成为空，请检查传参group是否合法！");


    private String errorCode;

    private String returnMsg;
}
