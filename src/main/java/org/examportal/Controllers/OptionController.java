package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.OptionDto;
import org.examportal.Models.Exam.Options;
import org.examportal.Services.OptionService;
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
    public ResponseEntity<Options> addOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        Options options = optionService.create(optionDto, principal.getName());
        return new ResponseEntity<>(options, HttpStatus.CREATED);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<Set<Options>> getOptionsByQuestion(@PathVariable Long questionId) {
        Set<Options> options = optionService.findAllByQuestion(questionId);
        return ResponseEntity.ok(options);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<Options> updateOption(@Valid @RequestBody OptionDto optionDto, Principal principal) {
        return ResponseEntity.ok(optionService.update(optionDto, principal.getName()));
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{optionId}")
    public ResponseEntity<String> deleteOption(@PathVariable("optionId") Long optionId) {
        optionService.delete(optionId);
        return ResponseEntity.ok("{\"response\": \"Option deleted successfully!\"}");
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<String> addAllOption(@Valid @RequestBody Set<OptionDto> optionDtoSet, Principal principal) {
        String res = optionService.saveAll(optionDtoSet, principal.getName());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/saveAnswer")
    public ResponseEntity<String> setAnswer(@Valid @RequestBody OptionDto optionDto) {
        return ResponseEntity.ok(optionService.saveAnswer(optionDto));
    }
}