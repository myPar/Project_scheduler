package com.scheduler.project.repos;

import com.scheduler.project.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    @Override
    Optional<UserEntity> findById(Long aLong);
}
