package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.model.sys.SysPerm;
import com.smile.repository.sys.SysPermMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class PermService extends ServiceImpl<SysPermMapper, SysPerm> {
}
