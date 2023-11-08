package com.xqxls.simpleexcel.exporter.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;


/**
 * @USER: com.xqxls
 * @DATE: 2023/4/27
 */
public class DefaultIntegerConverter implements Converter<Integer> {


    //导出无用
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    //导出无用
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        return null;
    }


    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }


    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String fieldName = contentProperty.getHead().getFieldName();
        if (fieldName.contains("isEnable")) {
            if (value.equals(1)) {
                return new CellData<>("是");
            } else if (value.equals(0)) {
                return new CellData<>("否");
            }
        }
        return new CellData(new BigDecimal(Integer.toString(value)));
    }


}
