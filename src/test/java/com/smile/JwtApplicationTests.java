package com.smile;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Test
    public void contextLoads() {
        SysUser sysUser=new SysUser();
        sysUser.setId(ObjectId.get());
        sysUser.setUsername("smile");
        sysUser.setPassword("123456");
        sysUser.setStatus(0);
        SysRole sysRole=new SysRole();
        sysRole.setId(ObjectId.get());
        sysRole.setRole("admin");
        Set<SysRole> set=new HashSet<>();
        set.add(sysRole);
        sysUser.setRoles(set);
        SysPerm sysPerm=new SysPerm();
        sysPerm.setId(ObjectId.get());
        sysPerm.setPerm("sys:*");
        Set<SysPerm> sysPerms=new HashSet<>();
        sysPerms.add(sysPerm);
        sysRole.setSysPerms(sysPerms);
        System.out.println(sysUser);
        sysUserRepository.save(sysUser);
    }

}
