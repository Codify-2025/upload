package Codify.upload.repository;

import Codify.upload.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    Boolean existsByUserUuidAndAssignmentId(UUID userUuid, Long assignmentId);

}
