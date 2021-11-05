package com.alpha.graduatesmeeting.api.usersservice.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserIdOrEmailOrPhone(String userId, String email, String phone);
    UserEntity findByUserId(String userId);
}
