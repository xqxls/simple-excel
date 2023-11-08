package com.xqxls.simpleexcel.compatible.converters.localdatetime;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.xqxls.simpleexcel.compatible.utils.DateUtils;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * LocalDateTime and string converter
 *
 * @author Jiaju Zhuang
 */
public class LocalDateTimeStringConverter implements Converter<LocalDateTime> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) throws ParseException {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.parseLocalDateTime(cellData.getStringValue(), null, globalConfiguration.getLocale());
        } else {
            return DateUtils.parseLocalDateTime(cellData.getStringValue(),
                    contentProperty.getDateTimeFormatProperty().getFormat(), globalConfiguration.getLocale());
        }
    }

    @Override
    public CellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
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
