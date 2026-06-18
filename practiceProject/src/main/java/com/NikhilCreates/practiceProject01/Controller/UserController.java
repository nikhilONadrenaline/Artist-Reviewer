package com.NikhilCreates.practiceProject01.Controller;

import com.NikhilCreates.practiceProject01.DTO.*;
import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Enums.Role;
import com.NikhilCreates.practiceProject01.Service.ArtistService;
import com.NikhilCreates.practiceProject01.Service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private ReviewerService reviewerService;


    @PostMapping("/signup/artist")
    public void artistSignup(@RequestBody ArtistDto artistDto)
    {
        Artist artist=new Artist();

        artist.setName(artistDto.getName());
        artist.setUpiId(artistDto.getUpiId());
        artist.setEmailId(artistDto.getEmail());
        artist.setGenre(artistDto.getGenre());
        artist.setRole(Role.ARTIST);
        artist.setPassword(artistDto.getPassword());
        artistService.addArtist(artist);
    }

    @PostMapping("/signup/reviewer")
    public void reviewerSignup(@RequestBody ReviewerDto reviewerDto)
    {
        Reviewer reviewer=new Reviewer();
        reviewer.setName(reviewerDto.getName());
        reviewer.setExpertise(reviewerDto.getExpertise());
        reviewer.setUpiId(reviewerDto.getUpiId());
        reviewer.setRole(Role.REVIEWER);
        reviewer.setEmailId(reviewerDto.getEmail());
        reviewer.setPassword(reviewerDto.getPassword());
        reviewerService.addReviewer(reviewer);
    }

    @PostMapping("signin/artist")
    public ArtistResponseDto artistSignin(@RequestBody UserDto userDto)
    {
        return artistService.loginArtist(userDto);
    }

    @PostMapping("signin/reviewer")
    public ReviewerResponseDTO reviewerSignin(@RequestBody UserDto userDto)
    {
        return reviewerService.loginReviewer(userDto);
    }
}
