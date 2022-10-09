package com.wen.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.wen.pojo.entity.Category;
import com.wen.pojo.entity.User;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.pojo.vo.CategoryVo;
import com.wen.pojo.vo.ExcelCategoryVo;
import com.wen.pojo.vo.PageVo;
import com.wen.service.CategoryService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分类", notes = "发布文章时获取所有分类列表")
    @GetMapping("/category/listAllCategory")
    @PreAuthorize("@ps.hasPermission('content:category:query')")// 在SPEL表达式中使用 @ex相当于获取容器中bean的名字的对象，然后再调用这个对象的hasAuthority方法
    public ResponseResult getListAllCategory() {
        ResponseResult allCategory = categoryService.getAllCategory();
        return allCategory;
    }

    @ApiOperation(value = "分类列表", notes = "获取分类列表")
    @GetMapping("/category/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @ApiOperation(value = "添加分类", notes = "添加分类信息")
    @PostMapping("/category")
    public ResponseResult add(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "删除分类", notes = "根据id删除分类")
    @DeleteMapping("/category/{id}")
    public ResponseResult remove(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "分类详情", notes = "根据id获取分类详情信息")
    @GetMapping("/category/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @ApiOperation(value = "分类修改", notes = "根据id修改分类")
    @PutMapping("/category")
    public ResponseResult edit(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "分类状态修改", notes = "分类状态修改")
    @PutMapping("/category/changeStatus")
    public ResponseResult changeStatus(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "导出Excel", notes = "导出分类到Excel")
    // 在SPEL表达式中使用 @ex相当于获取容器中bean的名字的对象，然后再调用这个对象的hasAuthority方法
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/category/export")
    public void export(HttpServletResponse response) {
        try {
            //（1）设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);

            //（2）获取需要导出的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);

            //（3）把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出").doWrite(excelCategoryVos);

        } catch (Exception e) {
            //（4）如果出现异常要响应json提示
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
