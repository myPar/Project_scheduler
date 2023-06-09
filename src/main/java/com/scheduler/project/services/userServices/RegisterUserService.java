package com.scheduler.project.services.userServices;

import com.scheduler.project.DTO.UserDTO;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.mappers.UserMapper;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserService {
    @NoArgsConstructor
    public static class RegisterUserServiceException extends Exception {
        public RegisterUserServiceException(String message) {
            super(message);
        }
    }
    private final UserRepo userRepo;

    @Autowired
    public RegisterUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void register(UserDTO userDTO) throws RegisterUserServiceException{
        List<UserEntity> selectedUsers = userRepo.findAllByCredentials(userDTO.getFirst_name(), userDTO.getLast_name());

        if (selectedUsers.size() > 0) {
            if (selectedUsers.size() > 1) {
                throw new RegisterUserServiceException("FATAL: more than one user with same credentials found");
            }
            throw new RegisterUserServiceException("user with first name=" + userDTO.getFirst_name() +
                    " and second name=" + userDTO.getLast_name() + " already exists");
        }
        UserEntity createdUser = UserMapper.INSTANSE.mapEntityFromDTO(userDTO);
        userRepo.save(createdUser);
    }
}
