package com.xqxls.service;

import com.xqxls.dto.UmsAdminExportDTO;
import com.xqxls.dto.UmsAdminParam;
import com.xqxls.model.UmsAdmin;

import java.util.List;

/**
 * 后台用户管理Service
 * Created by com.xqxls on 2018/4/26.
 */
public interface UmsAdminService {


    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 根据用户id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, UmsAdmin admin);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 查找全部
     * @return
     */
    List<UmsAdmin> findAll();

    /**
     * 查找导出数据
     * @return
     */
    List<UmsAdminExportDTO> findExportData();

}
