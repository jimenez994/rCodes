package com.zeus.rcode.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileService{
	@Autowired
    private Cloudinary cloudinary;

	@SuppressWarnings("unchecked")
	@Override
    public Map<String, Object> uploadFile(MultipartFile multipartFile) throws IOException {
		Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),Map.of("public_id", UUID.randomUUID().toString()));

        String publicId = uploadResult.get("public_id").toString();
        String imageUrl = uploadResult.get("url").toString();

        return uploadResult;
    }
    
    
    
    public void deleteFile(String imageURL) throws IOException {
    	Map<String, String> options = new HashMap<>();
    	options.put("invalidate", "true");
    	cloudinary.uploader().destroy(imageURL, options);
    	
    	try {
            ApiResponse response = cloudinary.api().resource(imageURL, ObjectUtils.emptyMap());

            if (response.get("error") != null) {
                System.out.println("Asset was successfully deleted.");
            } else {
                System.out.println("Asset was not deleted.");
            }
        } catch (Exception e) {
            System.out.println("Asset was successfully deleted.");
        }


    }
    
}