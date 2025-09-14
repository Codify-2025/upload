package Codify.upload.web.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDto {
    private Long assignmentId;
    private String fileName;
    private Long week;
    private LocalDateTime submissionDate;
    private Long studentId;
    private String studentName;
    private String s3Key;
    private Boolean isLastFile;
}