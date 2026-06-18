package com.NikhilCreates.practiceProject01.Entity;


import com.NikhilCreates.practiceProject01.Enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "artist")
public class Artist {
    @NonNull
    private String name;
    @Id
    private String artistId;

    @Email
    @Indexed(unique = true)
    private String emailId;

    private String password;
    private Role role;

    private List<String> genre;
    private float rating;
    private int countOfRatings;
    @NonNull
    private String upiId;
    private LocalDateTime time;
}
