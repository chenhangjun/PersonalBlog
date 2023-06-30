package com.chen.blog.handler;

import com.alibaba.fastjson.JSON;
import com.chen.blog.dao.pojo.SysUser;
import com.chen.blog.service.LoginService;
import com.chen.blog.utils.UserThreadLocal;
import com.chen.blog.vo.ErrorCode;
import com.chen.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行Controller方法(Handler)之前执行此方法
        /**
         * 1. 需要判断 请求的接口路径 是否为 HandlerMethod(Controller方法)
         * 2. 判断token是否为空， 如空需要登录
         * 3. token不为空，登陆验证 loginService checkToken
         * 4. 如果认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)) {
            // handler 可能是 RequestResourceHandler springboot程序访问静态资源 默认去classpath下的static目录去查询
            // 返回 true 表示放行
            return true;
        }

        String token = request.getHeader("Authorization");

        // 输出日志信息
        log.info("================== request start ==================");
        String requestURI = request.getRequestURI();
        log.info("request uri: {}", requestURI);
        log.info("request method: {}", request.getMethod());
        log.info("token: {}", token);
        log.info("================== request end ==================");

        if (requestURI.equals("/comments/create/change") && (StringUtils.isBlank(token) || token.equals("undefined")))  {
            // 如果是游客添加评论  没有token
            return true;
        }

        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        // 登录验证成功 放行
        // user信息 存入 threadLocal 中，以便在controller中直接获取
        UserThreadLocal.set(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 用完信息则删除  如果不删 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
