package com.chen.blog.controller;

import com.chen.blog.service.CommentService;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentService.commentsByArticle(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParams commentParams) {
        return commentService.comment(commentParams);
    }
}
