package Codify.upload.web.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class GetS3UrlDto {
    private String preSignedUrl;
    private String key;

    @Builder
    public GetS3UrlDto(String preSignedUrl, String key) {
        this.preSignedUrl = preSignedUrl;
        this.key = key;
    }
}