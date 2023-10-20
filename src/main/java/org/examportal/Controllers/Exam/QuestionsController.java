package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.QuestionMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.DTOs.Response;
import org.examportal.Services.Exam.QuestionsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/questions")
@CrossOrigin("*")
public class QuestionsController {
    private final QuestionsService questionsService;

    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<BaseResponseDto<QuestionsDto>> addQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        log.info(String.format("addQuestion() - start %s", questionsDto));
        QuestionsDto savedQuestion = questionsService.addQuestion(questionsDto, principal.getName());
        Response<QuestionsDto> response = new Response<>(savedQuestion);
        response.setResponseCode(HttpStatus.CREATED.value());
        log.info(String.format("addQuestion() - end %s", savedQuestion));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<BaseResponseDto<QuestionsDto>> getQuestion(@PathVariable("questionId") Long questionId) {
        log.info(String.format("getQuestion() - start %d", questionId));
        QuestionsDto question = questionsService.findByQuestionId(questionId);
        Response<QuestionsDto> response = new Response<>(question);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("getQuestion() - end %s", question));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<QuestionsDto>>> getQuestions() {
        log.info("getQuestions() - start");
        Set<QuestionsDto> allQuestions = questionsService.findAllQuestions();
        Response<Set<QuestionsDto>> response = new Response<>(allQuestions, allQuestions.size(), allQuestions.isEmpty());
        response.setResponseCode(allQuestions.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (allQuestions.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getQuestions() - end %s", allQuestions));
        return new ResponseEntity<>(response, allQuestions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //get categories with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<QuestionsDto>>> getPaginatedQuestions(@RequestParam int page, @RequestParam int size) {
        log.info(String.format("getPaginatedQuestions() - start %d %d", page, size));
        Page<QuestionsDto> paginated = questionsService.findPaginated(PageRequest.of(page, size));
        Response<Page<QuestionsDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (response.getData().isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getPaginatedQuestions() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BaseResponseDto<Set<QuestionsDto>>> getQuestionsByCategory(@PathVariable Long categoryId) {
        log.info(String.format("getQuestionsByCategory() - start %d", categoryId));
        Set<QuestionsDto> questions = questionsService.findQuestionsByCategoryId(categoryId);
        Response<Set<QuestionsDto>> response = new Response<>(questions, questions.size(), questions.isEmpty());
        response.setResponseCode(questions.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (questions.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getQuestionsByCategory() - end %s", questions));
        return new ResponseEntity<>(response, questions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<QuestionsDto>> updateQuestion(@Valid @RequestBody QuestionsDto questionsDto, Principal principal) {
        log.info(String.format("updateQuestion() - start %s", questionsDto));
        QuestionsDto questions = questionsService.updateQuestions(questionsDto, principal.getName());
        Response<QuestionsDto> response = new Response<>(questions);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("updateQuestion() - end %s", questions));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<BaseResponseDto<String>> deleteQuestion(@PathVariable("questionId") Long questionId) {
        log.info(String.format("deleteQuestion() - start %d", questionId));
        questionsService.deleteQuestion(questionId);
        Response<String> response = new Response<>(QuestionMessages.QUESTION_DELETED);
        response.setResponseCode(HttpStatus.OK.value());
        log.info("deleteQuestion() - end");
        return ResponseEntity.ok(response);
    }

}
