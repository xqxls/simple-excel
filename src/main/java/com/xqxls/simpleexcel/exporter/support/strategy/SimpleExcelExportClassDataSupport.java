package com.xqxls.simpleexcel.exporter.support.strategy;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportChainContext;
import com.xqxls.simpleexcel.exporter.support.SimpleExcelExportDataSupport;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @USER: xqxls
 * @DATE: 2023/4/27
 */
public class SimpleExcelExportClassDataSupport extends SimpleExcelExportDataSupport {


    public SimpleExcelExportClassDataSupport(SimpleExcelExportChainContext context) {
        super(context);
    }

    @Override
    protected void doInitialize(ExcelWriterBuilder writerBuilder, ExcelWriterSheetBuilder writerSheetBuilder) {
        ExcelWriterBuilder head = writerBuilder.head(context.getModel());
    }

    @Override
    protected List<?> converWriteList(List<?> handList) {

        return handList
                .stream()
                .map(item -> {
                    //处理map类型
                    if (Map.class.isInstance(item)) {
                        Map map = (Map) item;
                        return BeanUtil.toBean(map, context.getModel());
                    }
                    return item;
                })
                .collect(Collectors.toList());
    }


}
