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
public class UploadService {

    private final UploadRepository uploadRepository;
    private final ProcessingService processingService;

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

        boolean isLastFile = dto.getIsLastFile();

        //마지막 파일이 아니라면
        if (!isLastFile) {
            processingService.addFileUploadToGroup(submission.getAssignmentId(),submission.getStudentId());
        } else {
            //마지막 파일이라면
            processingService.addLastFileToGroup(submission.getAssignmentId(), submission.getStudentId());
        }

    }
}