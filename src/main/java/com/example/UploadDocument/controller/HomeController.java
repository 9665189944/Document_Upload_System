package com.example.UploadDocument.controller;

import com.example.UploadDocument.Bean.Document;
import com.example.UploadDocument.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private DocumentService service;


    @GetMapping("/")
    public String getInfo(Model model) {

        return findPaginated(1,model);
    }
    @GetMapping("/view/{pageNo}")
    public String findPaginated(@PathVariable ( value = "pageNo" ,required = false) Integer pageNo, Model model)
    {
           int pageSize=5;

        Page<Document> page = service.findPaginated(pageNo,pageSize);
        List<Document> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);

        return "doc";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            service.saveFile(file);

        }
        return "redirect:/";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
       Document applicant = service.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(applicant.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+applicant.getDocName()+"\"")
                .body(new ByteArrayResource(applicant.getData()));
    }


    @GetMapping("/deleteDocument/{id}")
    public String deleteDoc(@PathVariable int id)
    {
        service.deleteDoc(id);
        return "redirect:/";
    }
}
