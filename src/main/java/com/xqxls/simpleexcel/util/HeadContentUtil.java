package com.xqxls.simpleexcel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description TODO
 * @Author 姚仲杰#80998699
 * @Date 2022/7/11 15:55
 */
public class HeadContentUtil {


    public static final List<String> ignoreField = new ArrayList<>(3);

    static {
            //这里可以初始化 ignoreField

        
    }

    public static Map<Integer, String> declaredFieldHeadContentMap(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        List<Field> tempFieldList = new ArrayList<>();
        Class<?> tempClass = clazz;
        //todo 这里应该放在后面？
        while (tempClass != null) {
            Collections.addAll(tempFieldList, tempClass.getDeclaredFields());
            tempClass = tempClass.getSuperclass();
        }

        Map<Integer, String> headContentMap = new HashMap<>();

        int position = 0;
        for (Field field : tempFieldList) {
            if (ignoreField.contains(field.getName())) {
                continue;
            }
            ExcelContentProperty excelContentProperty = new ExcelContentProperty();
            excelContentProperty.setField(field);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if(excelProperty == null ){
                continue;
            }
            String[] value = excelProperty.value();
            headContentMap.put(position, value[0]);
            position++;
        }

        return headContentMap;
    }

}
