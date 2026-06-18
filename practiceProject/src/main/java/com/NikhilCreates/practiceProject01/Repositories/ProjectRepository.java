package com.NikhilCreates.practiceProject01.Repositories;

import com.NikhilCreates.practiceProject01.Entity.Project;
import com.NikhilCreates.practiceProject01.Enums.ProjectStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// HELPS THE REVIEWER TO FIND BEST PROJECT FOR HIMSELF
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project>  findByGenre(String genre);
    List<Project> findByLevelMinGreaterThan(int levelMin);
    List<Project> findByLevelMaxLessThan(int levelMax);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByArtistId(String artistId);

}
