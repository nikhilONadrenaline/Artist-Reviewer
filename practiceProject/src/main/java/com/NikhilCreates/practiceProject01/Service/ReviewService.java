package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import com.NikhilCreates.practiceProject01.Repositories.ProjectRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    public void addReview(Review review)
    {
        if(reviewRepository.existsByProjectIdAndReviewerId(review.getProjectId(),review.getReviewerId()))
        {
            throw new RuntimeException("Reviewer has already reviewed this project");
        }

        //FETCH REVIEWER ID AS REVIEWER IS LOGGED IN
        Reviewer reviewer=reviewerRepository.findById(review.getReviewerId() ).orElseThrow(
                ()->new RuntimeException("Reviewer not found")
        );
        // FETCH THE PROJECT ID AS THE PROJECT IS SELECTED
        Project project=projectRepository.findById(review.getProjectId()).orElseThrow(
                ()-> new RuntimeException("Project not found")
        );

        if(reviewer.getLevel()<project.getLevelMin())
        {
            throw new RuntimeException("User is not eligible to review the project.");
        }

        review.setReviewerLevel(reviewer.getLevel());
        review.setStatus(ReviewStatus.PENDING);
        review.setTime(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public List<Review> showReviews(String projectId, float ratingMin, float ratingMax, int levelMin, int levelMax) // Default values using controller
    {
//        ObjectId projectId=new ObjectId(projectIdString);
        if(levelMin>0) return reviewRepository.findByProjectIdAndReviewerLevelGreaterThan(projectId, levelMin);
        if(levelMax<100) return reviewRepository.findByProjectIdAndReviewerLevelLessThan(projectId,levelMax);
        if(ratingMin>0.0) return reviewRepository.findByProjectIdAndRatingGreaterThan(projectId,ratingMin);
        if(ratingMax<10.0) return reviewRepository.findByProjectIdAndRatingLessThan(projectId,ratingMax);
        return reviewRepository.findByProjectId(projectId);
    }

}
