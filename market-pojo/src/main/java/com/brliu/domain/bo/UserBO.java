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

    @ApiModelProperty(value = "用户名", name = "username", example = "imooc", required = true)
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;
}
