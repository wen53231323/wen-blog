package com.wen.controller;

import com.wen.service.ArticleService;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章相关接口
 */
@Api(tags = "文章", description = "文章相关接口")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "首页-文章列表", notes = "首页首页文章列表，若传入分类id则查询该分类下的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页面"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "categoryId", value = "文章分类id")
    })
    @GetMapping("/articleList/{pageNum}/{pageSize}")
    public ResponseResult articeList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize, Long categoryId) {
        ResponseResult result = articleService.articeList(pageNum, pageSize, categoryId);
        return result;
    }

    @ApiOperation(value = "首页-热门文章", notes = "查询浏览量最高的前10篇文章的信息，浏览量降序排列（由高到低）")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticeList() {
        ResponseResult result = articleService.hotArticeList();
        return result;
    }

    @ApiOperation(value = "文章详情", notes = "根据文章id查询文章详情")
    @GetMapping("/{id}")
    public ResponseResult getArticeDetail(@PathVariable("id") Long id) {
        ResponseResult result = articleService.getArticeDetail(id);
        return result;
    }

    @ApiOperation(value = "更新存储在redis中的文章浏览量", notes = "更新浏览量时，更新redis中的数据")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        ResponseResult result = articleService.updateViewCount(id);
        return result;
    }
}
