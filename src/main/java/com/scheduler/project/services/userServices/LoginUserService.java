package com.scheduler.project.services.userServices;

import com.scheduler.project.DTO.UserDTO;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUserService {
    @NoArgsConstructor
    public static class LoginUserServiceException extends Exception {
        public LoginUserServiceException(String message) {
            super(message);
        }
    }
    private final UserRepo userRepo;

    public LoginUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void login(UserDTO userDTO) throws LoginUserServiceException {
        List<UserEntity> selectedUsers = userRepo.findAllByCredentials(userDTO.getFirst_name(), userDTO.getLast_name());
        if (selectedUsers.isEmpty()) {
            throw new LoginUserServiceException("no user with credentials: first name=" + userDTO.getFirst_name() +
                    " and last name=" + userDTO.getLast_name());
        }
        if (selectedUsers.size() > 1) {
            throw new LoginUserServiceException("FATAL: more than one user with same credentials found");
        }
        UserEntity userEntity = selectedUsers.get(0);

        if (!userEntity.getUser_password().equals(userDTO.getUser_password())) {
            throw new LoginUserServiceException("password is incorrect");
        }
    }
}
