package com.wen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class NavigationVo {
    //网站id
    private Long id;
    //网站图标
    private String logo;
    //网站名称
    private String name;
    //网站描述
    private String description;
    //网站地址
    private String address;
    //网站类别
    private String category;
}
