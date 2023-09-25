package com.xqxls.simpleexcel.exporter.handler;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.xqxls.simpleexcel.SimpleExcelExport;
import com.xqxls.simpleexcel.common.base.ctx.SimpleExcelBaseChainContext;
import com.xqxls.simpleexcel.common.support.SimpleExcelTaskCalculateSupport;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportDataSupport;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportSupport;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Data
@SuperBuilder
public class SimpleExcelExportChainContext extends SimpleExcelBaseChainContext {

    /**
     * 用户请求
     */
    private SimpleExcelExportReq simpleExcelExportReq;


    /**
     * 用户处理器
     */
    private SimpleExcelExport handle;


    /**
     * easyExcel原生扩展
     * <p>
     * simpleExcel在动态头模式下会被增强(会被传入特定字段值)
     */
    private List<Converter<?>> dynamicConverterList;

    /**
     * Repository类
     *
     */
    // private TableFiledConfigExRepository tableFiledConfigExRepository;

    /**
     * 响应流
     */
    private HttpServletResponse response;

    /**
     * 导出数据处理
     */
    private SimpleExcelExportDataSupport exportDataSupport;


    /**
     * 导出业务处理
     */
    private SimpleExcelExportSupport exportSupport;


    /**
     * 任务计算处理
     */
    SimpleExcelTaskCalculateSupport taskCalculateSupport;


    /**
     * 写入Excel对象
     */
    private ExcelWriter excelWriter;


    /**
     * 写入WriteSheet对象
     */
    private WriteSheet writeSheet;


    /**
     * 导出成功文件
     */
    private File successFile;


    /**
     * 导出成功文件名称
     */
    private String successFileName;


    /**
     * 导出失败文件
     */
    private File errorFile;


    /**
     * 导出失败文件名称
     */
    private String errorFileName;
}
