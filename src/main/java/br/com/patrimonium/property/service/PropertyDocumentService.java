package br.com.patrimonium.property.service;

import br.com.patrimonium.property.dto.PropertyDocumentResponse;
import br.com.patrimonium.property.entity.PropertyDocument;
import br.com.patrimonium.property.entity.PropertyEntity;
import br.com.patrimonium.property.repository.PropertyDocumentRepository;
import br.com.patrimonium.property.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyDocumentService {

    private final PropertyRepository propertyRepository;
    private final PropertyDocumentRepository repository;

    private static final String UPLOAD_DIR = "uploads/documents/";

    public void upload(UUID propertyId, String type, MultipartFile file) throws Exception {

        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + fileName;

        file.transferTo(new File(filePath));

        PropertyDocument document = PropertyDocument.builder()
                .property(property)
                .documentType(type)
                .fileName(fileName)
                .filePath(filePath)
                .contentType(file.getContentType())
                .build();

        repository.save(document);
    }

    public Resource download(UUID documentId) throws MalformedURLException {

        PropertyDocument doc = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return new UrlResource(new File(doc.getFilePath()).toURI());
    }

    public List<PropertyDocumentResponse> listByProperty(UUID propertyId) {

        return repository.findByPropertyId(propertyId)
                .stream()
                .map(doc -> new PropertyDocumentResponse(
                        doc.getId(),
                        doc.getFileName(),
                        doc.getDocumentType(),
                        "/api/v1/properties/documents/" + doc.getId(),
                        doc.getContentType()
                ))
                .toList();
    }
}