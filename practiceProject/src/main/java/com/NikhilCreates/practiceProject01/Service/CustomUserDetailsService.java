package com.NikhilCreates.practiceProject01.Service;

import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import com.NikhilCreates.practiceProject01.Repositories.ArtistRepository;
import com.NikhilCreates.practiceProject01.Repositories.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public  class CustomUserDetailsService
        implements UserDetailsService
{
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Artist artist =artistRepository.findByEmailId(email);

        if(artist != null)
        {
            return User.builder()
                .username(artist.getEmailId())
                .password(artist.getPassword())
                .authorities("ARTIST")
                .build();
        }

        Reviewer reviewer =reviewerRepository.findByEmailId(email);

        if(reviewer != null)
        {
            return User.builder()
                .username(reviewer.getEmailId())
                .password(reviewer.getPassword())
                .authorities("REVIEWER")
                .build();
        }

        throw new UsernameNotFoundException(
                "User not found"
        );
    }
}