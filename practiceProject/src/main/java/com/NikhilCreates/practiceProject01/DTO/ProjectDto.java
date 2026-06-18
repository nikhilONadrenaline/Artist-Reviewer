package com.NikhilCreates.practiceProject01.DTO;

import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    @NonNull
    private String name;

    @Min(1)
    private int levelMin;
    @Max(50)
    private int levelMax;
    @NonNull
    private List<String> genre;
    @Min(1)
    private int payPerReview;
    @Min(1)
    private int totalReviewsNeeded;

    @NonNull
    private String description;

}
