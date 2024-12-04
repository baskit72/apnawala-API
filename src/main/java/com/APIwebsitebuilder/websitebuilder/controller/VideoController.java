package com.APIwebsitebuilder.websitebuilder.controller;

import com.APIwebsitebuilder.websitebuilder.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @PostMapping("/video/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
        log.info("Received file: " + file.getOriginalFilename());
        try {
            String url = videoService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload video");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
