package com.vendasfinal.sistema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pega o caminho absoluto da pasta onde as imagens realmente são salvas
        Path uploadDir = Paths.get("src/main/resources/static/img");
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        
        // Se o Windows usar caminhos com \, o Java converte para /
        if (uploadPath.startsWith("/")) {
            registry.addResourceHandler("/img/**")
                    .addResourceLocations("file:" + uploadPath + "/");
        } else {
            registry.addResourceHandler("/img/**")
                    .addResourceLocations("file:/" + uploadPath + "/");
        }
    }
}