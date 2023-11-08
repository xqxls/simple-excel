package com.xqxls.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @USER: huzhuo
 * @DATE: 2023/6/3
 */
@Data
public class SimpleExcelDownloadVO {



    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;


    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;


    /**
     * 类型：1-导入,2-导出
     *
     * @see
     */
    @ApiModelProperty(value = "1导入 2导出")
    private Integer type;

    @ApiModelProperty(value = "类型")
    private String typeStr;

    /**
     * 状态
     *
     * @see
     */
    @ApiModelProperty(value = "0-初始,1-进行中,3-成功,4-失败")
    private Integer status;

    /**
     * 下载文件id
     */
    @ApiModelProperty(value = "下载文件id")
    private Long downloadId;

    @ApiModelProperty(value = "状态字符串")
    private String statusStr;
}
