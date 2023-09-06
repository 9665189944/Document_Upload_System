package com.example.UploadDocument.Repository;

import com.example.UploadDocument.Bean.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Integer>{

}
