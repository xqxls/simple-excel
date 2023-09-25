package com.xqxls.simpleexcel.feign;

import com.xqxls.common.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


/**
 * @USER: xqxls
 * @DATE: 2023/5/12
 */
//@FeignClient(name = "mes-file-service")
public interface FileInfoControllerFeign {

    @PostMapping(
            value = {"/fileInfo/commonUpload"},
            consumes = {"multipart/form-data"}
    )
    CommonResult<FileDTO> commonUpload(@RequestParam("group") String var1, @RequestPart("file") MultipartFile var2);
}
