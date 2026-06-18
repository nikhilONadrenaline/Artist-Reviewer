package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import com.NikhilCreates.practiceProject01.Repositories.ProjectRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    // CALLED WHEN REVIEW IS APPROVED
    public void paymentToWallet(Review review)
    {

            review.setStatus(ReviewStatus.PAID);
            reviewRepository.save(review);
            Project project=projectRepository.findById(review.getProjectId()).orElseThrow(
                    ()-> new RuntimeException("Project Not Found")
            );

            Reviewer reviewer=reviewerRepository.findById(review.getReviewerId()).orElseThrow(
                    ()->new RuntimeException("No reviewer found")
            );

            reviewer.setWallet(reviewer.getWallet()+project.getPayPerReview());
            project.setRemainingBudget(project.getRemainingBudget()-project.getPayPerReview());
            projectRepository.save(project);

    }

    public void payment(String upiId, int amount)
    {
        // WITHRAW MONEY FROM SERVER TO UPI ID PROVIDED BY REVIEWER
    }
}
