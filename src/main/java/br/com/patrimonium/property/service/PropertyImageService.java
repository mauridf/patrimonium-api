package br.com.patrimonium.property.service;

import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.entity.PropertyImage;
import br.com.patrimonium.property.repository.PropertyImageRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
}