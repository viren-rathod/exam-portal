package org.examportal.Services.Impl;

import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.CollegeDto;
import org.examportal.Models.College;
import org.examportal.Repositories.CollegeRepository;
import org.examportal.Services.CollegeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;
    private final ModelMapper modelMapper;

    public CollegeServiceImpl(CollegeRepository collegeRepository, ModelMapper modelMapper) {
        this.collegeRepository = collegeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CollegeDto create(CollegeDto collegeDto, String user) {
        College college = modelMapper.map(collegeDto, College.class);
        college.update(user);
        return modelMapper.map(collegeRepository.save(college), CollegeDto.class);
    }

    @Override
    public String saveAll(List<CollegeDto> colleges, String user) {
//        for (CollegeDto collegeDto : colleges) {
//            create(collegeDto, user);
//        }
        Set<College> newColleges = colleges.stream().map(collegeDto -> {
            College college = modelMapper.map(collegeDto, College.class);
            college.update(user);
            return college;
        }).collect(Collectors.toSet());
        collegeRepository.saveAll(newColleges);
        return UserMessages.ALL_COLLEGE_ADDED;
    }

    @Override
    public CollegeDto findById(Long id) {
        Optional<College> optionalCollege = collegeRepository.findById(id);
        return optionalCollege.map(college -> modelMapper.map(college, CollegeDto.class)).orElse(null);
    }

    @Override
    public Set<CollegeDto> findAll() {
        List<College> colleges = collegeRepository.findAll();
        return colleges.stream().map(college -> modelMapper.map(college, CollegeDto.class)).collect(Collectors.toSet());
    }
}
