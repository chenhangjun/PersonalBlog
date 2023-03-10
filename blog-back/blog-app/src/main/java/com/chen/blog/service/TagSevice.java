package com.chen.blog.service;

import com.chen.blog.vo.Result;
import com.chen.blog.vo.TagVo;

import java.util.List;

public interface TagSevice {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
