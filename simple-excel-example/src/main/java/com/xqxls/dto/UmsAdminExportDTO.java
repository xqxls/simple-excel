package com.xqxls.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/9/25 21:27
 */
@Data
public class UmsAdminExportDTO {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "密码")
    private String password;

    @ExcelProperty(value = "头像")
    private String icon;

    @ExcelProperty(value = "邮件")
    private String email;

    @ExcelProperty(value = "昵称")
    private String nickName;

    @ExcelProperty(value = "备注")
    private String note;
}
