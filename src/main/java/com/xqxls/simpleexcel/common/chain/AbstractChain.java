package com.xqxls.simpleexcel.common.chain;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @USER: xqxls
 * @DATE: 2023/1/7
 */
@Slf4j
public abstract class AbstractChain<Context> implements Chain<Context> {


    /**
     * 通用处理器集合
     */
    protected List<GenericHandler<Context>> genericHandlerList = new ArrayList<>();


    @Override
    public void execute(Context context) {
        for (GenericHandler<Context> genericHandler : genericHandlerList) {
            if (!genericHandler.isSupport(context)) {
                continue;
            }

            try {
                // 停止往后传递
                ChainProcessResult complete = genericHandler.doHandler(context);
                if (ChainProcessResult.ABORT.equals(complete)) {
                    break;
                }
            } catch (Exception e) {
                log.error("【{}】链路执行【{}】处理异常", this.getClass().getSimpleName(), genericHandler.getClass().getSimpleName(), e);
                throw e;
            }
        }
    }

    /**
     * 责任链初始化
     */
    @PostConstruct
    public abstract void init();

}
