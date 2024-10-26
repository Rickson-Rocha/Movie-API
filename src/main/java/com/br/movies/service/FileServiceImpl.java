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
        // get name of the file
        String fileName = file.getOriginalFilename();
        // to get the file path
        String filePath = path + File.separator + fileName;

        // create a file object
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        // copy  file or upload  file to path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream downloadFile(String path, String Filename) throws FileNotFoundException {
        String filePath = path + File.separator + Filename;
        return new FileInputStream(filePath);
    }
}
