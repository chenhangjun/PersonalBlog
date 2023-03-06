package com.chen.blog.service;

import com.chen.blog.dao.pojo.SysUser;
import com.chen.blog.vo.Result;
import com.chen.blog.vo.UserVo;

public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUser(String account, String password);

    SysUser findUserById(Long id);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账号查找用户
     * @param account
     * @return
     */
    SysUser findByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
