package com.xqxls.simpleexcel.common.base;


/**
 * @USER: xqxls
 * @DATE: 2023/4/11
 */
public interface SimpleExcel {

    /**
     * 导入导出前调用
     * 只会被执行一次
     */
    default void init() {
        //do some thing...
    }


    /**
     * 导入导出后被调用
     * 只会被调用一次
     */
    default void complete() {
        //do something...
    }



}
