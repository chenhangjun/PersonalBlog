package com.chen.blog.vo;

public enum ErrorCode {

    PARAMS_ERROR(10001, "非法参数"),
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),
    ACCOUNT_EXISTED(10004, "账号已存在"),
    TOKEN_ERROR(10003, "非法token"),
    NO_PERMISSION(70001, "无权限"),
    SESSION_TIME_OUT(90001, "会话超时"),
    NOT_LOGIN(90002, "未登录");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }

    public void setCode(int code) { this.code = code; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }
}
