package Codify.upload.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Document(collection = "codify_processing")
public class ProcessingGroup {
    @Id
    private String id;

    private Long assignmentId;

    private List<Long> submissionIds = new ArrayList<>();

    @Indexed
    private GroupStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum GroupStatus {
        PROCESSING,
        COMPLETED
    }

    public static ProcessingGroup createGroup(Long assignmentId, Long firstSubmissionId) {

        ProcessingGroup group = new ProcessingGroup();

        group.assignmentId = assignmentId;
        group.submissionIds.add(firstSubmissionId);
        group.status = GroupStatus.PROCESSING;
        group.createdAt = LocalDateTime.now();

        return group;
    }

    public void completeProcessing() {
        if (this.status != GroupStatus.PROCESSING) {
            throw new IllegalStateException("Can only complete from PROCESSING status");
        }
        this.status = GroupStatus.COMPLETED;
    }

}
