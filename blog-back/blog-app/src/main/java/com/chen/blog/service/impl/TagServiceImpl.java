package com.chen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.blog.dao.mapper.TagMapper;
import com.chen.blog.dao.pojo.Tag;
import com.chen.blog.service.TagSevice;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagSevice {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // mybatisPlus 无法进行多表查询， 自己实现
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1. 最热标签：标签所拥有的文章数量最多
         * 2. 查询 根据 tag_id 分组(group by) 计数(count) 排列(order by desc) 取前 limit 个
         */
        List<Long> tagIds = tagMapper.findHotTagIds(limit);
        // sql中参数tagIds不能为空，判空处理
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
}
