package com.NikhilCreates.practiceProject01.DTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@NonNull
@AllArgsConstructor
public class ArtistResponseDto {
    @Id
    private String artistId;
    @NonNull
    private String name;

    @Indexed(unique = true)
    @Email
    private String emailId;

    private List<String> genre;
    private float rating;
    private int countOfRatings;
    private String upiId;
    private LocalDateTime time;

    private String token;
}
