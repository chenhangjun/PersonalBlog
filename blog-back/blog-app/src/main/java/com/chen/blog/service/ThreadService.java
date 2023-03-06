package com.chen.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chen.blog.dao.mapper.ArticleMapper;
import com.chen.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    // 期望此操作在线程池执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        // 设置一个乐观锁 多线程环境下 线程安全
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        // update article set viewCount = ? where viewCount = ? and id = ?
        articleMapper.update(articleUpdate, updateWrapper);
//        try {
//            Thread.sleep(5000);
//            System.out.println("更新完成...");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
