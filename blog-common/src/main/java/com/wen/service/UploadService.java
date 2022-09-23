package com.wen.service;

import com.wen.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    // 上传图片至七牛云
    ResponseResult uploadImg(MultipartFile img);
}
