package com.dream.shop.service.image;

import com.dream.shop.dto.ImageDto;
import com.dream.shop.model.Image;
import com.dream.shop.model.Product;
import com.dream.shop.repository.ImageRepository;
import com.dream.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Image Not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()->{throw  new IllegalArgumentException("Image not found");});
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product not found"));
        List<ImageDto> imageDto = new ArrayList<>();

        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFilName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDonwloadUrl = "/api/v1/images/";
                String downloadUrl = buildDonwloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDonwloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto dto = new ImageDto();
                dto.setImageId(savedImage.getId());
                dto.setImageName(savedImage.getFilName());
                dto.setDownloadUri(savedImage.getDownloadUrl());
                imageDto.add(dto);


            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFilName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }
}
