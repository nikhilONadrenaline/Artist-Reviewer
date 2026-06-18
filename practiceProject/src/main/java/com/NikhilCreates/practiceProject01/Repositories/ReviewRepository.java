package com.NikhilCreates.practiceProject01.Repositories;

import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
// SHOWS ALL REVIEWS OF A PROJECT AT A PLACE TO THE ARTIST AND STOPS THE REVIEWER FROM SPAMS
@Repository
public interface ReviewRepository extends MongoRepository<Review , String> {
    List<Review> findByProjectId(String projectId);
    List<Review> findByProjectIdAndRatingGreaterThan(String projectId, float rating);
    List<Review> findByProjectIdAndRatingLessThan(String projectId, float rating);
    List<Review> findByProjectIdAndReviewerLevelLessThan(String projectId, int level);
    List<Review> findByProjectIdAndReviewerLevelGreaterThan(String projectId, int level);
    List<Review> findByStatusAndArtistDeadlineBefore(ReviewStatus status,LocalDateTime time);
    List<Review> findByStatus(ReviewStatus status);

    boolean existsByProjectIdAndReviewerId(String projectId, String reviewerId);
}
