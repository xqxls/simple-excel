package com.xqxls.service;

import com.xqxls.controller.co.SimpleExcelTaskSearchCO;
import com.xqxls.controller.vo.SimpleExcelTaskVO;

import java.util.List;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/10/15 19:12
 */
public interface SimpleExcelTaskService {

    /**
     * 不分页条件查询
     *
     * @return
     */
    List<SimpleExcelTaskVO> findList(SimpleExcelTaskSearchCO simpleExcelTaskSearchCO);


    /**
     * 关闭弹窗
     *
     * @param id
     */
    void toastClose(Long id);
}
