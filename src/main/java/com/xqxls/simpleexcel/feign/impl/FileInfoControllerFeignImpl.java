package com.xqxls.simpleexcel.feign.impl;

import com.xqxls.common.CommonResult;
import com.xqxls.simpleexcel.feign.FileDTO;
import com.xqxls.simpleexcel.feign.FileInfoControllerFeign;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/9/25 0:29
 */
@Service
public class FileInfoControllerFeignImpl implements FileInfoControllerFeign {

    @Override
    public CommonResult<FileDTO> commonUpload(String var1, MultipartFile var2) {
        return null;
    }
}
