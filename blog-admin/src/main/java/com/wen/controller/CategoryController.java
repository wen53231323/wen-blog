package com.wen.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.wen.pojo.entity.Category;
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

    // 发布文章时获取分类列表
    @GetMapping("/category/listAllCategory")
    public ResponseResult getListAllCategory() {
        ResponseResult allCategory = categoryService.getAllCategory();
        return allCategory;
    }

    // 根据id修改分类
    @PutMapping("/category")
    public ResponseResult edit(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    // 根据id删除分类
    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id") Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    // 根据id获取分类信息
    @GetMapping(value = "/category/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    // 添加分类信息
    @PostMapping("/category")
    public ResponseResult add(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/category/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }



    @ApiOperation(value = "导出Excel", notes = "导出分类到Excel")
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
