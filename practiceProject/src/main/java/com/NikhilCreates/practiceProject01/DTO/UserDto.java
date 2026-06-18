package com.NikhilCreates.practiceProject01.DTO;

import com.NikhilCreates.practiceProject01.Enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {
    @Email
    @Valid
    private String emailId;
    private String password;
}
