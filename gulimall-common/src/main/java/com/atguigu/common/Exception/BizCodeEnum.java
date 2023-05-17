package com.atguigu.common.Exception;

/**
 * @author 郑国辉
 */
public enum BizCodeEnum {
    // 未知异常
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    // 校验失败
    VALID_EXCEPTION(10001, "参数格式校验失败");

    private Integer code;
    private String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
