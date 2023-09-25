package com.xqxls.simpleexcel.exporter.templete;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.LinkedList;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/6/15
 * 嵌套关系合并处理器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EachColumnMergeWriteHandler extends AbstractRowWriteHandler {


    private List<EachColumnMerge> eachColumnMergeList = new LinkedList<>();



    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {

        for (EachColumnMerge eachColumnMerge : eachColumnMergeList) {
            if (eachColumnMerge.getFirstCol() == null || eachColumnMerge.getLastCol() == null) {
                continue;
            }
            CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum(),
                    eachColumnMerge.getFirstCol(), eachColumnMerge.getLastCol());
            writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
        }

    }

}
