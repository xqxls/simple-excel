package com.xqxls.simpleexcel.persist.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.exception.ApiException;
import com.xqxls.simpleexcel.common.domain.UserDto;
import com.xqxls.simpleexcel.common.entity.BaseEntity;
import com.xqxls.simpleexcel.common.util.UserUtil;
import com.xqxls.simpleexcel.persist.dao.SimpleExcelTaskMapper;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import com.xqxls.simpleexcel.persist.qo.SimpleExcelTaskQo;
import com.xqxls.simpleexcel.persist.repository.SimpleExcelTaskRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/4/14
 */
@Repository
public class SimpleExcelTaskRepositoryImpl implements SimpleExcelTaskRepository {


    @Resource
    private SimpleExcelTaskMapper simpleExcelTaskMapper;

    @Override
    public List<SimpleExcelTask> findList(SimpleExcelTaskQo query, String orderBy, Boolean asc, Integer limit) {

        Example example = createExample(query, orderBy, asc, limit);
        return simpleExcelTaskMapper.selectByExample(example);
    }

    @Override
    public PageInfo<SimpleExcelTask> findPage(SimpleExcelTaskQo query, Integer page, Integer size, String orderBy, Boolean asc) {
        PageHelper.startPage(page, size);
        Example example = createExample(query, orderBy, asc, null);
        List<SimpleExcelTask> resultList = simpleExcelTaskMapper.selectByExample(example);
        return new PageInfo<>(resultList);
    }

    @Override
    public void updateById(SimpleExcelTask simpleExcelTask) {
        simpleExcelTaskMapper.updateByPrimaryKeySelective(simpleExcelTask);
    }

    @Override
    public void deleteBatch(List<Long> rows) {
        if (CollectionUtils.isEmpty(rows)) {
            return;
        }
        UserDto userDTO = UserUtil.getCurrentUser();
        rows.forEach(id -> {
            SimpleExcelTask simpleExcelTask = new SimpleExcelTask();
            simpleExcelTask.setIsDel(1);
            simpleExcelTask.setId(id);
            simpleExcelTask.setUpdateTime(LocalDateTime.now());
            simpleExcelTask.setUpdateId(userDTO.getId());
            simpleExcelTask.setUpdateName(userDTO.getUsername());
            simpleExcelTaskMapper.updateByPrimaryKeySelective(simpleExcelTask);
        });


    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insert(SimpleExcelTask task) {
        simpleExcelTaskMapper.insert(task);
    }

    private Example createExample(SimpleExcelTaskQo query, String orderBy, Boolean asc, Integer limit) {

        Example example = createBaseExample(query, SimpleExcelTask.class, orderBy, asc);
        List<Example.Criteria> oredCriteria = example.getOredCriteria();
        if (CollUtil.isEmpty(oredCriteria) || oredCriteria.size() > 1) {
            throw new ApiException("CollUtil.isEmpty(oredCriteria) ||  oredCriteria.size() > 1");
        }

        Example.Criteria criteria = oredCriteria.get(0);

        if (query != null) {

            if (query.getId() != null) {
                criteria.andEqualTo("id", query.getId());
            }

            if (!StringUtils.isEmpty(query.getPageId())) {
                criteria.andEqualTo("pageId", query.getPageId());
            }

            if (query.getType() != null) {
                criteria.andEqualTo("type", query.getType());
            }

            if (query.getStatus() != null) {
                criteria.andEqualTo("status", query.getStatus());
            }

            if (CollectionUtils.isNotEmpty(query.getStatusList())) {
                criteria.andIn("status", query.getStatusList());
            }


            if (StringUtils.isNotEmpty(query.getFileName())) {
                criteria.andLike("fileName", "%" + query.getFileName() + "%");
            }
            if (query.getStartTime() != null) {
                criteria.andGreaterThan("startTime", query.getStartTime());
            }
            if (query.getEndTime() != null) {
                criteria.andLessThan("startTime", query.getEndTime());
            }

            if (query.getToastClose() != null) {
                criteria.andEqualTo("toastClose", query.getToastClose());
            }

            if (limit != null) {
                PageHelper.startPage(0, limit);
            }

        }
        return example;
    }

    private Example createBaseExample(BaseEntity baseEntity, Class<SimpleExcelTask> clazz, String orderBy, Boolean asc) {
        Example example = new Example(clazz);
        if (orderBy != null) {
            asc = asc == null ? true : asc;
            if (asc) {
                example.orderBy(orderBy).asc();
            } else {
                example.orderBy(orderBy).desc();
            }
        }
        Example.Criteria criteria = example.createCriteria();
        // 逻辑删除
        criteria.andEqualTo("isDel", 0);
        if (baseEntity != null) {
            // id
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getId())) {
                criteria.andEqualTo("id", baseEntity.getId());
            }
            // 租户id
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getTenementId())) {
                criteria.andEqualTo("tenementId", baseEntity.getTenementId());
            }
            // 创建时间
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getCreateTime())) {
                criteria.andEqualTo("createTime", baseEntity.getCreateTime());
            }
            // 更新时间
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getUpdateTime())) {
                criteria.andEqualTo("updateTime", baseEntity.getUpdateTime());
            }
            // 创建人员id
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getCreateId())) {
                criteria.andEqualTo("createId", baseEntity.getCreateId());
            }
            // 更新人员id
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getUpdateId())) {
                criteria.andEqualTo("updateId", baseEntity.getUpdateId());
            }
            // 创建人员名字
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getCreateName())) {
                criteria.andLike("createName", "%" + baseEntity.getCreateName() + "%");
            }
            // 更新人员名字
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getUpdateName())) {
                criteria.andLike("updateName", "%" + baseEntity.getUpdateName() + "%");
            }
            // 是否已删除
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getIsDel())) {
                criteria.andEqualTo("isDel", baseEntity.getIsDel());
            }
            // 是否启用
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getIsEnable())) {
                criteria.andEqualTo("isEnable", baseEntity.getIsEnable());
            }
            // 版本
            if (!org.springframework.util.StringUtils.isEmpty(baseEntity.getVersion())) {
                criteria.andEqualTo("version", baseEntity.getVersion());
            }
        }
        return example;
    }
}
