package com.xqxls.simpleexcel.common.chain;

/**
 * 通用责任链处理基类
 *
 * @USER: xqxls
 * @DATE: 2023/1/7
 */
public interface GenericHandler<Context> {


    /**
     * 处理实现
     *
     * @param context
     * @return
     */
    ChainProcessResult doHandler(Context context);

    /**
     * 处理器是否支持处理
     * 默认处理，不处理 重写该方法
     *
     * @param context
     * @return
     */
    default boolean isSupport(Context context) {
        return true;
    }


}
