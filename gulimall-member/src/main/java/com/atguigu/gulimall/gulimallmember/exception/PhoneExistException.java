package com.atguigu.gulimall.gulimallmember.exception;

/**
 * @author 郑国辉
 */
public class PhoneExistException extends RuntimeException {
    public PhoneExistException() {
        super("手机号存在");
    }
}
