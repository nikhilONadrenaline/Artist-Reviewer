package com.NikhilCreates.practiceProject01.Repositories;

import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
    List<Artist> findByNameContainingIgnoreCase(String name);
    boolean existsByEmailId(String emailId);
    Artist findByEmailId(String emailId);
}
