package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import com.NikhilCreates.practiceProject01.Repositories.ProjectRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ModerationService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ArtistService artistService;


    public void aiModeration(String reviewId)
    {
        Review review=reviewRepository.findById(reviewId).orElseThrow(
                ()-> new RuntimeException("No review found")
        );

        if(review.getStatus()!= ReviewStatus.PENDING) throw new RuntimeException("Already processed");
        else
        {
            //DO AI CHECK
            review.setStatus(ReviewStatus.PROCESSED);
            review.setArtistDeadline(LocalDateTime.now().plusHours(24));
            reviewRepository.save(review);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void autoModeration()
    {
        List<Review> reviewList=reviewRepository.findByStatusAndArtistDeadlineBefore(ReviewStatus.PROCESSED, LocalDateTime.now());

        for(Review review: reviewList)
        {
            review.setStatus(ReviewStatus.APPROVED); // Calls
            reviewRepository.save(review);
            Reviewer reviewer=reviewerRepository.findById(review.getReviewerId()).orElseThrow(
                    ()->new RuntimeException("no reviewer found")
            );

            Project project=projectRepository.findById(review.getProjectId()).orElseThrow(
                    ()-> new RuntimeException("No project found")
            );

            if(project.getLevelMin()<=15) reviewer.setExp(reviewer.getExp()+10);
            else if(project.getLevelMin()<=25) reviewer.setExp(reviewer.getExp()+20);
            else if(project.getLevelMin()<=40) reviewer.setExp(reviewer.getExp()+30);
            else reviewer.setExp(reviewer.getExp()+40);

            reviewerRepository.save(reviewer);
        }
    }

    public void artistModerationApprove(String reviewId)
    {
//        ObjectId reviewId=new ObjectId(reviewIdString);
        Review review=reviewRepository.findById(reviewId).orElseThrow(()-> {
                log.error("Can't find a review with id: {}",reviewId);
                 return new RuntimeException("No review found");
                }
        );
        review.setStatus(ReviewStatus.APPROVED);
        reviewRepository.save(review);
        Reviewer reviewer=reviewerRepository.findById(review.getReviewerId()).orElseThrow(()->{
                log.error("Can't find a reviewer with id: {}",review.getReviewerId());
                return new RuntimeException("no reviewer found");
                }
        );

        Project project=projectRepository.findById(review.getProjectId()).orElseThrow(()-> {
            log.error("Can't find a project with id: {}", review.getProjectId());
                return new RuntimeException("No project found");
                }
        );

        if(project.getLevelMin()<=15) reviewer.setExp(reviewer.getExp()+10);
        else if(project.getLevelMin()<=25) reviewer.setExp(reviewer.getExp()+20);
        else if(project.getLevelMin()<=40) reviewer.setExp(reviewer.getExp()+30);
        else reviewer.setExp(reviewer.getExp()+40);

        reviewerRepository.save(reviewer);
    }

    public void artistModerationFlag(String reviewId)
    {
//        ObjectId reviewId=new ObjectId(reviewIdString);
        Review review=reviewRepository.findById(reviewId).orElseThrow(()-> {
                    log.error("Can't find a review with id: {}",reviewId);
                    return new RuntimeException("No review found");
                }
        );
        review.setStatus(ReviewStatus.FLAGGED);
        reviewRepository.save(review);
    }

    public void adminModerationApprove(String reviewId)
    {
//        ObjectId reviewId=new ObjectId(reviewIdString);
        Review review=reviewRepository.findById(reviewId).orElseThrow(()-> {
                    log.error("Can't find a review with id: {}",reviewId);
                    return new RuntimeException("No review found");
                }
        );
        review.setStatus(ReviewStatus.APPROVED);
        reviewRepository.save(review);

        Reviewer reviewer=reviewerRepository.findById(review.getReviewerId()).orElseThrow(()->{
                    log.error("Can't find a reviewer with id: {}",review.getReviewerId());
                    return new RuntimeException("no reviewer found");
                }
        );

        Project project=projectRepository.findById(review.getProjectId()).orElseThrow(()-> {
                    log.error("Can't find a project with id: {}", review.getProjectId());
                    return new RuntimeException("No project found");
                }
        );

        if(project.getLevelMin()<=15) reviewer.setExp(reviewer.getExp()+10);
        else if(project.getLevelMin()<=25) reviewer.setExp(reviewer.getExp()+20);
        else if(project.getLevelMin()<=40) reviewer.setExp(reviewer.getExp()+30);
        else reviewer.setExp(reviewer.getExp()+40);

        reviewerRepository.save(reviewer);
    }

    public void adminModerationReject(String reviewId)
    {
//        ObjectId reviewId=new ObjectId(reviewIdString);
        Review review=reviewRepository.findById(reviewId).orElseThrow(
                ()-> new RuntimeException("No review found")
        );
        review.setStatus(ReviewStatus.REJECTED);
        reviewRepository.save(review);
    }

    @Scheduled(fixedRate=60000)
    public void approveHandle()
    {
        List<Review> reviews=reviewRepository.findByStatus(ReviewStatus.APPROVED);

        for(Review review : reviews)
        {
            try
            {
                processApproved(review.getId());
            }
            catch(Exception e)
            {
                System.out.println("Retry next cycle");
            }
        }
    }

    @Transactional
    public void processApproved(String reviewId)
    {
        Review review= reviewRepository.findById(reviewId).orElseThrow(()-> {
                    log.error("Can't find a review with id: {}",reviewId);
                    return new RuntimeException("No review found");
                }
        );

        if(review.getStatus()!=ReviewStatus.APPROVED)
        {
            throw new RuntimeException("Review Not Approved");
        }

        projectService.updateRating(review);
        artistService.updateRating(review);
        reviewerService.updateLevel(review.getReviewerId());
        paymentService.paymentToWallet(review);

    }

}
