package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.DTO.ArtistResponseDto;
import com.NikhilCreates.practiceProject01.DTO.UserDto;
import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Entity.Review;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import com.NikhilCreates.practiceProject01.Enums.ReviewStatus;
import com.NikhilCreates.practiceProject01.Repositories.ArtistRepository;
import com.NikhilCreates.practiceProject01.Repositories.ProjectRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private PaymentService paymentService;



    public void addArtist(Artist artist)
    {
        if(artistRepository.existsByEmailId(artist.getEmailId()) || reviewerRepository.existsByEmailId(artist.getEmailId())) {
            log.error("Account already Exists for email id : {}", artist.getEmailId());
            throw new BadCredentialsException("Email Id already registered");
        }

        artist.setRating(5);
        artist.setPassword(bCryptPasswordEncoder.encode(artist.getPassword()));
        artist.setTime(LocalDateTime.now());
        artistRepository.save(artist);
    }

    public void updateRating(Review review) // REVIEW APPROVE HONE PE
    {

        if(review.getStatus() == ReviewStatus.APPROVED)
        {
            Project project=projectRepository.findById(review.getProjectId()).orElseThrow(()->{
                        log.error("Can't find any project with id : {}",review.getProjectId());
                        return new RuntimeException("no project");
                    }
            );

            Artist artist=artistRepository.findById(project.getArtistId()).orElseThrow(()-> {
                        log.error("Can't find artist with id: {}",project.getArtistId());
                        return new RuntimeException("No artist");
                    }
            );
            int ratingCount=artist.getCountOfRatings();
            float rating=(ratingCount* artist.getRating()+ review.getRating())/(ratingCount+1);

            artist.setRating(rating);
            artist.setCountOfRatings(ratingCount+1);
            artistRepository.save(artist);
        }
    }

    public void updateStatus(String projectId)
    {
//        ObjectId projectId=new ObjectId(projectIdString);
        Project project=projectRepository.findById(projectId).orElseThrow(
                ()->new RuntimeException("no project")
        );

        project.setStatus(ProjectStatus.OPEN);
        projectRepository.save(project);
    }

    public List<Project> showProjects(String artistId)
    {
//        ObjectId artistId=new ObjectId(artistIdString);
        List<Project> projectList =projectRepository.findByArtistId(artistId);

        return projectList;
    }

    public ArtistResponseDto loginArtist(UserDto userDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmailId(),userDto.getPassword()));

        Artist artist=artistRepository.findByEmailId(userDto.getEmailId());

        ArtistResponseDto artistResponseDto=new ArtistResponseDto();

        artistResponseDto.setArtistId(artist.getArtistId());
        artistResponseDto.setName(artist.getName());
        artistResponseDto.setRating(artist.getRating());
        artistResponseDto.setGenre(artist.getGenre());
        artistResponseDto.setUpiId(artist.getUpiId());
        artistResponseDto.setEmailId(artist.getEmailId());
        artistResponseDto.setCountOfRatings(artist.getCountOfRatings());
        artistResponseDto.setTime(artist.getTime());
        artistResponseDto.setToken(jwtService.generateToken(artist.getEmailId(),artist.getRole()));
        return artistResponseDto;
    }

    public void updateProfile(ArtistResponseDto artistResponseDto)
    {
        Artist artist=artistRepository.findById(artistResponseDto.getArtistId()).orElseThrow(()->{
                log.error("No such artist exists");
                return new RuntimeException("No such artist exists");
        });

        artist.setEmailId(artistResponseDto.getEmailId());
        artist.setName(artistResponseDto.getName());
        artist.setUpiId(artistResponseDto.getUpiId());
        artist.setGenre(artistResponseDto.getGenre());
        artistRepository.save(artist);
    }
}
