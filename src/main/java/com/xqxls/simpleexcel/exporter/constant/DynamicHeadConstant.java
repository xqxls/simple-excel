package com.xqxls.simpleexcel.exporter.constant;

/**
 * @USER: xqxls
 * @DATE: 2023/4/25
 */
public class DynamicHeadConstant {

    /**
     * 表头类型
     */
    public static class Type {

        /**
         * 分组
         * 包含多个单列
         * 可实现动态查询
         */
        public final static String GROUP = "GROUP";


        /**
         * 普通的单列
         */
        public final static String SIMPLE = "SIMPLE";

    }

    /**
     * 分隔符
     */
    public final static String SEPARATOR = "#";



    /**
     * 复杂表头分隔符
     */
    public final static String COMPLEX_SEPARATOR = "->";
}
