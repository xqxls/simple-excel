package com.xqxls.simpleexcel;

import com.xqxls.simpleexcel.common.base.SimpleExcel;

import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/10
 */
public interface SimpleExcelImport<T>  extends SimpleExcel {


    /**
     * 导入实现
     * @param list
     */
    void importData(List<T> list);





}
