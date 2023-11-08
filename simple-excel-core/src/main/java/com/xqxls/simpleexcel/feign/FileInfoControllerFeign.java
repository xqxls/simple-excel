package com.xqxls.simpleexcel.feign;

import com.xqxls.simpleexcel.feign.dto.MinioUploadDto;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @USER: com.xqxls
 * @DATE: 2023/5/12
 */
public interface FileInfoControllerFeign {

    MinioUploadDto upload(@RequestPart("file") MultipartFile file) throws IOException;
}
