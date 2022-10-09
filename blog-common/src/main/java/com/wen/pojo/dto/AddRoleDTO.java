package com.wen.pojo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddRoleDTO {
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //角色显示顺序
    private Integer roleSort;
    //角色备注
    private String remark;
    //权限id
    private Long[] menuIds;
}
