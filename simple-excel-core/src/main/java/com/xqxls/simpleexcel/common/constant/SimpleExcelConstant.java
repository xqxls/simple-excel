package com.xqxls.simpleexcel.common.constant;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
public class SimpleExcelConstant {


    /**
     * 任务
     */
    public static class Task {

        /**
         * 类型
         */
        public final static class Type {

            /**
             * 导入
             */
            public final static Integer IMPORTER = 1;

            /**
             * 导出
             */
            public final static Integer EXPORTER = 2;

        }

        /**
         * 状态
         */
        public static class Status {

            /**
             * 初始化
             */
            public final static Integer INIT = 0;

            /**
             * 进行中
             */
            public final static Integer RUNNING = 1;


            /**
             * 导入导出操作完成了
             * 但是错误文件没上传
             */
            public final static Integer COMPLETE = 2;


            /**
             * 成功
             */
            public final static Integer SUCCESS = 3;


            /**
             * 失败
             */
            public final static Integer FAIL = 4;

        }

        /**
         * 弹窗是否关闭
         */
        public static class ToastClose {


            /**
             * 开启
             */
            public final static Integer OPEN = 0;

            /**
             * 关闭
             */
            public final static Integer CLOSE = 1;


        }

        /**
         * 剩余间隔时间正在计算标识
         */
        public final static Integer REMAINING_RUNNING = -1;

    }

    /**
     * task定时落库时间间隔(秒)
     */
    public final static Integer TASK_STORAGE_INTERVAL = 4;


    /**
     * 错误日志不显示行数
     */
    public final static Integer ERROR_MESSAGE_NO_SHOW_ROW  = -1;

}
