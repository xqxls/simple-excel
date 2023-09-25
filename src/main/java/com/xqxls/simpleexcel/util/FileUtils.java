package com.xqxls.simpleexcel.util;

import cn.hutool.core.util.IdUtil;
import lombok.SneakyThrows;
import sun.security.action.GetPropertyAction;

import java.io.File;
import java.security.AccessController;


/**
 * @USER: xqxls
 * @DATE: 2023/4/26
 */
public class FileUtils {


    /**
     * 临时文件夹
     */
    private static final File tmpdir = new File(AccessController
            .doPrivileged(new GetPropertyAction("java.io.tmpdir")));


    @SneakyThrows
    public static File buildFileTemporaryFileName(String fileName) {
        //文件生成随机的
        StringBuffer sb = new StringBuffer();
        sb.append(IdUtil.simpleUUID());
        sb.append(".xlsx");

        //临时文件只能保证jvm正常退出时会被删除
        File tempFile = new File(tmpdir, sb.toString());
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        //保证异常退出被删除
        tempFile.deleteOnExit();
        return tempFile;
    }


}
