package com.smile.repository.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysMenu;
import com.smile.model.sys.SysPerm;
import com.smile.model.sys.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

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
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT),
            @Result(column = "role_name",property = "roleName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark",property = "remark",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_user_id",property = "createUserId",jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time",property = "createTime",jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "id",property = "permList",javaType = SysPerm.class,many = @Many(select = "com.smile.repository.sys.SysPermMapper.findAllSysPermByRoleId")),
            @Result(column = "id",property = "menuList",javaType = SysMenu.class,many = @Many(select = "com.smile.repository.sys.SysMenuMapper.findAllSysMenuByRoleId"))
            }
    )
    List<SysRole> findAllSysRoleByUserId(@Param("userId")Long userId);
}
