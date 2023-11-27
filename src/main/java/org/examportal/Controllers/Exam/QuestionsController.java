package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.QuestionMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.Exam.OptionDto;
import org.examportal.DTOs.Exam.QuestionsDto;
import org.examportal.DTOs.Response;
import org.examportal.Helper.MapObject;
import org.examportal.Services.Exam.QuestionsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<BaseResponseDto<Map<String, Object>>> addQuestion(@Valid @RequestBody MapObject<QuestionsDto, List<OptionDto>> requestDto, Principal principal) {
        log.info(String.format("addQuestion() - start %s", requestDto));
        Map<String, Object> mp = questionsService.addQuestion(requestDto, principal.getName());
        Response<Map<String, Object>> response = new Response<>(mp);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(QuestionMessages.QUESTION_ADDED);
        log.info(String.format("addQuestion() - end %s", response));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<BaseResponseDto<Map<String, Object>>> getQuestion(@PathVariable("questionId") Long questionId) {
        log.info(String.format("getQuestion() - start %d", questionId));
        Map<String, Object> mp = questionsService.findByQuestionId(questionId);
        Response<Map<String, Object>> response = new Response<>(mp);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("getQuestion() - end %s", mp));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<Map<String, Object>>>> getQuestions() {
        log.info("getQuestions() - start");
        Set<Map<String, Object>> collect = questionsService.findAllQuestions();
        Response<Set<Map<String, Object>>> response = new Response<>(collect, collect.size());
        response.setResponseCode(collect.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (collect.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getQuestions() - end %s", collect));
        return new ResponseEntity<>(response, collect.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //get categories with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<Map<String, Object>>>> getPaginatedQuestions(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                                                     @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                                     @RequestParam(required = false) String searchData) {
        log.info(String.format("getPaginatedQuestions() - start %d %d %s %s %s", page, size, sortOrder, sortField, searchData));
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Map<String, Object>> paginated = questionsService.findPaginated(pageable, searchData);
        Response<Page<Map<String, Object>>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (response.getData().isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getPaginatedQuestions() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BaseResponseDto<Set<Map<String, Object>>>> getQuestionsByCategory(@PathVariable Long categoryId) {
        log.info(String.format("getQuestionsByCategory() - start %d", categoryId));
        Set<Map<String, Object>> questions = questionsService.findQuestionsByCategoryId(categoryId);
        Response<Set<Map<String, Object>>> response = new Response<>(questions, questions.size(), questions.isEmpty());
        response.setResponseCode(questions.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (questions.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getQuestionsByCategory() - end %s", questions));
        return new ResponseEntity<>(response, questions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<Map<String, Object>>> updateQuestion(@Valid @RequestBody MapObject<QuestionsDto, List<OptionDto>> requestDto, Principal principal) {
        log.info(String.format("updateQuestion() - start %s", requestDto));
        Map<String, Object> mp = questionsService.updateQuestions(requestDto, principal.getName());
        Response<Map<String, Object>> response = new Response<>(mp);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(QuestionMessages.QUESTION_UPDATED);
        log.info(String.format("updateQuestion() - end %s", mp));
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

    @GetMapping("/{questionId}/answer")
    public ResponseEntity<BaseResponseDto<OptionDto>> getAnswerByQuestion(@PathVariable("questionId") Long questionId) {
        log.info(String.format("getAnswerByQuestion() - start %d", questionId));
        OptionDto answer = questionsService.findAnswerByQuestionId(questionId);
        Response<OptionDto> response = new Response<>(answer);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("getAnswerByQuestion() - end %s", answer));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
