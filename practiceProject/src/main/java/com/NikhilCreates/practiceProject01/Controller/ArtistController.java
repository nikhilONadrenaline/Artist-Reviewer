package com.NikhilCreates.practiceProject01.Controller;

import com.NikhilCreates.practiceProject01.DTO.ArtistResponseDto;
import com.NikhilCreates.practiceProject01.DTO.ProjectDto;
import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import com.NikhilCreates.practiceProject01.Service.*;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ModerationService moderationService;

    @GetMapping("/{artistId}/projects")
    public List<Project> showProjects(@PathVariable String artistId)
    {
        return artistService.showProjects(artistId);
    }

    @GetMapping("/projects")
    public List<Project> homeProjects(@RequestParam List<String> genre,@RequestParam int minLevel,@RequestParam int maxLevel,@RequestParam ProjectStatus status){
        return projectService.searchProject(genre,minLevel, maxLevel,status);
    }

    @GetMapping("/projects/{projectId}/reviews")
    public List<Review> showReviews(@PathVariable String projectId, @RequestParam float ratingMin,@RequestParam float ratingMax,@RequestParam int levelMin,@RequestParam int levelMax)
    {
        return reviewService.showReviews(projectId,  ratingMin,  ratingMax,  levelMin,  levelMax);
    }

    @GetMapping("/reviewers/{reviewerId}")
    public Reviewer showReviewer(@PathVariable String reviewerId)
    {
        Reviewer reviewer =reviewerService.findById(reviewerId);
        reviewer.setWallet(0);
        reviewer.setUpiId(null);
        reviewer.setPassword(null);
        reviewer.setEmailId(null);
        return reviewer;
    }

    @PostMapping("/{artistId}/postProject")
    public String postProject( @Valid @RequestBody ProjectDto projectDto, @PathVariable String artistId)
    {
//        ObjectId artistId=new ObjectId(artistIdString);

        Project project=new Project();

        project.setArtistId(artistId);
        project.setName(projectDto.getName());
        project.setLevelMax(projectDto.getLevelMax());
        project.setLevelMin(projectDto.getLevelMin());
        project.setGenre(projectDto.getGenre());
        project.setPayPerReview(projectDto.getPayPerReview());
        project.setTotalReviewsNeeded(projectDto.getTotalReviewsNeeded());
        project.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAD34Y2hQGurxFzuhwk_mVh1_t9Y9O3mC-LA&s");
        return projectService.addProject(project);
    }

    @PostMapping("/{reviewId}/statusFlagged")
    public void statusFlagged(@PathVariable String reviewId)
    {
        moderationService.artistModerationFlag(reviewId);
    }

    @PostMapping("/{reviewId}/statusApproved")
    public void statusApproved(@PathVariable String reviewId)
    {
        moderationService.artistModerationApprove(reviewId);
    }


    @PostMapping("/withdrawProject/{projectId}")
    public void withdrawProject(@PathVariable String projectId)
    {
        projectService.projectWithdrawal(projectId);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody ArtistResponseDto artistResponseDto)
    {
        artistService.updateProfile(artistResponseDto);
    }

    @GetMapping("/showProject/{projectId}")
    public Optional<Project> selectProject(@PathVariable String projectId)
    {
        return projectService.selectProject(projectId);
    }

    @PostMapping("/deleteProject/{projectId}")
    public void deleteProject(@PathVariable String projectId)
    {
        projectService.deleteProject(projectId);
    }

}
