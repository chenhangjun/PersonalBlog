package com.chen.blog.controller;

import com.chen.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file) {
        // MultipartFile -- Springboot专门用来接收文件的类型
        // 获取文件原始名称
        String originalFileName = file.getOriginalFilename();
        // 唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFileName, ".");
        File path = new File("../img/");
        try {
            File imgPath = new File(path.getAbsolutePath() + File.separator + fileName);
            file.transferTo(imgPath);
//            System.out.println(fileName);
//            System.out.println(imgPath);
            return Result.success("http://localhost:8088/" + fileName);  // 本地
//            return Result.success("http://101.43.27.242:8088/" + fileName);   // 服务器 nginx映射图片服务器
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(20001, "上传失败");
    }
}
