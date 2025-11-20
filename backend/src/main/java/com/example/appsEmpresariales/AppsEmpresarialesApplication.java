package com.example.appsEmpresariales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.example.appsEmpresariales")
@EnableJpaRepositories(basePackages = "com.example.appsEmpresariales.domain.Repository")
@EntityScan(basePackages = "com.example.appsEmpresariales.Persisten.Entity")
public class AppsEmpresarialesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppsEmpresarialesApplication.class, args);
    }
}