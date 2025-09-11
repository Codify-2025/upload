package Codify.upload.service;

import Codify.upload.exception.assignment.AssignmentAccessDeniedException;
import Codify.upload.repository.AssignmentRepository;
import Codify.upload.web.dto.GetS3UrlDto;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3Client;
    private final AssignmentRepository assignmentRepository;

    @Value("${cloud.aws.s3.bucket}") // Spring의 설정 파일(yml)에서 값을 읽어오는 어노테이션
    private String bucket;

    public GetS3UrlDto getPostS3Url(UUID userUuid, Long assignmentId, String filename) {
        if(!assignmentRepository.existsByUserUuidAndAssignmentId(userUuid, assignmentId))
        {
            throw new AssignmentAccessDeniedException();
        }

        String key = "uploads/" + assignmentId + "/" + UUID.randomUUID() + "/" + filename; // S3 Key(저장 경로)를 생성, randomUUID -> 중복 방지, 항상 새로운 파일로 저장
        Date expiration = getExpiration(6); // 6시간

        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        URL url = amazonS3Client.generatePresignedUrl(req);

        return GetS3UrlDto.builder()
                .preSignedUrl(url.toString())
                .key(key)
                .build();

    }

    private Date getExpiration(int hours) {
        long now = System.currentTimeMillis();
        return new Date(now + 1000L * 60 * 60 * hours);
    }
}