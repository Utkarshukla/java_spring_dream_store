package com.dream.shop.service.image;

import com.dream.shop.dto.ImageDto;
import com.dream.shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    Image updateImage(MultipartFile file, Long imageId);
}
