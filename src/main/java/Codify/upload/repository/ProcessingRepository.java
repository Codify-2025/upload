package Codify.upload.repository;

import Codify.upload.domain.ProcessingGroup;
import Codify.upload.domain.Submission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProcessingRepository extends MongoRepository<ProcessingGroup, String> {

    @Query("{'assignmentId': ?0, 'status': 'PROCESSING'}")
    Optional<ProcessingGroup> findGroupByAssignmentId(Long assignmentId);

}
