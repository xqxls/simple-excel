package com.xqxls.controller.vo;

import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @USER: huzhuo
 * @DATE: 2023/4/14
 */
@Data
public class SimpleExcelTaskVO extends SimpleExcelTask {


    /**
     * 错误信息集合
     */
    @ApiModelProperty(value = "错误信息集合")
    private List<SimpleExcelErrorMessageVO> failedMessageList;
}
