package com.xqxls.simpleexcel.exporter.templete;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EachColumnMerge {

    /**
     * 起始列
     */
    private Integer firstCol;


    /**
     * 终止列
     */
    private Integer lastCol;


    public static EachColumnMerge merge(Integer firstCol, Integer lastCol) {
        return new EachColumnMerge(firstCol,lastCol);
    }
}
