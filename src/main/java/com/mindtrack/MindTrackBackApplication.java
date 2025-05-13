package com.mindtrack;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

@SpringBootApplication
public class MindTrackBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindTrackBackApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
