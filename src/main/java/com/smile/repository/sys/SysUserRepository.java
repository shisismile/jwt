package com.smile.repository.sys;

import com.smile.model.sys.SysUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 14:22
 */
@Repository
public interface SysUserRepository extends MongoRepository<SysUser, ObjectId> {
    /**
     * 通过用户名和密码查询用户
     * @param username
     * @return
     */
    SysUser findByUsername(String username);
}
