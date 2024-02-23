package com.example.severdemo.configuration;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ServerConfig {
    private Integer http = 8081;
    private Integer https = 8082;
}
