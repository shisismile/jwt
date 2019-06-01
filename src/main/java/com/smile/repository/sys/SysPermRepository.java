package com.smile.repository.sys;

import com.smile.model.sys.SysPerm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/31 14:24
 */
@Repository
public interface SysPermRepository extends MongoRepository<SysPerm, ObjectId> {
}
