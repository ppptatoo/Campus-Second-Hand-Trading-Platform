package com.springmvc.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/files/web/**")
    public ResponseEntity<Resource> getWebFile(javax.servlet.http.HttpServletRequest request) throws Exception {
        // Extract the path after /files/web/
        String requestURI = request.getRequestURI();
        String prefix = request.getContextPath() + "/files/web/";
        String relativePath = requestURI.substring(prefix.length());

        Path filePath = Path.of("/data/uploads/web").resolve(relativePath).normalize();
        logger.info("Requesting file: " + filePath);

        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            logger.warn("File not found or not readable: " + filePath);
            return ResponseEntity.notFound().build();
        }

        String contentType = "image/jpeg";
        try {
            String detected = Files.probeContentType(filePath);
            if (detected != null) {
                contentType = detected;
            }
        } catch (Exception e) {
            logger.debug("Could not detect content type", e);
        }

        logger.info("Serving file with content type: " + contentType);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}

