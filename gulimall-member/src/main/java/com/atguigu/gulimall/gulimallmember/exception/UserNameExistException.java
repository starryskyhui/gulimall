package com.atguigu.gulimall.gulimallmember.exception;

/**
 * @author 郑国辉
 */
public class UserNameExistException extends RuntimeException {
    public UserNameExistException() {
        super("用户名存在");
    }
}
