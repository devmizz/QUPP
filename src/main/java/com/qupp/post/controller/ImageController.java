package com.qupp.post.controller;

import com.qupp.post.dto.response.ResponseImage;
import com.qupp.post.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "image", description = "이미지 API")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "이미지 등록 , 접근 제한 API", description = "이미지 등록", tags = "image")
    @PostMapping("/image")
    public ResponseEntity<ResponseImage> register(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Parameter(name = "directory", description = "사진이 저장될 폴더 경로", example = "question or answer")
            @RequestParam(value = "directory") String directory
    ) {
        String url = new String();
        if(directory.equals("question") || directory.equals("answer")) {
            url = imageService.upload(image, directory);
        }

        ResponseImage responseImage = new ResponseImage();
        responseImage.setUrl(url);

        return ResponseEntity.ok(responseImage);
    }
}
