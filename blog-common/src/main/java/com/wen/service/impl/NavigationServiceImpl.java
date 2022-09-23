package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.pojo.constants.SystemConstants;
import com.wen.mapper.NavigationMapper;
import com.wen.pojo.entity.Navigation;
import com.wen.pojo.vo.NavigationVo;
import com.wen.service.NavigationService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Navigation)表服务实现类
 *
 * @author makejava
 * @since 2022-08-30 12:06:34
 */
@Service("navigationService")
public class NavigationServiceImpl extends ServiceImpl<NavigationMapper, Navigation> implements NavigationService {

    @Autowired
    private NavigationMapper navigationMapper;

    @Override
    public ResponseResult getAllNavigation() {
        // QueryWrapper对象：用于封装查询条件的对象，该对象可以动态使用API调用的方法添加条件，最终转化成对应的SQL语句。
        QueryWrapper<Navigation> qw = new QueryWrapper<>();
        // 必须是正式链接才显示
        qw.lambda().eq(Navigation::getStatus, SystemConstants.STATUS_NORMAL);
        // 查询文章列表
        List<Navigation> navigations = navigationMapper.selectList(qw);

        // -----------------------------使用工具类BeanCopyUtils封装VO-----------------------------
        List<NavigationVo> navigationVos = BeanCopyUtils.copyBeanList(navigations, NavigationVo.class);
        return ResponseResult.okResult(navigationVos);
    }
}

