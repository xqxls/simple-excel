package com.xqxls.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @USER: huzhuo
 * @DATE: 2023/4/14
 */
@Data
public class SimpleExcelErrorMessageVO {


    @ApiModelProperty(value = "第几行")
    private String row;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String msg;
}
