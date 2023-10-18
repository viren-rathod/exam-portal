package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.Constants.Exam.OptionMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Response;
import org.examportal.Services.Exam.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/option")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<BaseResponseDto<OptionDto>> addOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        OptionDto options = optionService.create(optionDto, principal.getName());
        Response<OptionDto> response = new Response<>(options);
        response.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<BaseResponseDto<Set<OptionDto>>> getOptionsByQuestion(@PathVariable Long questionId) {
        Set<OptionDto> options = optionService.findAllByQuestion(questionId);
        Response<Set<OptionDto>> response = new Response<>(options, options.size(), options.isEmpty());
        response.setResponseCode(options.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (options.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        return new ResponseEntity<>(response, options.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<OptionDto>> updateOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        OptionDto option = optionService.update(optionDto, principal.getName());
        Response<OptionDto> response = new Response<>(option);
        response.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{optionId}")
    public ResponseEntity<BaseResponseDto<String>> deleteOption(@PathVariable("optionId") Long optionId) {
        optionService.delete(optionId);
        Response<String> response = new Response<>(OptionMessages.OPTION_DELETED);
        response.setResponseCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<BaseResponseDto<String>> addAllOption(@Valid @RequestBody Set<OptionDto> optionDtoSet, Principal principal) {
        String res = optionService.saveAll(optionDtoSet, principal.getName());
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/saveAnswer")
    public ResponseEntity<BaseResponseDto<String>> setAnswer(@Valid @RequestBody OptionDto optionDto) {
        String res = optionService.saveAnswer(optionDto);
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}