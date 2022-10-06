package com.wen.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mapper.ArticleTagMapper;
import com.wen.pojo.entity.ArticleTag;
import com.wen.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * (ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-06 16:59:19
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

