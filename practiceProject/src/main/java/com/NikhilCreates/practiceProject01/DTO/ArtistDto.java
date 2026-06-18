package com.NikhilCreates.practiceProject01.DTO;

import com.NikhilCreates.practiceProject01.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    @NotBlank(message = "Enter a name")
    private String name;
    @NotBlank(message = "Please select the genres")
    private List<String> genre;
    @NotBlank(message = "Please fill the required field")
    private String upiId;
    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;
    private Role role;
}
