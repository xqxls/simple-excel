package com.xqxls.simpleexcel.common.chain;

/**
 * 处理器处理结果
 * 控制执行行为
 *
 * @USER: xqxls
 * @DATE: 2023/1/7
 */
public enum ChainProcessResult {

    /**
     * 继续执行下一个handler
     */
    CONTINUE(0, "CONTINUE_PROCESSING"),

    /**
     * 处理结束，不再往后传递handler
     */
    ABORT(1, "PROCESSING_COMPLETE");


    private int value;
    private String type;


    ChainProcessResult(int value, String type) {
        this.value = value;
        this.type = type;
    }


    public int getValue() {
        return this.value;
    }


    public String getType() {
        return this.type;
    }
}
