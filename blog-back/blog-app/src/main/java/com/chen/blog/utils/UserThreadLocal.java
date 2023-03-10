package com.chen.blog.utils;

import com.chen.blog.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal() {};

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void set(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
