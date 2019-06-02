package com.smile.repository.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysRole;
import com.smile.model.sys.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Select("SELECT id,username,password,email,mobile,`status`,create_user_id,create_time FROM sys_user WHERE username=#{username}")
    @Results(id = "userBaseMap",
     value = {
             @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
             @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
             @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
             @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
             @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
             @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
             @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
             @Result(column="create_time", property="createTime", jdbcType=JdbcType.DATETIMEOFFSET),
             @Result(column = "id",property = "roleList" ,javaType = SysRole.class,many = @Many(select = "com.smile.repository.sys.SysRoleMapper.findAllSysRoleByUserId"))
     }
    )
    SysUser findSysUserByUsername(@Param("username")String username);

}
