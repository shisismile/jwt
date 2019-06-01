package com.smile.repository.sys;

import com.smile.model.sys.SysCaptchaModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysCaptchaModelRepository extends MongoRepository<SysCaptchaModel, ObjectId> {

}
