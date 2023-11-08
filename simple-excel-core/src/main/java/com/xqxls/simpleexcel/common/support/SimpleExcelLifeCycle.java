package com.xqxls.simpleexcel.common.support;

import java.util.List;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/26
 * 生命周期能力提供
 */
public interface SimpleExcelLifeCycle {

    /**
     * 初始化
     */
    void init();

    /**
     * 单次操作前调用
     */
    void singleHandleBefore();

    /**
     * 单次操作后调用
     *
     * @param handList
     */
    void singleHandleAfter(List handList);

    /**
     * 操作完成后调用
     */
    void onComplete();

    /**
     * 异常处理
     *
     * @param e
     */
    void onException(Exception e);

    /**
     * 兜底处理
     */
    void onFinally();
}
