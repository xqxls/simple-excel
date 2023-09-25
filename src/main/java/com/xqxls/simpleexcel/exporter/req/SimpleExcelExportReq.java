package com.xqxls.simpleexcel.exporter.req;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.fastjson.annotation.JSONField;
import com.xqxls.simpleexcel.common.base.req.SimpleExcelBaseReq;
import com.xqxls.simpleexcel.exporter.constant.ExportExcelConstant;
import com.xqxls.simpleexcel.exporter.converter.DefaultIntegerConverter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 * 导出参数类
 * @see <a href="https://shuyigroup.feishu.cn/wiki/O0O9wo6v8izY68kGb6ec6TqAnJd">simple excel 使用文档</a>
 */
@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SimpleExcelExportReq extends SimpleExcelBaseReq {

    /**
     * sheet名称(非必填)
     */
    @Builder.Default
    private String sheetName = ExportExcelConstant.SIMPLE_EXCEL_SHEET_NAME;

    /**
     * 获取数据函数流
     * 和handle二选一
     */
    @JSONField(serialize = false)
    private Supplier<List> fetchData;


    /**
     * 表头类
     * <p>
     * 使用fetchData，必填
     */
    private Class<?> fetchDataClass;


    /**
     * 从第几行开始写，默认是0
     */
    private Integer relativeHeadRowIndex;


    /**
     * easyExcel原生扩展
     * <p>
     * 自定义样式、格式、数据拦截
     */
    private List<WriteHandler> writeHandlerList;


    /**
     * easyExcel原生扩展
     * <p>
     * 定义数据转换格式
     */
    @Builder.Default
    private List<Converter<?>> converterList = Arrays.asList(new DefaultIntegerConverter());


}
