package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.ExamDto;
import org.examportal.Services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Autowired
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    //add exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ExamDto> addExam(@Valid @RequestBody ExamDto examDto) {
        ExamDto savedExam = examService.addExam(examDto);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    //get category
    @GetMapping("/{examId}")
    public ResponseEntity<ExamDto> getExam(@PathVariable("examId") Long examId) {
        ExamDto examDto = examService.getExam(examId);
        return ResponseEntity.ok(examDto);
    }

    //    get all categories
    @GetMapping("/")
    public ResponseEntity<Set<ExamDto>> getExams() {
        return ResponseEntity.ok(this.examService.getExams());
    }

    //    update category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<ExamDto> updateExam(@Valid @RequestBody ExamDto exam) {
        return ResponseEntity.ok(examService.updateExam(exam));
    }

    //delete exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{examId}")
    public ResponseEntity<String> deleteExam(@PathVariable("examId") Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok("Exam deleted successfully!");
    }
}
