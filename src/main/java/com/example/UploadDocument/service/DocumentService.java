package com.example.UploadDocument.service;

import com.example.UploadDocument.Bean.Document;
import com.example.UploadDocument.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository repository;

    public Document saveFile(MultipartFile file) {
        String docname = file.getOriginalFilename();
        try {
            Document doc = new Document(docname, file.getContentType(), file.getBytes());
            return repository.save(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Document> getFile(Integer fileId) {
        return repository.findById(fileId);
    }

    public Page<Document> findPaginated(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return repository.findAll(pageable);
    }


    public void deleteDoc(int id) {
        repository.deleteById(id);
    }


}