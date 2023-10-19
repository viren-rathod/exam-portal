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
