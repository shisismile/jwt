package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.model.sys.SysMenu;
import com.smile.repository.sys.SysMenuMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class MenuService extends ServiceImpl<SysMenuMapper, SysMenu> {
}
