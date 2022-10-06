package com.wen.controller;

import com.wen.service.UploadService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "上传", description = "上传文件相关接口")
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "图片上传", notes = "上传图片至七牛云")
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        ResponseResult result = uploadService.uploadImg(img);
        return result;
    }

}
