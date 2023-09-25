package com.xqxls.simpleexcel.exception;

import lombok.Data;
import lombok.ToString;

/**
 * @USER: xqxls
 * @DATE: 2023/1/6
 */
@Data
@ToString
public class SimpleExcelException extends RuntimeException {

    private String errorCode;
    private String returnMsg;


    public SimpleExcelException(String errorCode, String returnMsg) {
        super(returnMsg);
        this.errorCode = errorCode;
        this.returnMsg = returnMsg;
    }

    public SimpleExcelException(SimpleExcelErrorCode errorCode) {
        super(errorCode.getReturnMsg());
        this.errorCode = errorCode.getErrorCode();
        this.returnMsg = errorCode.getReturnMsg();
    }

    public SimpleExcelException(SimpleExcelErrorCode errorCode, Throwable cause) {
        super(errorCode.getReturnMsg(), cause);
        this.errorCode = errorCode.getErrorCode();
        this.returnMsg = errorCode.getReturnMsg();
    }

    public SimpleExcelException(String message) {
        super(message);
        this.errorCode = SimpleExcelErrorCode.FATAL_ERROR.getErrorCode();
        this.returnMsg = message;
    }


}
