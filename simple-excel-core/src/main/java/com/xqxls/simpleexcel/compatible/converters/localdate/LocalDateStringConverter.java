package com.xqxls.simpleexcel.compatible.converters.localdate;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.xqxls.simpleexcel.compatible.utils.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * LocalDate and string converter
 *
 * @author Jiaju Zhuang
 */
public class LocalDateStringConverter implements Converter<LocalDate> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws ParseException {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.parseLocalDate(cellData.getStringValue(), null, globalConfiguration.getLocale());
        } else {
            return DateUtils.parseLocalDate(cellData.getStringValue(),
                    contentProperty.getDateTimeFormatProperty().getFormat(), globalConfiguration.getLocale());
        }
    }

    @Override
    public CellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                          GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData<>(DateUtils.format(value, null, globalConfiguration.getLocale()));
        } else {
            return new CellData<>(
                    DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat(),
                            globalConfiguration.getLocale()));
        }
    }
}
