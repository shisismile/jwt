package com.smile;

import com.smile.model.sys.SysRolePerm;
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


    @Test
    public void contextLoads() {
        Long userId=1135089791520690178L;
        Long roleId=1135090432607469569L;
        Long permId=1135091547335999489L;
        SysRolePerm sysRolePerm =new SysRolePerm();

        sysRolePerm.setPermId(permId);
        sysRolePerm.setRoleId(roleId);
        sysRolePermMapper.insert(sysRolePerm);

    }

}
