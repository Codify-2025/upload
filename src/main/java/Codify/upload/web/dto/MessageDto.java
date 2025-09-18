package Codify.upload.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {

    private static final long serialVersionUID = 123L;

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
