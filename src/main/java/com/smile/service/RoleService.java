package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.model.sys.SysRole;
import com.smile.repository.sys.SysRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class RoleService extends ServiceImpl<SysRoleMapper, SysRole> {
}
