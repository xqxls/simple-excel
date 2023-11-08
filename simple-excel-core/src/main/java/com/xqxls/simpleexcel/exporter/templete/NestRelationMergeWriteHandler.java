package com.xqxls.simpleexcel.exporter.templete;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @USER: com.xqxls
 * @DATE: 2023/6/15
 * 嵌套关系合并处理器
 */
@Data
public class NestRelationMergeWriteHandler extends AbstractRowWriteHandler {


    private Map<Integer, List<NestRelationMerge>> nestRelationMergeMap = new HashMap<>();

    /**
     * 第一行的所在的行数
     */
    private Integer firstDataRowNumber;

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }

        if (firstDataRowNumber == null) {
            firstDataRowNumber = row.getRowNum();
        }

        List<NestRelationMerge> nestRelationMergeList;

        if ((nestRelationMergeList = nestRelationMergeMap.get(relativeRowIndex)) != null) {

            nestRelationMergeList.forEach(merge -> {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(merge.getFirstRow() + firstDataRowNumber, merge.getLastRow() + firstDataRowNumber, merge.getFirstCol(), merge.getLastCol());
                writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
            });

            //清空该key，防止重复执行
            nestRelationMergeMap.remove(relativeRowIndex);

        }

    }

}
