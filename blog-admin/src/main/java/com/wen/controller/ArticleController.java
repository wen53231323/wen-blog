package com.wen.controller;

import com.wen.pojo.dto.ArticleDTO;
import com.wen.pojo.entity.Article;
import com.wen.pojo.vo.AdminUserInfoVo;
import com.wen.service.ArticleService;
import com.wen.service.CategoryService;
import com.wen.service.TagService;
import com.wen.service.UploadService;
import com.wen.utils.ResponseResult;
import com.wen.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/content")
public class ArticleController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/listAllTag")
    public ResponseResult getListAllTag() {
        ResponseResult categoryList =tagService.getListAllTag();
        return categoryList;
    }

    @ApiOperation(value = "图片上传", notes = "上传图片至七牛云")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile img) {
        ResponseResult result = uploadService.uploadImg(img);
        return result;
    }

    @PostMapping("/article")
    public ResponseResult addArticle(@RequestBody ArticleDTO articleDTO) {
        ResponseResult result = articleService.addArticle(articleDTO);
        return result;
    }

}
