package com.NikhilCreates.practiceProject01.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    @NotBlank(message = "Adhere to the project instructions and fill the review appropriately to get approved")
    private String review;
    @NotBlank(message = "Please do rate")
    private float rating;
}
