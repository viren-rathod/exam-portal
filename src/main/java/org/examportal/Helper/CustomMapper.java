package org.examportal.Helper;

import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.Models.BaseEntity;
import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Exam;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class CustomMapper {
    private final CategoryRepository categoryRepository;

    public CustomMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<ExamDto, Exam> typeMap = modelMapper.createTypeMap(ExamDto.class, Exam.class);
        typeMap.addMappings(mapper -> {
            mapper.using(ctx -> {
                List<Long> categoryIds = (List<Long>) ctx.getSource();
                if (categoryIds != null) {
                    return categoryIds.stream()
                            .map(id -> categoryRepository.findById(id).orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                }
                return Collections.emptySet();
            }).map(ExamDto::getCategories, Exam::setCategories);
        });

        TypeMap<Exam, ExamDto> examToExamDtoTypeMap = modelMapper.createTypeMap(Exam.class, ExamDto.class);
        examToExamDtoTypeMap.addMappings(mapper -> {
            mapper.using(ctx -> {
                Set<Category> categories = (Set<Category>) ctx.getSource();
                if (categories != null) {
                    return categories.stream()
                            .map(BaseEntity::getId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                }
                return Collections.emptySet();
            }).map(Exam::getCategories, ExamDto::setCategories);
        });
        return modelMapper;
    }
}
