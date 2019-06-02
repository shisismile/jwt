package com.smile.repository.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
             @Result(column="id", property="id",javaType = Long.class, id=true),
             @Result(column="username", property="username", javaType = String.class),
             @Result(column="password", property="password",javaType = String.class),
             @Result(column="email", property="email", javaType = String.class),
             @Result(column="mobile", property="mobile", javaType = String.class),
             @Result(column="status", property="status", javaType = Integer.class),
             @Result(column="create_user_id", property="createUserId",javaType = Long.class),
             @Result(column="create_time", property="createTime", javaType = LocalDateTime.class),
             @Result(column = "id",property = "roleList" ,javaType = List.class,many = @Many(select = "com.smile.repository.sys.SysRoleMapper.findAllSysRoleByUserId"))
     }
    )
    SysUser findSysUserByUsername(@Param("username")String username);

}
