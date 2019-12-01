package com.brliu.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户对象",description = "封装前端传入的用户属性")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBO {
    /**
     * 主键id 用户id
     */
    private String id;

    /**
     * 用户名 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;
}
