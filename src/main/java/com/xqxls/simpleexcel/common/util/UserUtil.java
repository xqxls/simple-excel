package com.xqxls.simpleexcel.common.util;

import com.xqxls.simpleexcel.common.domain.UserDto;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/9/24 23:40
 */
public class UserUtil {

    public static UserDto getCurrentUser(){
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("test");
        userDto.setTenementId(1130021L);
        return userDto;
    }
}
