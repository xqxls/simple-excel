package com.xqxls.simpleexcel.persist.qo;

import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import lombok.Data;

import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/6/3
 */
@Data
public class SimpleExcelTaskQo extends SimpleExcelTask {

    private List<Integer> statusList;
}
