package com.atguigu.gulimall.cart.vo;

import lombok.Data;

/**
 * @author 郑国辉
 */
@Data
public class UserInfoTo {
    private Long userId;
    private String userKey;

    private Boolean tempUser = false;
}
