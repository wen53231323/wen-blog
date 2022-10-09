package com.wen.controller;

import com.wen.pojo.dto.AddTagDTO;
import com.wen.pojo.dto.EditTagDTO;
import com.wen.pojo.dto.TagListDTO;
import com.wen.pojo.entity.Tag;
import com.wen.pojo.vo.PageVo;
import com.wen.pojo.vo.TagVo;
import com.wen.service.TagService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation(value = "标签查询", notes = "发布文章时查询所有标签")
    @GetMapping("/tag/listAllTag")
    public ResponseResult getListAllTag() {
        ResponseResult categoryList = tagService.getListAllTag();
        return categoryList;
    }

    @ApiOperation(value = "标签列表", notes = "标签列表")
    @GetMapping("/tag/list")
    public ResponseResult getTagPage(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        ResponseResult categoryList = tagService.pageTagList(pageNum, pageSize, tagListDTO);
        return categoryList;
    }

    @ApiOperation(value = "新增标签", notes = "新增标签")
    @PostMapping("/tag")
    public ResponseResult add(@RequestBody AddTagDTO tagDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagDTO, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "删除标签", notes = "删除标签")
    @DeleteMapping("/tag/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @PutMapping("/tag")
    public ResponseResult edit(@RequestBody EditTagDTO tagDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagDTO, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "标签详情", notes = "标签详情")
    @GetMapping("/tag/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

}
