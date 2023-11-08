package com.xqxls.simpleexcel.exporter.templete;

import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

public class SimpleExcelColumnWidthStrategy extends SimpleColumnWidthStyleStrategy {


    public SimpleExcelColumnWidthStrategy() {
        super(20);
    }

    /**
     * @param columnWidth
     */
    public SimpleExcelColumnWidthStrategy(Integer columnWidth) {
        super(columnWidth);
    }

    @Override
    public String uniqueValue() {
        return "SimpleColumnWidthStrategy";
    }
}
