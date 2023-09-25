package com.xqxls.simpleexcel.common.base.handler;

import com.xqxls.simpleexcel.common.chain.GenericHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @USER: xqxls
 * @DATE: 2023/5/24
 */
@Slf4j
public abstract class SimpleExcelParamCheckBaseHandler<T> implements GenericHandler<T>, ApplicationContextAware {


    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
