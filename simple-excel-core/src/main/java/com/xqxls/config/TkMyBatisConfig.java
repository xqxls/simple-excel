package com.xqxls.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/10/5 22:37
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.xqxls.simpleexcel.persist.dao"})
public class TkMyBatisConfig {

}