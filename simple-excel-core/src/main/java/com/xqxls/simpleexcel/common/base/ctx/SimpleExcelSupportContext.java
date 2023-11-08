package com.xqxls.simpleexcel.common.base.ctx;

import com.xqxls.simpleexcel.common.ErrorMessage;
import lombok.Data;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/11
 * 主要放一下组件相关的业务逻辑
 * 和SimpleExcelContext用户逻辑隔离
 */
@Data
public class SimpleExcelSupportContext {



    protected void onCollectErrorMessage(ErrorMessage message) {
        //如果收到错误信息，代表已经失败了
    }


    protected void onProgress() {

    }


}
