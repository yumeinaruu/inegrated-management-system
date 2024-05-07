package com.yumeinaruu.iis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(info = @Info(
        title = "Integrated Management System",
        description = "Description: for Universities",
        contact = @Contact(name = "Lisavy Stanislau",
                url = "https://github.com/yumeinaruu",
                email = "stas.lisavoy@icloud.com")
))
@SpringBootApplication
@EnableCaching
public class IisApplication {

    public static void main(String[] args) {
        SpringApplication.run(IisApplication.class, args);
    }

}
