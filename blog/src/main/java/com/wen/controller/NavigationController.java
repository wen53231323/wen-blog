package com.wen.controller;


import com.wen.service.CategoryService;
import com.wen.service.NavigationService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "导航", description = "网站导航接口")
@RestController
@RequestMapping("/navigation")
public class NavigationController {
    @Resource
    private NavigationService navigationService;

    @ApiOperation(value = "导航展示", notes = "获取所有导航信息")
    @GetMapping("/getAllNavigation")
    public ResponseResult getCategoryList(){
        ResponseResult  result = navigationService.getAllNavigation();
        return result;
    }
}

