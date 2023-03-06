package com.chen.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.chen.blog.dao.pojo.SysUser;
import com.chen.blog.service.LoginService;
import com.chen.blog.service.SysUserService;
import com.chen.blog.utils.JWTUtils;
import com.chen.blog.vo.ErrorCode;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String salt = "chen!@#";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 使用jwt 生成Token 返回给前端
         * 5. token放入redis  redis中存 token:user 键值对  设置过期时间
         * 6. 登录认证时 先认证token字符串是否合法 再去redis认证是否存在
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt );
//        System.out.println(password);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1. 判断参数合法性
         * 2. 判断账户是否存在， 存在 则返回账户已被注册
         * 3. 不存在 则注册用户
         * 4. 生成token， 存入redis并返回
         * 5. 注意：需要加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚  @Transactional注解，已加在文件头
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = sysUserService.findByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXISTED.getCode(), ErrorCode.ACCOUNT_EXISTED.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setNickname(nickname);
        sysUser.setAvatar("/static/user/admin.png");
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAdmin(1);    // 1为true
        sysUser.setDeleted(0);  // 0为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
