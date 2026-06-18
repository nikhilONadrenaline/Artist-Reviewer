package com.NikhilCreates.practiceProject01.Controller;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import com.NikhilCreates.practiceProject01.Service.ProjectService;
import com.NikhilCreates.practiceProject01.Service.ReviewService;
import com.NikhilCreates.practiceProject01.Service.ReviewerService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviewer")
public class ReviewerController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/showProjects")
    public List<Project> showProjects(@RequestParam List<String> genre,@RequestParam int levelMin,@RequestParam int levelMax,@RequestParam ProjectStatus status)
    {
        return projectService.searchProject( genre,levelMin,levelMax,status);
    }
    @GetMapping("/showProject/{projectId}")
    public Optional<Project> selectProject(@RequestParam String projectId)
    {
        return projectService.selectProject(projectId);
    }

    @PostMapping("/{reviewerId}/withdrawMoney")
    public void withdrawMoney(@PathVariable String reviewerId, @RequestBody int amount)
    {
        reviewerService.withdrawMoney(reviewerId, amount);
    }
    @PostMapping("/project/{projectId}/{reviewerId}/addReview")
    public void addReview(@Valid @RequestBody Review review, @PathVariable String projectId, @PathVariable String reviewerId)
    {
//        ObjectId projectId=new ObjectId(projectIdString);
//        ObjectId reviewerId=new ObjectId(reviewerIdString);
        review.setProjectId(projectId);
        review.setReviewerId(reviewerId);
        reviewService.addReview(review);
    }

}
