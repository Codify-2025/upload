package Codify.upload.web.controller;

import Codify.upload.exception.dev.DevPasswordUnauthorizedException;
import Codify.upload.service.S3Service;
import Codify.upload.web.dto.GetS3UrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestHeader("Dev-Password") String password,
            @RequestParam Long userId,
            @RequestParam String filename) { // 임시 대체, Spring Security 적용 후 변경 필요

        if (!devPassword.equals(password)) {
            throw new DevPasswordUnauthorizedException();
        }
        GetS3UrlDto getS3UrlDto = s3Service.getPostS3Url(userId, filename);
        return ResponseEntity.ok(getS3UrlDto);
    }
}