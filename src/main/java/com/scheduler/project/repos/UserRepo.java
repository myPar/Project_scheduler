package com.scheduler.project.repos;

import com.scheduler.project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM users as u where u.first_name= :first_name and u.last_name= :second_name", nativeQuery = true)
    List<UserEntity> findAllByCredentials(@Param(value="first_name") String first_name, @Param(value="second_name") String second_name);
}
