package com.chen.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CategoryVo {

    // 防止前端精度损失 把id转为string
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String avatar;
    private String categoryName;
    private String description;
}
