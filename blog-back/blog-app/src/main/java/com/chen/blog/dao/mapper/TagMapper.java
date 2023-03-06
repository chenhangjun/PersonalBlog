package com.chen.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签 前 n 条
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
