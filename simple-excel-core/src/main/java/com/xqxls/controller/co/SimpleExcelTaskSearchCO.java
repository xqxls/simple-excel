package com.xqxls.controller.co;

import com.xqxls.simpleexcel.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @USER: huzhuo
 * @DATE: 2023/4/14
 */
@Data
public class SimpleExcelTaskSearchCO extends BaseEntity {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "每页条数")
    private Integer size = 10;

    @ApiModelProperty(value = "是否是正序，和orderBy搭配使用")
    private Boolean asc;

    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    @ApiModelProperty(value = "返回的条数，条件搜索用")
    private Integer limit;

    /**
     * 页面id
     */
    @ApiModelProperty(value = "pageId")
    private String pageId;


    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 类型：1-导入,2-导出
     *
     * @see
     */
    @ApiModelProperty(value = "1导入 2导出")
    private Integer type;

    /**
     * @see
     */
    @ApiModelProperty(value = " 状态 0-初始,1、2-进行中,3-完成,4-失败")
    private Integer status;


    @ApiModelProperty(value = "0-初始,1、2-进行中,3-完成,4-失败")
    private List<Integer> statusList;

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


    @ApiModelProperty(value = "是否查询当前用户")
    private Boolean queryCurrentUser;


    @ApiModelProperty(value = "弹窗是否关闭 0 未关闭 1关闭")
    private Integer toastClose;


}
