package com.NikhilCreates.practiceProject01.Entity;

import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private String id;
    @NonNull
    private String review;
    @NonNull
    private float rating;

    private ReviewStatus status;
    private String reviewerId;
    private int reviewerLevel;

    private String projectId;

    private LocalDateTime time;
    private LocalDateTime artistDeadline;
}
