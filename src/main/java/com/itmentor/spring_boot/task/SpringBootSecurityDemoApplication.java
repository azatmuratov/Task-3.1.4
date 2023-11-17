package com.itmentor.spring_boot.task;


import com.itmentor.spring_boot.task.config.RestConfig;
import com.itmentor.spring_boot.task.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
@Import(RestConfig.class)
public class SpringBootSecurityDemoApplication implements CommandLineRunner {

    private final RestTemplateService restTemplateController;

    @Autowired
    public SpringBootSecurityDemoApplication(RestTemplateService restTemplateController) {
        this.restTemplateController = restTemplateController;
    }



    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        restTemplateController.getAllUser();
        restTemplateController.createUser();
        restTemplateController.updateUser();
        restTemplateController.deleteUserById(3L);

    }
}