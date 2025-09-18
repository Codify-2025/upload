package Codify.upload.web.controller;

import Codify.upload.service.S3Service;
import Codify.upload.web.dto.GetS3UrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class S3Controller {

    private final S3Service s3Service;
    @Value("${upload.dev-password}")
    private String devPassword;

    // Presigned URL 발급 API
    @GetMapping("/presigned-url")
    public ResponseEntity<GetS3UrlDto> getPostS3Url(
            @RequestHeader("USER-UUID") String userUuidHeader,
            @RequestParam String filename,
            @RequestParam Long assignmentId) { // 임시 대체, Spring Security 적용 후 변경 필요

        final UUID userUuid = UUID.fromString(userUuidHeader);
        GetS3UrlDto getS3UrlDto = s3Service.getPostS3Url(userUuid,assignmentId,filename);
        return ResponseEntity.ok(getS3UrlDto);
        //test
    }
}