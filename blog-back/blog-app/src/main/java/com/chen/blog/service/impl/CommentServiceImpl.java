package com.chen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.blog.dao.mapper.ArticleMapper;
import com.chen.blog.dao.mapper.CommentMapper;
import com.chen.blog.dao.pojo.Article;
import com.chen.blog.dao.pojo.Comment;
import com.chen.blog.dao.pojo.SysUser;
import com.chen.blog.service.CommentService;
import com.chen.blog.service.SysUserService;
import com.chen.blog.utils.UserThreadLocal;
import com.chen.blog.vo.CommentVo;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.UserVo;
import com.chen.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticle(Long id) {
        /**
         * 1. 根据文章id查询评论列表 comment表
         * 2. 根据作者id 查询作者信息
         * 3. 判断如果level = 1， 要去查询它是否有子评论
         * 4. 如果有 根据评论id 进行查询 parent_id
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParams commentParams) {

        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(2L);
        }

        Comment comment = new Comment();
        Long articleId = commentParams.getArticleId();
        comment.setArticleId(articleId);
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        // 增加评论数量的功能考虑放入到线程池中
        Article article = articleMapper.selectById(articleId);
        article.setCommentCounts(article.getCommentCounts() + 1);
        articleMapper.updateById(article);
        CommentVo commentVo = copy(comment);
        return Result.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        // 作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        // 子评论
        Integer level = comment.getLevel();
        if (level == 1) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildren(commentVoList);
        }
        // to User 子评论的父级
        if(level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
