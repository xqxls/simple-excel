package com.xqxls.simpleexcel;


import com.xqxls.simpleexcel.common.ErrorMessage;
import com.xqxls.simpleexcel.common.base.ctx.SimpleExcelSupportContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @USER: com.xqxls
 * @DATE: 2023/4/10
 */
public class SimpleExcelContext extends SimpleExcelSupportContext {


    private static final ThreadLocal<SimpleExcelContext> LOCAL = ThreadLocal.withInitial(() -> new SimpleExcelContext());

    /**
     * 保留参数
     */
    private final Map<String, Object> values = new HashMap<String, Object>();


    /**
     * 错误消息收集器
     */
    private final List<ErrorMessage> errorCollector = new ArrayList<>();


    public static SimpleExcelContext getContext() {
        return LOCAL.get();
    }

    public static void setContext(SimpleExcelContext context) {
        LOCAL.set(context);
    }


    public static void removeContext() {
        LOCAL.remove();
    }

    /**
     * 设置单个值
     *
     * @param key
     * @param value
     * @return
     */
    public static SimpleExcelContext set(String key, Object value) {
        if (value == null) {
            SimpleExcelContext.getContext().values.remove(key);
        } else {
            SimpleExcelContext.getContext().values.put(key, value);
        }
        return SimpleExcelContext.getContext();
    }

    /**
     * 追加设置map
     *
     * @param values
     * @return
     */
    public static SimpleExcelContext set(Map<String, Object> values) {
        if (values != null) {
            SimpleExcelContext.getContext().values.putAll(values);
        }
        return SimpleExcelContext.getContext();
    }

    public static SimpleExcelContext remove(String key) {
        SimpleExcelContext.getContext().values.remove(key);
        return SimpleExcelContext.getContext();
    }


    public static Object get(String key) {
        return SimpleExcelContext.getContext().values.get(key);
    }


    public static Map<String, Object> get() {
        return SimpleExcelContext.getContext().values;
    }

    public static List<ErrorMessage> getErrorCollector() {
        return SimpleExcelContext.getContext().errorCollector;
    }



    /**
     * 添加错误信息
     *
     * @param rowIndex
     * @param msg
     */
    public static void addErrorMessage(Integer rowIndex, String msg) {
        addErrorMessage(ErrorMessage.error(rowIndex, msg));
    }

    /**
     * 添加错误信息
     *
     * @param message
     */
    public static void addErrorMessage(ErrorMessage message) {
        SimpleExcelContext context = SimpleExcelContext.getContext();
        context.errorCollector.add(message);
        context.onCollectErrorMessage(message);
    }

    /**
     * 记录单次处理
     */
    public static void recordProgress() {
        SimpleExcelContext context = SimpleExcelContext.getContext();
        context.onProgress();
    }

    /**
     * 任务是否成功
     * 如果收集到错误日志了，会返回false
     * @return
     */
    public static boolean taskSuccess() {
        SimpleExcelContext context = SimpleExcelContext.getContext();
        return true;
    }


}
