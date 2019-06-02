package com.smile;

import com.smile.model.sys.SysUser;
import com.smile.repository.sys.SysRoleMapper;
import com.smile.repository.sys.SysRolePermMapper;
import com.smile.repository.sys.SysUserRoleMapper;
import com.smile.service.PermService;
import com.smile.service.RoleService;
import com.smile.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermService permService;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysRolePermMapper sysRolePermMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;


    @Test
    public void contextLoads() {
        Long userId=1135089791520690178L;
        SysUser byId = userService.findSysUserByUsername("admin");
        System.out.println(byId);
    }


    @Test
    public void test(){

    }
}
