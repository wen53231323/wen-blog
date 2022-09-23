package com.wen.controller;


import com.wen.service.CategoryService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.RequestMatchResult;

import javax.annotation.Resource;

/**
 * 文章分类相关接口
 */
@Api(tags = "文章分类", description = "文章分类相关接口")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "文章分类", notes = "获取文章分类列表（首页文章分类展示）")
    @GetMapping("/getCategory")
    public ResponseResult getCategoryList(){
        ResponseResult  result = categoryService.getCategoryList();
        return result;
    }

}

