package com.genesys.messenger.repository;

import com.genesys.messenger.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String> {

}
