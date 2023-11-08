package com.xqxls.simpleexcel.persist.repository;

import com.github.pagehelper.PageInfo;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import com.xqxls.simpleexcel.persist.qo.SimpleExcelTaskQo;

import java.util.List;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/14
 */
public interface SimpleExcelTaskRepository {


    /**
     * 不分页查询
     *
     * @param query
     * @param orderBy
     * @param asc
     * @param limit
     * @return
     */
    List<SimpleExcelTask> findList(SimpleExcelTaskQo query, String orderBy, Boolean asc, Integer limit);


    /**
     * 分页查询
     *
     * @param query
     * @param page
     * @param size
     * @param orderBy
     * @param asc
     * @return
     */
    PageInfo<SimpleExcelTask> findPage(SimpleExcelTaskQo query, Integer page, Integer size, String orderBy, Boolean asc);

    /**
     * 通过id更新
     *
     * @param simpleExcelTask
     */
    void updateById(SimpleExcelTask simpleExcelTask);


    /**
     * 批量删除
     *
     * @param rows
     */
    void deleteBatch(List<Long> rows);



    void insert(SimpleExcelTask task);
}
