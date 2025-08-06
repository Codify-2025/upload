package Codify.upload.service;

import Codify.upload.domain.Submission;
import Codify.upload.repository.UploadRepository;
import Codify.upload.web.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadRepository uploadRepository;

    public void saveUpload(SubmissionDto dto) {
        Submission submission = Submission.builder()
                .assignmentId(dto.getAssignmentId())
                .fileName(dto.getFileName())
                .week(dto.getWeek())
                .submissionDate(LocalDateTime.now())
                .studentId(dto.getStudentId())
                .studentName(dto.getStudentName())
                .s3Key(dto.getS3Key())
                .build();

        uploadRepository.save(submission);
    }
}