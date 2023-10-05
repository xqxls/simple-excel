package com.xqxls.simpleexcel.feign;

import com.xqxls.common.CommonResult;
import com.xqxls.dto.MinioUploadDto;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @USER: xqxls
 * @DATE: 2023/5/12
 */
public interface FileInfoControllerFeign {

    MinioUploadDto upload(@RequestPart("file") MultipartFile file) throws IOException;
}
