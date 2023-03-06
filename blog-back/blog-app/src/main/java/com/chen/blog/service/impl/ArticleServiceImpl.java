package com.chen.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.blog.dao.dos.Archives;
import com.chen.blog.dao.mapper.ArticleBodyMapper;
import com.chen.blog.dao.mapper.ArticleMapper;
import com.chen.blog.dao.mapper.ArticleTagMapper;
import com.chen.blog.dao.pojo.Article;
import com.chen.blog.dao.pojo.ArticleBody;
import com.chen.blog.dao.pojo.ArticleTag;
import com.chen.blog.dao.pojo.SysUser;
import com.chen.blog.service.*;
import com.chen.blog.utils.UserThreadLocal;
import com.chen.blog.vo.*;
import com.chen.blog.vo.params.ArticleParam;
import com.chen.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagSevice tagSevice;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ThreadService threadService;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));

    }


//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1. 分页查询数据库表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (!articleIdList.isEmpty()) {
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//
//        }
//        // 先按置顶进行排序
//        // order by create_date desc
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
////        List<Article> records = articleMapper.selectList(queryWrapper);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        System.out.println("****" + records.size());
//
//        List<ArticleVo> articleVoList = copyList(records, true, true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id, title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        // select id, title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId 和 categoryId 去做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        /**
         * 查看文章时，应该新增阅读数
         * 在返回文章的同时，做更新操作 == 更新是数据库会加写锁，阻塞其他的读操作，性能会比较低
         * 所以更新阅读数会增加此次接口的耗时 如果更新过程出问题也不能影响查看文章的操作
         * 线程池 == 可以把更新操作放到线程池中去执行 和主线程不相关
         */
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        // 此接口需要加入到登录拦截中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 -- 构建 Article 对象
         * 2. 作者Id -- 当前登录用户
         * 3. 标签 要将标签加入到关联列表中
         * 4. body 内容存储
         */

        // 数据库的summary格式为varchar(255) 最多可以存储255个字符
        // 取markdown格式的content前200个字符加上... 作为summary 如果content本身小于200 则不加...
        String summary = "";
        if(articleParam.getBody().getContent().length() < 200) {
            summary = articleParam.getBody().getContent();
        } else {
            summary = articleParam.getBody().getContent().substring(0, 200) + "...";
        }

        Article article = new Article();
        // 判断是否传递文章id 传了是编辑发布 没传是新增发布
        boolean isEdit = false;
        if (articleParam.getId() != null) {
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(summary);
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            articleMapper.updateById(article);
            isEdit = true;
        } else {
            article.setAuthorId(sysUser.getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setSummary(summary);
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
            // 插入之后会在程序上直接为当前article对象成生成一个id
            articleMapper.insert(article);
        }
        // tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            Long articleId = article.getId();
            if (isEdit) {
                // 先删除
                LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ArticleTag::getArticleId, articleId);
                articleTagMapper.delete(queryWrapper);
            }
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        // body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        if (isEdit) {
            LambdaUpdateWrapper<ArticleBody> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ArticleBody::getArticleId, article.getId());
            articleBodyMapper.update(articleBody, updateWrapper);
        } else {
            articleBodyMapper.insert(articleBody);
            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);
        }

        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());

        return Result.success(map);
    }

    @Override
    public Result searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle, search);
        queryWrapper.orderByDesc(Article::getCreateDate);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, true, true));

    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 不是所有接口都需要标签信息和作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagSevice.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return  articleBodyVo;
    }
}
