package com.chen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.blog.dao.mapper.ArticleMapper;
import com.chen.blog.dao.mapper.CategoryMapper;
import com.chen.blog.dao.mapper.TagMapper;
import com.chen.blog.dao.pojo.Article;
import com.chen.blog.dao.pojo.Category;
import com.chen.blog.dao.pojo.Tag;
import com.chen.blog.service.StatisticService;
import com.chen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticServcieImpl implements StatisticService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result getNumbers() {
        LambdaQueryWrapper<Article> articleQuery = new LambdaQueryWrapper<>();
        Integer article = articleMapper.selectCount(articleQuery);
        LambdaQueryWrapper<Category> categoryQuery = new LambdaQueryWrapper<>();
        Integer category = categoryMapper.selectCount(categoryQuery);
        LambdaQueryWrapper<Tag> tagQuery = new LambdaQueryWrapper<>();
        Integer tag = tagMapper.selectCount(tagQuery);

        Map<String, Integer> map = new HashMap<>();
        map.put("article", article);
        map.put("category", category);
        map.put("tag", tag);

        return Result.success(map);
    }
}
