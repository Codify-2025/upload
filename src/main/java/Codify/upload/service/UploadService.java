package Codify.upload.service;

import Codify.upload.domain.Submission;
import Codify.upload.repository.UploadRepository;
import Codify.upload.web.dto.SubmissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class UploadService {

    private final UploadRepository uploadRepository;

    @Transactional
    public void saveUpload(SubmissionDto dto) {
        final Submission submission = Submission.builder()
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