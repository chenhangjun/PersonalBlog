package com.chen.blog.service;

import com.chen.blog.vo.Result;
import com.chen.blog.vo.params.CommentParams;

public interface CommentService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticle(Long id);

    Result comment(CommentParams commentParams);
}
