package com.wen.controller;

import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.service.RoleService;
import com.wen.service.UserService;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import com.wen.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content")
public class UserController {
    @ApiOperation(value = "导出Excel", notes = "导出用户信息到Excel")
    @GetMapping("/user/export")
    public void excelExport(HttpServletResponse response) {

        try {
            // 设置下载文件的请求头

            // 获取需要导出的数据

            // 把数据写入到Excel中
            WebUtils.setDownLoadHeader("用户.xlsx",response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 如果出现异常响应Json提示
        }


    }

}
