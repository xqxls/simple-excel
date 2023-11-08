package com.xqxls.simpleexcel.common.base.req;


import com.xqxls.simpleexcel.SimpleExcelContext;
import com.xqxls.simpleexcel.exporter.handler.SimpleExcelExportParamCheckHandler;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
@Data
@SuperBuilder
public class SimpleExcelBaseReq {

    /**
     * 是否接入任务中心
     * 默认不接入
     */
    @Builder.Default
    private Boolean accessTaskCenter = false;


    /**
     * 页面id
     * 前端基于该参数来查询任务，请确保唯一，避免任务混淆
     * 接入任务中心必填，标识功能维度，
     * 默认请用tableId
     */
    private String pageId;


    /**
     * 导入的处理器类
     * 必须实现SimpleExcelImport，且在spring容器内
     */
    private Class<?> handle;


    /**
     * 导出导入文件名称
     * <p>
     * 导入：错误文件文件名
     * 导出：导出文件名
     */
    private String filename;


    /**
     * 分批次大小
     * 默认不分批
     */
    @Builder.Default
    private int batchSize = Integer.MAX_VALUE;;


    /**
     * 允许添加额外参数
     * 可以通过以下方法获取
     *
     * @see SimpleExcelContext#get(String)
     */
    private Map<String, Object> extraParam;


    /**
     * tableId集合
     * 本质上会转换成dynamicHeadList 类型为tableId
     *
     * @see SimpleExcelExportParamCheckHandler#doHandler
     */
    @Singular
    private final List<String> tableIds;


}
