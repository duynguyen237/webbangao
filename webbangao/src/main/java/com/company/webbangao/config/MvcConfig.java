package com.company.webbangao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Lấy đường dẫn tuyệt đối tới thư mục images trong máy bạn
        Path uploadDir = Paths.get("./src/main/resources/static/images");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Cấu hình: Khi gọi link /images/** thì tìm trực tiếp trong thư mục đó
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}