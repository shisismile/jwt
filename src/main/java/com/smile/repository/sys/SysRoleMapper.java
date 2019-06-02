package com.smile.repository.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author smile
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户id查角色
     * @param userId
     * @return
     */
    @Select("SELECT role.`id`,role.`role_name`,role.`remark`,role.`create_user_id`,role.`create_time` " +
            "FROM sys_role role,sys_user_role ur WHERE role.`id`=ur.role_id AND ur.user_id=#{userId}")
    @Results(id = "roleBaseMap",
            value = {
            @Result(column = "id",property = "id",javaType = Long.class),
            @Result(column = "role_name",property = "roleName",javaType = String.class),
            @Result(column = "remark",property = "remark",javaType = String.class),
            @Result(column = "create_user_id",property = "createUserId",javaType = Long.class),
            @Result(column = "create_time",property = "createTime",javaType = LocalDateTime.class),
            @Result(column = "id",property = "permList",javaType = List.class,many = @Many(select = "com.smile.repository.sys.SysPermMapper.findAllSysPermByRoleId")),
            @Result(column = "id",property = "menuList",javaType = List.class,many = @Many(select = "com.smile.repository.sys.SysMenuMapper.findAllSysMenuByRoleId"))
            }
    )
    List<SysRole> findAllSysRoleByUserId(@Param("userId")Long userId);
}
