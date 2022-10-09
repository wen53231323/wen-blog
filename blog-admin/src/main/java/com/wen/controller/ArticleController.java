package com.wen.controller;

import com.wen.pojo.dto.ArticleDTO;
import com.wen.pojo.entity.Article;
import com.wen.pojo.vo.AdminUserInfoVo;
import com.wen.service.ArticleService;
import com.wen.service.CategoryService;
import com.wen.service.TagService;
import com.wen.service.UploadService;
import com.wen.utils.ResponseResult;
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

    @ApiOperation(value = "图片上传", notes = "上传图片至七牛云")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile img) {
        ResponseResult result = uploadService.uploadImg(img);
        return result;
    }

    @ApiOperation(value = "发布文章", notes = "发布文章接口")
    @PostMapping("/article")
    public ResponseResult addArticle(@RequestBody ArticleDTO articleDTO) {
        ResponseResult result = articleService.addArticle(articleDTO);
        return result;
    }


    /**
     * 分页查询文章列表，根据标题和摘要模糊查询
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param title    文章标题
     * @param summary  文章摘要
     */
    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表，根据标题和摘要模糊查询")
    @GetMapping("/article/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        ResponseResult result = articleService.selectArticlePage(pageNum, pageSize, title, summary);
        return result;
    }

    /**
     * 根据文章id查询文章详情
     */
    @ApiOperation(value = "文章详情", notes = "根据文章id查询文章详情")
    @GetMapping("/article/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        ResponseResult result = articleService.getInfo(id);
        return result;
    }

    /**
     * 修改文章
     */
    @ApiOperation(value = "修改文章", notes = "修改文章")
    @PutMapping("/article")
    public ResponseResult updateArticleById(@RequestBody ArticleDTO articleDTO) {
        ResponseResult result = articleService.updateArticleById(articleDTO);
        return result;
    }

    /**
     * 根据文章id删除文章
     */
    @ApiOperation(value = "文章删除", notes = "根据文章ID删除文章")
    @DeleteMapping("/article/{id}")
    public ResponseResult deleteByArticleId(@PathVariable("id") Long id) {
        articleService.removeById(id); // 删除文章
        return ResponseResult.okResult();
    }


}
