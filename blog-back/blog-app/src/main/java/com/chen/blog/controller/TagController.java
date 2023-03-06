package com.chen.blog.controller;

import com.chen.blog.service.TagSevice;
import com.chen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagSevice tagSevice;

    @GetMapping("/hot")
    public Result hot() {
        // 取前6个标签
        int limit = 6;
        return tagSevice.hots(limit);
    }

    @GetMapping
    public Result findAll() {
        return tagSevice.findAll();
    }

    @GetMapping("/detail")
    public Result findAllDetail() {
        return tagSevice.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id) {
        return tagSevice.findDetailById(id);
    }
}
