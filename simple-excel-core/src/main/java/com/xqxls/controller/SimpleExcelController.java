package com.xqxls.controller;

import com.xqxls.common.CommonResult;
import com.xqxls.controller.co.SimpleExcelTaskSearchCO;
import com.xqxls.controller.co.SimpleExcelTaskToastCloseCo;
import com.xqxls.controller.vo.SimpleExcelTaskVO;
import com.xqxls.exception.ApiException;
import com.xqxls.service.SimpleExcelTaskService;
import com.xqxls.simpleexcel.common.constant.SimpleExcelConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/10/15 19:05
 */
@RestController
@Api(tags = "simple-excel任务接口")
@RequestMapping("/simpleExcel/task/")
public class SimpleExcelController {

    @Resource
    private SimpleExcelTaskService simpleExcelTaskService;

    /**
     *
     *
     * @param simpleExcelTaskSearchCO
     * @return
     */
    @ApiOperation(value = "条件获取单条任务")
    @PostMapping("/findOne")
    public CommonResult<SimpleExcelTaskVO> findOne(@RequestBody SimpleExcelTaskSearchCO simpleExcelTaskSearchCO) {
        if(simpleExcelTaskSearchCO.getId()==null){
            throw new ApiException("任务ID不能为空");
        }
        simpleExcelTaskSearchCO.setOrderBy("createTime");
        simpleExcelTaskSearchCO.setAsc(false);

        SimpleExcelTaskVO simpleExcelTaskVO = Optional
                .ofNullable(simpleExcelTaskService.findList(simpleExcelTaskSearchCO))
                .filter(CollectionUtils::isNotEmpty)
                .map(list -> list.get(0))
                .orElse(null);

        return CommonResult.success(simpleExcelTaskVO);
    }


    @ApiOperation(value = "获取轮询调用间隔(秒)")
    @PostMapping("/fetchCallInterval")
    public CommonResult<Integer> fetchCallInterval() {
        return CommonResult.success(SimpleExcelConstant.TASK_STORAGE_INTERVAL);
    }


    @ApiOperation(value = "关闭弹窗")
    @PostMapping("/toastClose")
    public CommonResult toastClose(@RequestBody SimpleExcelTaskToastCloseCo request) {
        if (request.getId() == null) {
            throw new ApiException("传入的认为id不能为空");
        }
        simpleExcelTaskService.toastClose(request.getId());
        return CommonResult.success(null);
    }



}
