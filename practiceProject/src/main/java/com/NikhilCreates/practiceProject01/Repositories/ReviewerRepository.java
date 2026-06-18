package com.NikhilCreates.practiceProject01.Repositories;

import com.NikhilCreates.practiceProject01.Entity.Artist;
import com.NikhilCreates.practiceProject01.Entity.Reviewer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewerRepository extends MongoRepository<Reviewer, String> {
    List<Reviewer> findByNameContainingIgnoreCase(String name);
    List<Reviewer> findByLevelGreaterThan(int level);
    boolean existsByEmailId(String emailId);
    Reviewer findByEmailId(String emailId);

}
