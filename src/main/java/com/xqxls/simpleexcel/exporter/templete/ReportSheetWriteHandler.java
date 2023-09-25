package com.xqxls.simpleexcel.exporter.templete;

import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportSheetWriteHandler implements SheetWriteHandler {

    private String fileName;

    private String dateStr;

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    public ReportSheetWriteHandler(String fileName) {
        this.dateStr = "查询截止时间：" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        this.fileName = fileName;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        Workbook wb = writeWorkbookHolder.getWorkbook();

        Sheet sheet = writeSheetHolder.getSheet();

        //数据列数
        Integer columnSize = null;

        //CLASS导出
        if (HeadKindEnum.CLASS.equals(writeSheetHolder.getExcelWriteHeadProperty().getHeadKind())) {
            columnSize = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap().size();
        } else {
            //动态头导出
            List<List<String>> head = writeSheetHolder.getHead();
            columnSize = head.size();
        }

        //第一行处理
        Row firstRow = sheet.createRow(0);
        Cell cell = firstRow.createCell(0);
        cell.setCellValue(fileName);
        CellStyle cellStyle = wb.createCellStyle();
        //对齐设置
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //字体设置
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setFontName("Calibri");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        //合并单元格
        if (columnSize < 6) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        } else {
            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:F1"));
        }

        //第二行处理
        Row twoRow = sheet.createRow(1);
        Cell twoCell = null;

        if (columnSize < 6) {
            twoCell = twoRow.createCell(columnSize);
        } else {
            twoCell = twoRow.createCell(6);
        }
        twoCell.setCellValue(dateStr);

        CellStyle cellStyle2 = wb.createCellStyle();
        //对齐设置
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        //字体设置
        Font font2 = wb.createFont();
        font2.setFontHeightInPoints((short) 11);
        font2.setFontName("Calibri");
        cellStyle2.setFont(font2);
        twoCell.setCellStyle(cellStyle2);

        //第三行为空，不用管


    }
}
