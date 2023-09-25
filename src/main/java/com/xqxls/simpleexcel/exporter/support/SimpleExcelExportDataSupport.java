package com.xqxls.simpleexcel.exporter.support;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.xqxls.simpleexcel.compatible.converters.localdate.LocalDateStringConverter;
import com.xqxls.simpleexcel.compatible.converters.localdatetime.LocalDateTimeStringConverter;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportChainContext;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import com.xqxls.simpleexcel.exporter.support.strategy.SimpleExcelExportClassDataSupport;
import com.xqxls.simpleexcel.util.FileUtils;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @USER: xqxls
 * @DATE: 2023/4/26
 */
public abstract class SimpleExcelExportDataSupport {

    /**
     * 上下文
     */
    protected SimpleExcelExportChainContext context;

    /**
     * 响应
     */
    private HttpServletResponse response;

    /**
     * 用户原始请求
     */
    protected SimpleExcelExportReq simpleExcelExportReq;


    public SimpleExcelExportDataSupport(SimpleExcelExportChainContext context) {
        this.context = context;
        this.simpleExcelExportReq = context.getSimpleExcelExportReq();
        this.response = context.getResponse();
        initialize();
    }

    /**
     * 初始化操作
     */
    @SneakyThrows
    private void initialize() {

        //1、 文件处理
        ExcelWriterBuilder writerBuilder;

        //生成导出成功文件名
        String fileName = buildFileName();

        // 设置响应头
        if (simpleExcelExportReq.getAccessTaskCenter()) {
            context.setSuccessFileName(fileName);
            File file = FileUtils.buildFileTemporaryFileName(fileName);
            writerBuilder = EasyExcel.write(file);
            context.setSuccessFile(file);
        } else {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            writerBuilder = EasyExcel.write(response.getOutputStream());
        }

        ExcelWriterSheetBuilder writerSheetBuilder = EasyExcel.writerSheet(0, simpleExcelExportReq.getSheetName());

        if (simpleExcelExportReq.getRelativeHeadRowIndex() != null) {
            writerBuilder.relativeHeadRowIndex(simpleExcelExportReq.getRelativeHeadRowIndex());
        }

        //添加默认支持的转换器
        writerBuilder.registerConverter(new LocalDateStringConverter());
        writerBuilder.registerConverter(new LocalDateTimeStringConverter());

        doInitialize(writerBuilder, writerSheetBuilder);

        if (CollectionUtils.isNotEmpty(simpleExcelExportReq.getConverterList())) {
            simpleExcelExportReq.getConverterList()
                    .stream()
                    .forEach(writerBuilder::registerConverter);
        }

        //默认插入自动列宽
        writerBuilder.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());

        if (CollectionUtils.isNotEmpty(simpleExcelExportReq.getWriteHandlerList())) {
            simpleExcelExportReq.getWriteHandlerList()
                    .stream()
                    .forEach(writerBuilder::registerWriteHandler);
        }


        ExcelWriter excelWriter = writerBuilder.autoCloseStream(false).build();

        WriteSheet writeSheet = writerSheetBuilder.build();

        context.setExcelWriter(excelWriter);
        context.setWriteSheet(writeSheet);

        //写一行空数据 强制生成表头
        context.getExcelWriter().write(new ArrayList(), context.getWriteSheet());
    }

    /**
     * 初始化
     * 子类实现
     */
    protected abstract void doInitialize(ExcelWriterBuilder writerBuilder, ExcelWriterSheetBuilder writerSheetBuilder);


    /**
     * 处理数据
     *
     * @param handList
     */
    public void handleDataList(List<?> handList) {

        if (CollectionUtils.isNotEmpty(handList)) {
            List<?> writeList = converWriteList(handList);
            context.getExcelWriter().write(writeList, context.getWriteSheet());
        }
    }

    /**
     * 动态数据集转换
     *
     * @param handList
     * @return
     */
    protected abstract List<?> converWriteList(List<?> handList);


    private String buildFileName() {
        StringBuffer sb = new StringBuffer();
        String fileNameParam = simpleExcelExportReq.getFilename();
        if (fileNameParam.lastIndexOf('.') != -1) {
            fileNameParam = fileNameParam.substring(0, fileNameParam.indexOf('.'));
        }
        sb.append(fileNameParam);
        sb.append("_");
        sb.append(context.getStartTime()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(".xlsx");
        return sb.toString();
    }


    public static SimpleExcelExportDataSupport chooseDataSupport(SimpleExcelExportChainContext context) {
        return new SimpleExcelExportClassDataSupport(context);

    }

    /**
     * 重制响应头
     */
    public void resetResponse() {
        if (context.getCurTask() == null) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
        }
    }

}
