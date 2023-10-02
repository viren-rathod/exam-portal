package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.QuestionsDto;
import org.examportal.DTOs.Response;
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
    public ResponseEntity<BaseResponseDto<QuestionsDto>> addQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        QuestionsDto savedQuestion = questionsService.addQuestion(questionsDto, principal.getName());
        Response<QuestionsDto> response = new Response<>(savedQuestion);
        response.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<BaseResponseDto<QuestionsDto>> getQuestion(@PathVariable("questionId") Long questionId) {
        QuestionsDto question = questionsService.findByQuestionId(questionId);
        Response<QuestionsDto> response = new Response<>(question);
        response.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<QuestionsDto>>> getQuestions() {
        Set<QuestionsDto> allQuestions = questionsService.findAllQuestions();
        Response<Set<QuestionsDto>> response = new Response<>(allQuestions);
        response.setResponseCode(allQuestions.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (allQuestions.isEmpty()) response.setMessage("No content!");
        return new ResponseEntity<>(response, allQuestions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BaseResponseDto<Set<QuestionsDto>>> getQuestionsByCategory(@PathVariable Long categoryId) {
        Set<QuestionsDto> questions = questionsService.findQuestionsByCategoryId(categoryId);
        Response<Set<QuestionsDto>> response = new Response<>(questions);
        response.setResponseCode(questions.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (questions.isEmpty()) response.setMessage("No content!");
        return new ResponseEntity<>(response, questions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<QuestionsDto>> updateQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        QuestionsDto questions = questionsService.updateQuestions(questionsDto, principal.getName());
        Response<QuestionsDto> response = new Response<>(questions);
        response.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<BaseResponseDto<String>> deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionsService.deleteQuestion(questionId);
        Response<String> response = new Response<>("Question deleted successfully!");
        response.setResponseCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}
