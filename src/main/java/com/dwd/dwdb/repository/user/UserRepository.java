package com.dwd.dwdb.repository.user;

import com.dwd.dwdb.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
}
