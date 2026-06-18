package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.DTO.ReviewerResponseDTO;
import com.NikhilCreates.practiceProject01.DTO.UserDto;
import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.Role;
import com.NikhilCreates.practiceProject01.Repositories.ArtistRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void addReviewer(Reviewer reviewer){

        if(artistRepository.existsByEmailId(reviewer.getEmailId()) || reviewerRepository.existsByEmailId(reviewer.getEmailId()))
            throw new BadCredentialsException("Email Id already registered");

        reviewer.setExp(0);
        reviewer.setCompletedReviews(0);
        reviewer.setLevel(1);
        reviewer.setRating(5);
        reviewer.setWallet(0);
        reviewer.setPassword(bCryptPasswordEncoder.encode(reviewer.getPassword()));
        reviewer.setJoinedAt(LocalDateTime.now());
        reviewerRepository.save(reviewer);
    }

    public void updateLevel(String reviewerId)  //CALLED AS SOON AS A REVIEW IS APPROVED
    {
        Reviewer reviewer=reviewerRepository.findById(reviewerId).orElseThrow(
                ()->new RuntimeException("No reviewer found")
        );

        int currExp=reviewer.getExp();
        int currLevel= reviewer.getLevel();

        if(currExp>=currLevel*10) {
            currExp%=(currLevel*10);
            currLevel++;
        }
        reviewer.setLevel(currLevel);
        reviewer.setExp(currExp);
        reviewerRepository.save(reviewer);
    }

    public void withdrawMoney(String reviewerId, int amount) // REVIEWER WITHDRAWS MONEY
    {
//        ObjectId reviewerId=new ObjectId(reviewerIdString);
        Reviewer reviewer=reviewerRepository.findById(reviewerId).orElseThrow(
                ()->new RuntimeException("No reviewer found")
        );

        int currBalance=reviewer.getWallet();

        if(amount>currBalance)
        {
            throw new RuntimeException("In sufficient amount in wallet!!");
        }
        else {
            // Withrawal Gateway initiated
            paymentService.payment(reviewer.getUpiId(), amount);
            currBalance-=amount;
            reviewer.setWallet(currBalance);
        }
        reviewerRepository.save(reviewer);
    }
    public ReviewerResponseDTO loginReviewer(UserDto userDto)
    {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmailId(),userDto.getPassword()
                )
        );

        Reviewer reviewer=reviewerRepository.findByEmailId(userDto.getEmailId());


        ReviewerResponseDTO reviewerResponseDTO=new ReviewerResponseDTO();

        reviewerResponseDTO.setId(reviewer.getId());
        reviewerResponseDTO.setCompletedReviews(reviewer.getCompletedReviews());
        reviewerResponseDTO.setExp(reviewer.getExp());
        reviewerResponseDTO.setLevel(reviewer.getLevel());
        reviewerResponseDTO.setName(reviewer.getName());
        reviewerResponseDTO.setWallet(reviewer.getWallet());
        reviewerResponseDTO.setExpertise(reviewer.getExpertise());
        reviewerResponseDTO.setEmailId(reviewer.getEmailId());
        reviewerResponseDTO.setRating(reviewer.getRating());
        reviewerResponseDTO.setRateArtist(reviewer.getRateArtist());
        reviewerResponseDTO.setJoinedAt(reviewer.getJoinedAt());
        reviewerResponseDTO.setUpiId(reviewer.getUpiId());
        reviewerResponseDTO.setToken(jwtService.generateToken(reviewer.getEmailId(), reviewer.getRole()));
        return reviewerResponseDTO;
    }

    public Reviewer findById(String reviewerId)
    {
//        ObjectId objectId=new ObjectId(reviewerId);
        return reviewerRepository.findById(reviewerId).orElseThrow(
                ()->new RuntimeException("No reviewer Found")
        );
    }
}
