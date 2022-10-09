package com.wen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTagDTO {
    //备注
    private String remark;
    //标签名
    private String name;
}
