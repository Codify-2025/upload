package Codify.upload.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MessageDto {
    private String messageType;
    private String groupId;
    private Long assignmentId;
    private List<Long> submissionIds;
    private Integer totalFiles;
    private LocalDateTime timestamp;

    // 메시지 타입 enum
    public enum MessageType {
        FILE_UPLOADED,
        PARSING_COMPLETED,
        SIMILARITY_COMPLETED
    }
}
