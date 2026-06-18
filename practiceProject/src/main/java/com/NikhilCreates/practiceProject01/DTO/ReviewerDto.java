package com.NikhilCreates.practiceProject01.DTO;

import com.NikhilCreates.practiceProject01.Enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewerDto {
    @NonNull
    private String name;

    @NonNull
    private List<String> expertise;

    @NonNull
    private String upiId;

    private Role role;

    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;
}
