package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.CandidateDto;
import org.examportal.DTOs.Response;
import org.examportal.Models.Role;
import org.examportal.Models.User;
import org.examportal.Services.CandidateService;
import org.examportal.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "REST APIs for Candidate")
@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class CandidateController {

    private final UserService userService;
    private final CandidateService candidateService;

    public CandidateController(UserService userService, CandidateService candidateService) {
        this.userService = userService;
        this.candidateService = candidateService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<BaseResponseDto<String>> addCandidate(@Valid @RequestBody CandidateDto candidateDto, Principal principal) {
        candidateService.create(candidateDto, principal.getName());
        Response<String> response = new Response<>(null);
        response.setEmpty(false);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(UserMessages.CANDIDATE_SAVED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/getCurrentUser")
    public ResponseEntity<BaseResponseDto<Map<String, Object>>> getCurrentUser(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Response<Map<String, Object>> response = new Response<>();
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            response.setData(map);
            response.setMessage(UserMessages.USER_FOUND);
            response.setResponseCode(HttpStatus.OK.value());
            response.getData().put("id", user.getId());
//            response.getData().put("created_at", user.getCreated_at());
//            response.getData().put("created_by", user.getCreated_by());
            response.getData().put("username", user.getUsername());
            response.getData().put("email", user.getEmail());
            response.getData().put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        } else {
            response.setResponseCode(HttpStatus.NO_CONTENT.value());
            response.setMessage(UserMessages.USER_NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }
}
