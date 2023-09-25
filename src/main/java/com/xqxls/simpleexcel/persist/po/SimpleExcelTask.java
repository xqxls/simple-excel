package com.xqxls.simpleexcel.persist.po;

import com.xqxls.simpleexcel.common.ErrorMessage;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import com.xqxls.simpleexcel.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/10
 */
@Data
@Table(name = "simple_excel_task")
public class SimpleExcelTask extends BaseEntity {


    /**
     * 页面id
     */
    @ApiModelProperty(value = "pageId")
    private String pageId;


    /**
     * 类型：1-导入,2-导出
     *
     * @see com.xqxls.simpleexcel.common.constant.SimpleExcelConstant.Task.Type
     */
    @ApiModelProperty(value = "1导入 2导出")
    private Integer type;

    /**
     * 状态
     *
     * @see com.xqxls.simpleexcel.common.constant.SimpleExcelConstant.Task.Status
     */
    @ApiModelProperty(value = "0-初始,1、2-进行中,3-成功,4-失败")
    private Integer status;

    /**
     * 目前只能获取大概的条数（每个excel都会记录一个总条数，但是这个总条数由于各种空行等原因，不一定正确），无法精确获取，要精确获取的自己在监听器里面一条条统计。
     */

    @ApiModelProperty(value = "最大预估条数")
    private Long estimateCount;


    /**
     * 实际总记录数 为成功记录数+失败记录数
     */
    @ApiModelProperty(value = "实际总记录数")
    private Long totalCount;

    /**
     * 成功记录数
     */
    @ApiModelProperty(value = "成功记录数")
    private Long successCount;

    /**
     * 失败记录数
     */
    @ApiModelProperty(value = "失败记录数")
    private Long failedCount;


    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 失败文件id
     */
    @ApiModelProperty(value = "失败文件id")
    private Long failedFileId;

    /**
     * 失败文件id
     */
    @ApiModelProperty(value = "失败文件名称")
    private String failedFileName;

    /**
     * 失败文件id
     */
    @ApiModelProperty(value = "成功文件id")
    private Long successFileId;


    /**
     * 失败文件id
     */
    @ApiModelProperty(value = "成功文件名称")
    private String successFileName;


    /**
     * 失败消息
     * <p>
     * 简要消息
     */
    @ApiModelProperty(value = "失败消息")
    private String failedMessage;


    /**
     * 内部的失败消息集合，用于计算失败消息
     * 这个是实时的，但是组件错误可能在用户错误前面所以不是顺序的
     */
    private List<ErrorMessage> innerFailedMessageList;

    /**
     * 这个是顺序错误日志
     * 最后落库，保证最终展示为最新的10条
     */
    private List<ErrorMessage> innerFailedCompositeMessageList;

    /**
     * 剩余完成时间
     */
    @ApiModelProperty(value = "剩余完成时间(秒) -1代表正在计算 ")
    private Integer remainingCompletionTime;

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
     * 弹窗是否关闭
     */
    @ApiModelProperty(value = "弹窗是否关闭 0 未关闭 1关闭")
    private Integer toastClose;


    @ApiModelProperty(value = "参数快照")
    private String paramSnapshot;

    /**
     * 获取一个初始化过的task
     *
     * @return
     */
    public static SimpleExcelTask of() {
        SimpleExcelTask simpleExcelTask = new SimpleExcelTask();
        simpleExcelTask.setTotalCount(0L);
        simpleExcelTask.setSuccessCount(0L);
        simpleExcelTask.setFailedCount(0L);
        simpleExcelTask.setInnerFailedMessageList(new ArrayList<>());
        simpleExcelTask.setInnerFailedCompositeMessageList(new ArrayList<>());
        simpleExcelTask.setRemainingCompletionTime(SimpleExcelConstant.Task.REMAINING_RUNNING);
        simpleExcelTask.setToastClose(SimpleExcelConstant.Task.ToastClose.OPEN);
        return simpleExcelTask;
    }
}
