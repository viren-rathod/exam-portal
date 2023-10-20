package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/option")
@CrossOrigin("*")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<BaseResponseDto<OptionDto>> addOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        log.info(String.format("addOption() - start  %s", optionDto));
        OptionDto options = optionService.create(optionDto, principal.getName());
        Response<OptionDto> response = new Response<>(options);
        response.setResponseCode(HttpStatus.CREATED.value());
        log.info(String.format("addOption() - end %s", options));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<BaseResponseDto<Set<OptionDto>>> getOptionsByQuestion(@PathVariable Long questionId) {
        log.info(String.format("getOptionsByQuestion() - start %d", questionId));
        Set<OptionDto> options = optionService.findAllByQuestion(questionId);
        Response<Set<OptionDto>> response = new Response<>(options, options.size(), options.isEmpty());
        response.setResponseCode(options.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (options.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getOptionsByQuestion() - end %s", options));
        return new ResponseEntity<>(response, options.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<OptionDto>> updateOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        log.info(String.format("updateOption() -  start %s", optionDto));
        OptionDto option = optionService.update(optionDto, principal.getName());
        Response<OptionDto> response = new Response<>(option);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("updateOption() - %s", option));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{optionId}")
    public ResponseEntity<BaseResponseDto<String>> deleteOption(@PathVariable("optionId") Long optionId) {
        log.info(String.format("deleteOption() - start %d", optionId));
        optionService.delete(optionId);
        Response<String> response = new Response<>(OptionMessages.OPTION_DELETED);
        response.setResponseCode(HttpStatus.OK.value());
        log.info("deleteOption() - end");
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<BaseResponseDto<String>> addAllOption(@Valid @RequestBody Set<OptionDto> optionDtoSet, Principal principal) {
        log.info(String.format("addAllOption() - start %s", optionDtoSet));
        String res = optionService.saveAll(optionDtoSet, principal.getName());
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        log.info(String.format("addAllOption() - end %s", res));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/saveAnswer")
    public ResponseEntity<BaseResponseDto<String>> setAnswer(@Valid @RequestBody OptionDto optionDto) {
        log.info(String.format("setAnswer() - start %s", optionDto));
        String res = optionService.saveAnswer(optionDto);
        Response<String> response = new Response<>(res);
        response.setResponseCode(HttpStatus.CREATED.value());
        log.info(String.format("setAnswer() - end %s", response));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}