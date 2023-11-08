package com.xqxls.simpleexcel.common.chain;

/**
 * 责任链
 *
 * @USER: com.xqxls
 * @DATE: 2023/1/7
 */
public interface Chain<Context> {


    /**
     * 执行
     *
     * @param context
     */
    void execute(Context context);
}
