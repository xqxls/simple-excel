package com.xqxls.simpleexcel.compatible.converters.localdate;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.xqxls.simpleexcel.compatible.utils.DateUtil;
import com.xqxls.simpleexcel.compatible.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * LocalDate and number converter
 *
 * @author Jiaju Zhuang
 */
public class LocalDateNumberConverter implements Converter<LocalDate> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.getLocalDate(cellData.getNumberValue().doubleValue(),
                globalConfiguration.getUse1904windowing());
        } else {
            return DateUtils.getLocalDate(cellData.getNumberValue().doubleValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing());
        }
    }

    @Override
    public CellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                          GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData<>(
                BigDecimal.valueOf(DateUtil.getExcelDate(value, globalConfiguration.getUse1904windowing())));
        } else {
            return new CellData<>(BigDecimal.valueOf(
                DateUtil.getExcelDate(value, contentProperty.getDateTimeFormatProperty().getUse1904windowing())));
        }
    }
}
