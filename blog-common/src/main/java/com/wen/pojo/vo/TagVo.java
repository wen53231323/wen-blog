package com.wen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    //标签ID
    private Long id;
    //标签名称
    private String name;
}
