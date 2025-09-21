package Codify.upload.service;

import Codify.upload.domain.ProcessingGroup;
import Codify.upload.exception.GroupIsNotReadyException;
import Codify.upload.repository.ProcessingRepository;
import Codify.upload.web.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static Codify.upload.domain.ProcessingGroup.GroupStatus.PROCESSING;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final ProcessingRepository processingRepository;
    private final RabbitTemplate rabbitTemplate;

    //첫 번째 ~ 마지막 이전
    @Transactional
    public void addFileUploadToGroup(Long assignmentId, Long submissionId) {
        // 기존 그룹 찾기 또는 새 그룹 생성
        Optional<ProcessingGroup> existingGroup = processingRepository.findGroupByAssignmentId(assignmentId);

        ProcessingGroup group;

        //그룹이 mongodb에 존재한다면
        if (existingGroup.isPresent()) {
            group = existingGroup.get();
            //submissionId 중복체크
            if (!group.getSubmissionIds().contains(submissionId)) {
                group.getSubmissionIds().add(submissionId);
            }
        } else {
            group = ProcessingGroup.createGroup(assignmentId, submissionId);
        }

        //db에 저장
        processingRepository.save(group);
        log.info("파일 업로드 후 mongodb에 저장 {}", group.getSubmissionIds());
    }

    //마지막 파일
    //group의 status를 COMPLETED로 변경 후 디비에 저장
    @Transactional
    public void addLastFileToGroup(Long assignmentId, Long submissionId) {
        addFileUploadToGroup(assignmentId, submissionId);

        Optional<ProcessingGroup> lastUploadGroup = processingRepository.findGroupByAssignmentId(assignmentId);

        if(lastUploadGroup.isPresent()) {
            ProcessingGroup group = lastUploadGroup.get();
            if (group.getStatus()== PROCESSING) {
                //processing을 complete로 변경
                group.completeProcessing();
                processGroup(group);
            }
        }
    }

    private void processGroup(ProcessingGroup group) {
        processingRepository.save(group);
        log.info("파일 업로드 모두 완료 후 mongodb에 저장 {}", group.getSubmissionIds());

        //group 객체로 메시지 생성
        MessageDto message = toProcessingMessage(group);
        log.info("group객체로 메시지 생성 {}", message.getSubmissionIds());

        //message를 RabbitMQ에 push
        rabbitTemplate.convertAndSend("codifyExchange", "file.upload", message);
        log.info("parsing queue에 push완료");
        log.info("submissionIds: {}", message.getSubmissionIds());
        log.info("assignmentId: {}", message.getAssignmentId());
        log.info("groupId: {}", message.getGroupId());
        log.info("messageType: {}", message.getMessageType());
        log.info("totalFiles: {}", message.getTotalFiles());


        // 처리된 그룹 삭제
        processingRepository.deleteById(group.getId());
        log.info("처리된 그룹 삭제 완료");
    }

    //group 정보를 메시지로 변환
    public MessageDto toProcessingMessage(ProcessingGroup group) {
        if (group.getStatus().equals(PROCESSING)) {
            throw new GroupIsNotReadyException();
        }

        String batchGroupId = String.format("assignment_%d_batch_%d",
                group.getAssignmentId(),
                System.currentTimeMillis());

        return new MessageDto(
                "FILE_UPLOADED",
                batchGroupId,
                group.getAssignmentId(),
                new ArrayList<>(group.getSubmissionIds()), // 복사본 반환
                group.getSubmissionIds().size(),
                LocalDateTime.now()
        );
    }


}
