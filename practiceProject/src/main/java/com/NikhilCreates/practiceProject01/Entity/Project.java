package com.NikhilCreates.practiceProject01.Entity;

import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    private String name;
    private String artistId;

    @Min(1)
    private int levelMin;
    @Max(50)
    private int levelMax;

    private List<String> genre;

    @Min(1)
    private int payPerReview;
    private ProjectStatus status;
    @Min(1)
    @Max(5)
    private float rating;

    @Min(1)
    private int totalReviewsNeeded;
    private int totalReviewsReceived;
    private int totalBudget;
    private int remainingBudget;
    private LocalDateTime createdAt;
    private String imageUrl;

    private String description;

}
