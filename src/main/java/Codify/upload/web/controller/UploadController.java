package Codify.upload.web.controller;

import Codify.upload.service.UploadService;
import Codify.upload.web.dto.SubmissionDto;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody SubmissionDto submissiondto) {
        String groupId = uploadService.saveUpload(submissiondto);
        return ResponseEntity.ok(groupId);
    }
}