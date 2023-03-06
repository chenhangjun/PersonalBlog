package com.chen.blog.controller;

import com.chen.blog.common.aop.LogAnnotation;
import com.chen.blog.common.cache.Cache;
import com.chen.blog.dao.pojo.Article;
import com.chen.blog.service.ArticleService;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.params.ArticleParam;
import com.chen.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 首页 文章搜索--标题
     * @param articleParam
     * @return
     */
    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam) {
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
//    @Cache(expire = 5 * 60 * 1000, name = "list_article")
    @LogAnnotation(module = "文章", operation = "获取文章列表")   // 该注解可以对接口进行日志记录
    public Result listArticle(@RequestBody PageParams pageParams) {
//        int i = 10 / 0;
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticles() {
        int limit = 5;  // 取前5
        return articleService.hotArticles(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result newArticles() {
        int limit = 5;  // 取前5
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档（按时间）
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    @PostMapping("/{id}")
    public Result editArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }
}
