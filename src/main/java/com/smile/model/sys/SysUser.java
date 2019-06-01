package com.smile.model.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 14:08
 */
@ApiModel(description = "用户")
@Data
@Document(collection = "sys_user")
public class SysUser {
    @Id
    @ApiModelProperty("主键")
    private ObjectId id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户状态")
    private Integer status;
    @ApiModelProperty("用户访问ip")
    private String ip;
    @ApiModelProperty("用户访问uri")
    private String uri;
    @ApiModelProperty("用户访问所带信息")
    private String from;
    @ApiModelProperty("用户唯一识别码")
    private String jti;
    @ApiModelProperty("用户所拥有的角色")
    private Set<SysRole> roles;
}
