package com.smile.repository.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smile.model.sys.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smile
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过角色id查询菜单
     * @param roleId
     * @return
     */
    @Select("SELECT menu.* FROM sys_menu menu,sys_role_menu rm WHERE menu.id=rm.menu_id AND rm.role_id=#{roleId}")
    List<SysMenu> findAllSysMenuByRoleId(@Param("roleId") Long roleId);
}
