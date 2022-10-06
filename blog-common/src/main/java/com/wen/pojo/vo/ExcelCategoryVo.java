package com.wen.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategoryVo {
    // @ExcelProperty用于匹配excel和实体类的匹配
    @ExcelProperty("分类名")
    private String name;

    // @ExcelProperty用于匹配excel和实体类的匹配
    @ExcelProperty("描述")
    private String description;

    // @ExcelProperty用于匹配excel和实体类的匹配
    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
