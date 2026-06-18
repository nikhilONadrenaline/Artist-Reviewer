package com.NikhilCreates.practiceProject01.Entity;

import com.NikhilCreates.practiceProject01.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reviewers")

public class Reviewer {
    @NonNull
    private String name;
    @Id
    private String id;

    @Email
    @Indexed(unique = true)
    private String emailId;

    private String password;

    @Min(1)
    private int level;
    private int exp;
    private float rating;

    private List<String> expertise;
    private int completedReviews;
    private Role role;

    private float rateArtist;
    @Min(0)
    private int wallet;
    @NonNull
    private String upiId;
    private LocalDateTime joinedAt;

}
