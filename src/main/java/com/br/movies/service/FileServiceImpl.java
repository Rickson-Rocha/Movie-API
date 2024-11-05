package com.br.movies.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        String filePath = path + File.separator + fileName;


        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream downloadFile(String path, String Filename) throws FileNotFoundException {
        String filePath = path + File.separator + Filename;
        return new FileInputStream(filePath);
    }
}
