package com.xqxls.simpleexcel.common;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.util.StringUtils;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
public class ErrorMessage {

    /**
     * 行数
     */
    @ExcelIgnore
    private Integer rowIndex;


    @ExcelProperty("失败行数")
    private String rowIndexStr;

    /**
     * 错误信息
     */
    @ExcelProperty("失败内容")
    private String msg;


    public static ErrorMessage error(Integer rowIndex, String msg) {
        String rowIndexStr = "";
        if (rowIndex == null) {
            //保证排在最后面
            rowIndex = Integer.MAX_VALUE;
            rowIndexStr = "程序异常已被终止，请联系管理员";
        } else if (rowIndex.equals(SimpleExcelConstant.ERROR_MESSAGE_NO_SHOW_ROW)) {
            //如果是-1表示不需要显示行数

        } else {
            rowIndexStr = "第" + rowIndex + "行";
        }
        return new ErrorMessage(rowIndex, rowIndexStr, msg);
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!StringUtils.isEmpty(rowIndexStr)) {
            sb.append(rowIndexStr + ",");
        }
        sb.append(msg);
        return sb.toString();
    }
}
