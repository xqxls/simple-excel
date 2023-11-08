package com.xqxls.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xqxls.controller.co.SimpleExcelTaskSearchCO;
import com.xqxls.controller.vo.SimpleExcelErrorMessageVO;
import com.xqxls.controller.vo.SimpleExcelTaskVO;
import com.xqxls.service.SimpleExcelTaskService;
import com.xqxls.simpleexcel.common.ErrorMessage;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import com.xqxls.simpleexcel.persist.po.SimpleExcelTask;
import com.xqxls.simpleexcel.persist.qo.SimpleExcelTaskQo;
import com.xqxls.simpleexcel.persist.repository.SimpleExcelTaskRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/10/15 19:13
 */
@Service
public class SimpleExcelTaskServiceImpl implements SimpleExcelTaskService {

    @Resource
    private SimpleExcelTaskRepository simpleExcelTaskRepository;

    @Override
    public List<SimpleExcelTaskVO> findList(SimpleExcelTaskSearchCO simpleExcelTaskSearchCO) {
        SimpleExcelTaskQo simpleExcelTask = new SimpleExcelTaskQo();
        BeanUtils.copyProperties(simpleExcelTaskSearchCO, simpleExcelTask);

        List<SimpleExcelTask> resultList = simpleExcelTaskRepository.findList(simpleExcelTask, simpleExcelTaskSearchCO.getOrderBy(), simpleExcelTaskSearchCO.getAsc(), simpleExcelTaskSearchCO.getLimit());
        return toVo(resultList);
    }

    @Override
    public void toastClose(Long id) {
        SimpleExcelTask simpleExcelTask = new SimpleExcelTask();
        simpleExcelTask.setId(id);
        simpleExcelTask.setUpdateTime(LocalDateTime.now());
        simpleExcelTask.setToastClose(SimpleExcelConstant.Task.ToastClose.CLOSE);
        simpleExcelTaskRepository.updateById(simpleExcelTask);
    }

    private List<SimpleExcelTaskVO> toVo(List<SimpleExcelTask> poList) {
        return poList
                .stream()
                .map(po -> {
                    SimpleExcelTaskVO vo = new SimpleExcelTaskVO();
                    BeanUtils.copyProperties(po, vo);
                    Optional
                            .ofNullable(vo.getFailedMessage())
                            .filter(StringUtils::isNotEmpty)
                            .map(fm -> {
                                List<ErrorMessage> errorMessages = JSONArray.parseArray(fm, ErrorMessage.class);
                                return errorMessages
                                        .stream()
                                        .map(errorMessage -> {
                                            SimpleExcelErrorMessageVO simpleExcelErrorMessageVO = new SimpleExcelErrorMessageVO();
                                            simpleExcelErrorMessageVO.setRow(errorMessage.getRowIndexStr());
                                            simpleExcelErrorMessageVO.setMsg(errorMessage.getMsg());
                                            return simpleExcelErrorMessageVO;
                                        }).collect(Collectors.toList());
                            })
                            .ifPresent(vo::setFailedMessageList);
                    return vo;
                })
                .collect(Collectors.toList());

    }



}
