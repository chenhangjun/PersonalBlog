package com.chen.blog.controller;


import com.chen.blog.service.StatisticService;
import com.chen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public Result getNumbers() {
        return statisticService.getNumbers();
    }
}
