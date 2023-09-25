package com.xqxls.service;

import com.xqxls.dto.UmsAdminParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/9/24 22:48
 */
@SpringBootTest
class UmsAdminServiceTest {

    @Autowired
    private UmsAdminService umsAdminService;

    @Test
    void add(){
        for(int i=1;i<=500;i++){
            UmsAdminParam param = new UmsAdminParam();
            param.setUsername("user"+i*2);
            param.setPassword("123456");
            param.setNickName("小明"+i);
            umsAdminService.register(param);
        }


    }
}