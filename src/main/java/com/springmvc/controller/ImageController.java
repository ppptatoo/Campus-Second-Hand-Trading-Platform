package com.springmvc.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 图片兜底控制器：确保 /images/web/* 可直接从容器持久化目录读取。
 * 说明：部分环境下 mvc:resources 对 file:/ 映射可能失效，此处兜底保证新上传图片可访问。
 */
@Controller
public class ImageController {

    @GetMapping("/images/web/{fileName:.+}")
    public ResponseEntity<Resource> serveWebImages(@PathVariable("fileName") String fileName,
                                                   HttpServletRequest request) throws Exception {
        return serveFromUploadsOrWebapp(fileName, request);
    }

    @GetMapping("/uploads/web/{fileName:.+}")
    public ResponseEntity<Resource> serveUploadsWebImages(@PathVariable("fileName") String fileName,
                                                          HttpServletRequest request) throws Exception {
        return serveFromUploadsOrWebapp(fileName, request);
    }

    private ResponseEntity<Resource> serveFromUploadsOrWebapp(String fileName, HttpServletRequest request) throws Exception {
        // 优先从持久化目录读取
        Path filePath = Path.of("/data/uploads/web").resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            // 兜底：尝试从运行时应用目录 /images/web/ 读取（存在符号链接也可生效）
            ServletContext context = request.getServletContext();
            String basePath = context.getRealPath("/images/web/");
            if (basePath != null) {
                Path altPath = Path.of(basePath).resolve(fileName).normalize();
                Resource altResource = new UrlResource(altPath.toUri());
                if (altResource.exists() && altResource.isReadable()) {
                    resource = altResource;
                    filePath = altPath;
                }
            }
        }

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = request.getServletContext().getMimeType(filePath.toString());
        if (contentType == null) {
            try {
                contentType = Files.probeContentType(filePath);
            } catch (Exception ignored) {
            }
        }
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
