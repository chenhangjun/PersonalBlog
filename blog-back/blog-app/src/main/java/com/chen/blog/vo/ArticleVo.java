package com.chen.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    // 防止前端精度损失 把id转为string
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Integer weight;
    private String createDate;
    private UserVo author;
    private ArticleBodyVo body;
    private List<TagVo> tags;
    private CategoryVo category;
}
