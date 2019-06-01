package com.smile.model.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 14:12
 */
@ApiModel(description = "系统角色")
@Document(collection = "sys_role")
@Data
public class SysRole {
    @Id
    @ApiModelProperty("主键")
    private ObjectId id;
    @ApiModelProperty("角色")
    private String role;
    @ApiModelProperty("角色描述")
    private String roleDesc;
    @ApiModelProperty("角色所拥有的权限")
    private Set<SysPerm> sysPerms;

}
