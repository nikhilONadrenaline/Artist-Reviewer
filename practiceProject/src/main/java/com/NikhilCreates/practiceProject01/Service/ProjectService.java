package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import com.NikhilCreates.practiceProject01.Repositories.ProjectRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public String addProject(Project project)
    {
        // THE ARTIST ID MUST BE FETCHED AS THE ARTIST IS LOGGED IN
        project.setRating(5);
        project.setStatus(ProjectStatus.MONEYNOTPAID);
        project.setTotalBudget(project.getTotalReviewsNeeded()*project.getPayPerReview());
        project.setRemainingBudget(project.getTotalBudget());
        project.setCreatedAt(LocalDateTime.now());
        project.setTotalReviewsReceived(0);
        project.setDescription(project.getDescription());
        projectRepository.save(project);
        return project.getId();
    }

    public void updateRating(Review review)  //  Works when review is APPROVED
    {

        Project project=projectRepository.findById(review.getProjectId()).orElseThrow(
                ()-> new RuntimeException("Project Not Found")
        );

        float rating=project.getRating();
        int totalReviewsReceived= project.getTotalReviewsReceived();

        project.setTotalReviewsReceived(totalReviewsReceived+1);
        rating=(rating*totalReviewsReceived+review.getRating())/(totalReviewsReceived+1);
        project.setRating(rating);

        if(totalReviewsReceived+1 == project.getTotalReviewsNeeded()) project.setStatus(ProjectStatus.COMPLETED);

        projectRepository.save(project);
    }

    public void projectWithdrawal(String projectId)
    {
//        ObjectId projectId=new ObjectId(projectIdString);
        Project project=projectRepository.findById(projectId).orElseThrow(
                ()-> new RuntimeException("Project Not Found")
        );

        project.setStatus(ProjectStatus.CLOSED);
        //REFUND OF REMAINING MONEY

        projectRepository.save(project);
    }

    public List<Project> searchProject(List<String> genre, int minLevel, int maxLevel, ProjectStatus status)
    {
        return projectRepository.findAll(); // KAAM KAR YAHA PE
    }

    public float showRating(String projectId)
    {
        Project project=projectRepository.findById(projectId).orElseThrow(
                ()-> new RuntimeException("Project not available")
        );
        return project.getRating();
    }

    public Optional<Project> selectProject(String projectId)
    {
        return projectRepository.findById(projectId);
    }

    public void deleteProject(String projectId){

        Project project=projectRepository.findById(projectId).orElseThrow(
                ()->new RuntimeException("No project Found")
        );

        if(project.getStatus()!=ProjectStatus.CLOSED && project.getStatus()!=ProjectStatus.MONEYNOTPAID )
        {
            log.error("Project is not closed");
            throw new RuntimeException("Project need to be closed in order to delete");
        }

        projectRepository.deleteById(projectId);
    }
}
