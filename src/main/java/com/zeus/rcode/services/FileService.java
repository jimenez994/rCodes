package com.zeus.rcode.services;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileService {
    Map<String, Object> uploadFile(MultipartFile multipartFile) throws IOException;
    void deleteFile(String imageURL) throws IOException;
}