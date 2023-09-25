package com.xqxls.simpleexcel.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户信息
 * Created by xushuaihu on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {
    private Long id;
    private Long tenementId;
    private String username;
    private Integer status;
    private String clientId;
    private List<String> authorities;
    private Integer type; // 用户类型 0超管 1管理员 2 实施 3 员工

}
