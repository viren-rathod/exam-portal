package org.examportal;

import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Models.Exam.Exam;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Collectors;

@SpringBootApplication
public class ExamPortalApplication {
    private final CategoryRepository categoryRepository;

    public ExamPortalApplication(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamPortalApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/").allowedOrigins("http://localhost:9191").allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Define a custom mapping from ExamDto.numbers to Exam.categories
        modelMapper.addMappings(new PropertyMap<ExamDto, Exam>() {
            @Override
            protected void configure() {
                map().setCategories(source.getCategories().stream().map(categoryId -> categoryRepository.findById(categoryId).get()).collect(Collectors.toSet()));
            }
        });

        return modelMapper;
    }
}
