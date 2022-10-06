package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.TagMapper;
import com.wen.pojo.constants.SystemConstants;
import com.wen.pojo.entity.Category;
import com.wen.pojo.entity.Tag;
import com.wen.pojo.vo.ArticleDetailVo;
import com.wen.pojo.vo.TagVo;
import com.wen.service.TagService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-06 15:49:24
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult getListAllTag() {
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Tag::getStatus, SystemConstants.STATUS_NORMAL);// 分类状态必须为正常的
        lqw.select(Tag::getId,Tag::getName);// 只查询标签ID和标签名称
        List<Tag> tags = tagMapper.selectList(lqw);
        // -----------------------------使用工具类BeanCopyUtils封装到VO-----------------------------
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

