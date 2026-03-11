package br.com.patrimonium.property.service;

import br.com.patrimonium.property.dto.PropertyImageResponse;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.entity.PropertyImage;
import br.com.patrimonium.property.repository.PropertyImageRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/properties/";

    public void upload(UUID propertyId, MultipartFile file) throws IOException {

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + fileName;

        file.transferTo(new File(filePath));

        PropertyImage image = PropertyImage.builder()
                .property(property)
                .fileName(fileName)
                .filePath(filePath)
                .contentType(file.getContentType())
                .build();

        imageRepository.save(image);
    }

    public List<PropertyImageResponse> listByProperty(UUID propertyId) {

        return imageRepository.findByPropertyId(propertyId)
                .stream()
                .map(img -> new PropertyImageResponse(
                        img.getId(),
                        img.getFileName(),
                        "/api/v1/properties/images/" + img.getId(),
                        img.getContentType()
                ))
                .toList();
    }

    public Resource getImage(UUID imageId) throws MalformedURLException {

        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        return new UrlResource(new File(image.getFilePath()).toURI());
    }
}