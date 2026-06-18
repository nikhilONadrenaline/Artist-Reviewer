package com.NikhilCreates.practiceProject01.DTO;

import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerResponseDTO {
    @NonNull
    private String name;
    @Id
    private String id;

    @Indexed(unique = true)
    @Email
    private String emailId;

    @Min(1)
    private int level;
    private int exp;
    private float rating;

    private List<String> expertise;
    private int completedReviews;

    private float rateArtist;
    @Min(1)
    private int wallet;
    private String upiId;
    private LocalDateTime joinedAt;

    private String token;
}
