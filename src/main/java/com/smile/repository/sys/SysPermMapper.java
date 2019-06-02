package com.smile.repository.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysPerm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smile
 */
@Repository
public interface SysPermMapper extends BaseMapper<SysPerm> {
    /**
     * 通过角色id查询所有的权限
     * @param permId
     * @return
     */
    @Select("SELECT perm.* FROM sys_perm perm ,sys_perm_role pr WHERE pr.perm_id=perm.id AND pr.role_id=#{permId}")
    List<SysPerm> findAllSysPermByRoleId(@Param("permId") Long permId);
}
