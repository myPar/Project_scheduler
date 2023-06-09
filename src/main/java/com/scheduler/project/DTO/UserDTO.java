package com.scheduler.project.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    @NotBlank(message = "user first name should contain characters")
    private String first_name;

    @NotBlank(message = "user second name should contain characters")
    private String last_name;

    @NotBlank(message = "user password should contain characters")
    private String user_password;
}
