package org.examportal.Services;

import org.examportal.DTOs.CollegeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CollegeService {
    CollegeDto create(CollegeDto collegeDto, String user);

    String saveAll(List<CollegeDto> colleges, String user);

    CollegeDto findById(Long id);

    Set<CollegeDto> findAll();
}
