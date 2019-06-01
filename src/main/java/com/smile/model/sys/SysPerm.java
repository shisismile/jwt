package com.smile.model.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 14:17
 */
@ApiModel(description = "系统权限")
@Document(collection = "sys_perm")
@Data
public class SysPerm {
    @Id
    @ApiModelProperty("主键")
    private ObjectId id;
    @ApiModelProperty("权限")
    private String perm;
    @ApiModelProperty("权限描述")
    private String permDesc;
}
