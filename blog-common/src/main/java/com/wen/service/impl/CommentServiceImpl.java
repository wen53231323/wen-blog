package com.wen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.exception.SystemException;
import com.wen.mapper.CommentMapper;
import com.wen.mapper.ReplyMapper;
import com.wen.mapper.UserMapper;
import com.wen.pojo.dto.CommentDTO;
import com.wen.pojo.entity.Comment;
import com.wen.pojo.entity.Reply;
import com.wen.pojo.entity.User;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.pojo.vo.CommentVo;
import com.wen.pojo.vo.PageVo;
import com.wen.pojo.vo.ReplyVo;
import com.wen.service.CommentService;
import com.wen.utils.BeanCopyUtils;
import com.wen.utils.ResponseResult;
import com.wen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (Comment)表服务实现类
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    /**
     * 根据一级评论分页，一级评论按点赞降序（由高到低），二级回复按时间升序（由旧到新）
     *
     * @param articleId 文章id
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     */
    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        // TODO 根据文章id，查询对应文章的一级评论（评论表），并按点赞数据降序分页
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getBelongId, articleId);
        lqw.orderByDesc(Comment::getLikeNum);// 按照点赞量降序排序（由高到低）
        Page<Comment> page = new Page<>(pageNum, pageSize);// 分页查询
        commentMapper.selectPage(page, lqw);// 添加查询条件
        List<Comment> records = page.getRecords();// 获取全部数据列表

        // TODO 使用工具类BeanCopyUtils封装一级评论和二级评论 的分页对象
        // 使用bean拷贝工具类
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(records, CommentVo.class);

        // TODO 处理父评论，即文章一级回复
        // 遍历vo集合，对用户昵称进行赋值
        for (CommentVo commentVo : commentVoList) {
            Long createBy = commentVo.getCreateBy(); // 父评论发表者id
            User user = userMapper.selectById(createBy);
            String nickName = user.getNickName(); // 父评论用户昵称
            commentVo.setNickName(nickName);
        }

        // TODO 处理一级评论子级的回复，即一级评论内的回复
        for (CommentVo commentVo : commentVoList) {
            Long commentId = commentVo.getId(); // 父评论id

            // TODO  获取封装好的vo对象
            List<ReplyVo> replyVos = geRreplyList(commentId);

            // TODO 给父回复中的子回复列表赋值
            commentVo.setReply(replyVos);
        }

        // TODO 将数据与总记录数封装到分页VO对象返回
        PageVo pageVo = new PageVo(commentVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 根据一级评论id获取所有二级评论回复列表并封装成vo对象
     *
     * @param commentId 一级评论id
     */
    public List<ReplyVo> geRreplyList(Long commentId) {
        // TODO 根据一级评论id获取所有二级评论回复列表
        // 根据条件查询出子回复所有内容
        LambdaQueryWrapper<Reply> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Reply::getCommentId, commentId); // 比较评论id
        lqw2.orderByAsc(Reply::getCreateTime); // 升序排序（由旧到新）
        List<Reply> replies = replyMapper.selectList(lqw2);

        // TODO 使用工具类BeanCopyUtils封装父评论中的子评论
        List<ReplyVo> replyVos = BeanCopyUtils.copyBeanList(replies, ReplyVo.class);// 使用bean拷贝工具类
        // 遍历vo集合，对用户昵称进行赋值
        for (ReplyVo replyVo : replyVos) {
            Long createBy1 = replyVo.getCreateBy();// 子评论用户id（谁回复的）
            Long toUserId = replyVo.getToUserId();// 子评论回复的目标id（给哪个用户回复）
            User user = userMapper.selectById(createBy1);
            User user1 = userMapper.selectById(toUserId);
            String nickName = user.getNickName(); // 子评论用户昵称（谁回复的）
            String nickName1 = user1.getNickName();// 子评论回复的目标昵称（给哪个用户回复）
            replyVo.setNickName(nickName);
            replyVo.setParentNickName(nickName1);
        }
        return replyVos;
    }


    /**
     * 处理发布一级评论和二级回复
     */
    @Override
    public ResponseResult addComment(CommentDTO commentDTO) {
        // TODO 使用工具类从token获取评论的用户id
        Long userId = SecurityUtils.getUserId();

        // 如果评论内容为空给出提示
        if (!StringUtils.hasText(commentDTO.getContent())) {
            // 还要加上判断 ，请求头必须包含token
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

        // TODO 如果commentId或toUserId为null，则说明是一级评论，反之是回复
        if (Objects.isNull(commentDTO.getCommentId()) | Objects.isNull(commentDTO.getToUserId())) {
            // 向评论表插入，文章id和评论内容
            commentMapper.commentInsert(commentDTO.getArticleId(), commentDTO.getContent(), userId, userId);
        } else {
            // 向评论回复表插入，一级评论id、回复的目标id（给哪个用户回复）、回复内容
            replyMapper.replyInsert(commentDTO.getCommentId(), commentDTO.getToUserId(), commentDTO.getContent(), userId, userId);
        }
        return ResponseResult.okResult();
    }


}

