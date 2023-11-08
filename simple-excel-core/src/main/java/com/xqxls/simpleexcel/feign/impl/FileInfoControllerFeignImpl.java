package com.xqxls.simpleexcel.feign.impl;

import com.alibaba.fastjson.JSON;
import com.xqxls.common.CommonResult;
import com.xqxls.simpleexcel.feign.FileInfoControllerFeign;
import com.xqxls.simpleexcel.feign.dto.MinioUploadDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/10/5 22:03
 */
@Service
public class FileInfoControllerFeignImpl implements FileInfoControllerFeign {

    @Override
    public MinioUploadDto upload(@RequestPart("file") MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8090/minio/upload";
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        InputStreamResource inputStreamResource = new InputStreamResource(file.getInputStream()){
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
            @Override
            public long contentLength() {
                return file.getSize();
            }
        };
        params.add("file", inputStreamResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String,Object>> requestEntity  = new HttpEntity<>(params, headers);
        Object obj = restTemplate.postForObject(url,requestEntity , CommonResult.class).getData();
        return JSON.parseObject(JSON.toJSONString(obj),MinioUploadDto.class);
    }
}
