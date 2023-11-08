package com.xqxls.controller;

import com.xqxls.common.CommonPage;
import com.xqxls.common.CommonResult;
import com.xqxls.dto.UmsAdminExportDTO;
import com.xqxls.dto.UmsAdminParam;
import com.xqxls.model.UmsAdmin;
import com.xqxls.service.UmsAdminService;
import com.xqxls.simpleexcel.SimpleExcelUtils;
import com.xqxls.simpleexcel.exporter.req.SimpleExcelExportReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 后台用户管理Controller
 * Created by com.xqxls on 2018/4/26.
 */
@Controller
@RequestMapping("/admin")
public class UmsAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminController.class);

    @Autowired
    private UmsAdminService adminService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public void export(HttpServletResponse response) {
        SimpleExcelExportReq req = SimpleExcelExportReq
                .builder()
                .filename("用户表")
                .fetchDataClass(UmsAdminExportDTO.class)
                .fetchData(() -> adminService.findExportData())
                .build();
        SimpleExcelUtils.doExport(req);
    }

    @RequestMapping(value = "/asyncExport", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Long> asyncExport(HttpServletResponse response) {
        SimpleExcelExportReq req = SimpleExcelExportReq
                .builder()
                .accessTaskCenter(true)
                .pageId("com/xqxls")
                .filename("用户表")
                .fetchDataClass(UmsAdminExportDTO.class)
                .fetchData(() -> adminService.findExportData())
                .build();
        Long taskId = SimpleExcelUtils.doExport(req).getTaskId();
        return CommonResult.success(taskId);
    }

}
