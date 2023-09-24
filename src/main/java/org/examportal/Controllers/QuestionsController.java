package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.QuestionsDto;
import org.examportal.Models.Exam.Questions;
import org.examportal.Services.QuestionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
    private final QuestionsService questionsService;

    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Questions> addQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        Questions savedQuestion = questionsService.addQuestion(questionsDto, principal.getName());
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Questions> getQuestion(@PathVariable("questionId") Long questionId) {
        Questions question = questionsService.findByQuestionId(questionId);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/")
    public ResponseEntity<Set<Questions>> getQuestions() {
        return ResponseEntity.ok(this.questionsService.findAllQuestions());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Set<Questions>> getQuestionsByCategory(@PathVariable Long categoryId) {
        Set<Questions> questions = questionsService.findQuestionsByCategoryId(categoryId);
        return ResponseEntity.ok(questions);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<Questions> updateQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        return ResponseEntity.ok(questionsService.updateQuestions(questionsDto, principal.getName()));
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionsService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully!");
    }

}
