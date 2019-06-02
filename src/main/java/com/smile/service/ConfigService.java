package com.smile.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.model.sys.SysConfig;
import com.smile.repository.sys.SysConfigMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
@AllArgsConstructor
public class ConfigService extends ServiceImpl<SysConfigMapper, SysConfig> {
}
