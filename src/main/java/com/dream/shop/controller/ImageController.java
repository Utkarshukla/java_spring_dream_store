package com.dream.shop.controller;

import com.dream.shop.dto.ImageDto;
import com.dream.shop.model.Image;
import com.dream.shop.response.ApiResponse;
import com.dream.shop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestBody List<MultipartFile> images,@RequestParam Long productId) {
        try {
            List<ImageDto> image = imageService.saveImage(images, productId);
            return ResponseEntity.status(HttpStatus.OK).body( new ApiResponse(
                    "Image uploaded successfully",
                    image,
                    null
            ));
//            return
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(
                            "Image upload failed",
                            null,
                            e.getMessage()
                    )
            );
        }
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile image, @PathVariable Long imageId) {
        try {
            Image updatedImage = imageService.updateImage(image, imageId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            "Image updated successfully",
                            updatedImage,
                            null
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(
                            "Image update failed",
                            null,
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.deleteImageById(imageId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            "Image deleted successfully",
                            null,
                            null
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(
                            "Image deletion failed",
                            null,
                            e.getMessage()
                    )
            );
        }
    }
}
