package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.CollegeDto;
import org.examportal.DTOs.Response;
import org.examportal.Services.CollegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Tag(name = "APIs for Colleges")
@RestController
@RequestMapping("/api/college")
@CrossOrigin("*")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BaseResponseDto<CollegeDto>> addCollege(@Valid @RequestBody CollegeDto collegeDto, Principal principal) {
        CollegeDto college = collegeService.create(collegeDto, principal.getName());
        Response<CollegeDto> response = new Response<>(college);
        response.setEmpty(false);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(UserMessages.COLLEGE_ADDED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<CollegeDto>>> getAllColleges() {
        Set<CollegeDto> colleges = collegeService.findAll();
        Response<Set<CollegeDto>> response = new Response<>(colleges, colleges.size(), colleges.isEmpty());
        response.setResponseCode(colleges.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (colleges.isEmpty()) response.setMessage("No colleges found");
        return new ResponseEntity<>(response, colleges.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-all")
    public ResponseEntity<BaseResponseDto<String>> addAllCollege(@Valid @RequestBody List<CollegeDto> collegeDtos, Principal principal) {
        String res = collegeService.saveAll(collegeDtos, principal.getName());
        Response<String> response = new Response<>();
        response.setEmpty(false);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(res);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
