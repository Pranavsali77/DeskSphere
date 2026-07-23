package com.ithelpdesk.backend.storage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.bucket}")
    private String bucket;

    @Value("${supabase.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        String uploadUrl =
                supabaseUrl
                        + "/storage/v1/object/"
                        + bucket
                        + "/"
                        + fileName;

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(serviceKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> request =
                new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        uploadUrl,
                        HttpMethod.POST,
                        request,
                        String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Supabase upload failed");
        }

        return supabaseUrl
                + "/storage/v1/object/public/"
                + bucket
                + "/"
                + fileName;
    }
}