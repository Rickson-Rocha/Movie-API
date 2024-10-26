package com.br.movies.controllers;

import com.br.movies.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")

public class FileController {
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
       String uploadedFileName =  fileService.uploadFile(path,file);
       return ResponseEntity.ok().body("File uploaded: " + uploadedFileName);

    }

    @GetMapping("/{fileName}")
        public void downloadFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile =  fileService.downloadFile(path,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile,response.getOutputStream());

        }
    }


