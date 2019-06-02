package com.smile.model.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 权限与角色对应关系
 *
 * @author smile
 */
@Data
@TableName("sys_perm_role")
public class SysRolePerm {
    @TableId
    private Long id;

    /**
     * 权限ID
     */
    private Long permId;

    /**
     * 角色ID
     */
    private Long roleId;

}
