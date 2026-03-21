package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.ExamMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.Exam.*;
import org.examportal.DTOs.Response;
import org.examportal.Services.Exam.StudentExamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student/exam")
@CrossOrigin("*")
@SecurityRequirement(name = "Bear Authentication")
@PreAuthorize("hasAuthority('USER')")
public class StudentExamController {

    private final StudentExamService studentExamService;

    public StudentExamController(StudentExamService studentExamService) {
        this.studentExamService = studentExamService;
    }

    // Get all active exams
    @GetMapping("/active")
    public ResponseEntity<Response<Page<ExamDto>>> getActiveExams(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false) String searchData,
            @RequestParam(required = false) Long userId) {
        log.info("getActiveExams() - start");
        Sort sort = sortOrder.equals("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Page<ExamDto> paginated = studentExamService.getActiveExams(PageRequest.of(page, size, sort), searchData, userId);
        Response<Page<ExamDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setMessage(response.getData().isEmpty() ? UserMessages.NO_CONTENT : ExamMessages.EXAM_FETCHED);
        log.info("getActiveExams() - end");
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    // Start exam - generates random questions
    @PostMapping("/{examId}/start")
    public ResponseEntity<Response<List<StudentExamQuestionDto>>> startExam(
            @PathVariable Long examId, Principal principal) {
        log.info("startExam() - start examId={}", examId);
        List<StudentExamQuestionDto> questions = studentExamService.startExam(examId, principal.getName());
        Response<List<StudentExamQuestionDto>> response = new Response<>(questions, questions.size(), questions.isEmpty());
        response.setResponseCode(HttpStatus.OK.value());
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_QUESTIONS_GENERATED);
        log.info("startExam() - end, {} questions generated", questions.size());
        return ResponseEntity.ok(response);
    }

    // Get assigned questions (resume in-progress exam)
    @GetMapping("/{examId}/questions")
    public ResponseEntity<Response<List<StudentExamQuestionDto>>> getExamQuestions(
            @PathVariable Long examId, Principal principal) {
        log.info("getExamQuestions() - start examId={}", examId);
        List<StudentExamQuestionDto> questions = studentExamService.getExamQuestions(examId, principal.getName());
        Response<List<StudentExamQuestionDto>> response = new Response<>(questions, questions.size(), questions.isEmpty());
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(ExamMessages.EXAM_QUESTIONS_FETCHED);
        log.info("getExamQuestions() - end");
        return ResponseEntity.ok(response);
    }

    // Submit answer for a single question
    @PostMapping("/answer")
    public ResponseEntity<Response<StudentExamQuestionDto>> submitAnswer(
            @RequestBody SubmitAnswerDto dto, Principal principal) {
        log.info("submitAnswer() - start examQuestionId={}", dto.getExamQuestionId());
        StudentExamQuestionDto result = studentExamService.submitAnswer(dto, principal.getName());
        Response<StudentExamQuestionDto> response = new Response<>(result);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(ExamMessages.ANSWER_SUBMITTED);
        log.info("submitAnswer() - end");
        return ResponseEntity.ok(response);
    }

    // Finish exam - evaluates all answers
    @PostMapping("/{examId}/finish")
    public ResponseEntity<Response<ExamResultDto>> finishExam(
            @PathVariable Long examId, Principal principal) {
        log.info("finishExam() - start examId={}", examId);
        ExamResultDto result = studentExamService.finishExam(examId, principal.getName());
        Response<ExamResultDto> response = new Response<>(result);
        response.setResponseCode(HttpStatus.OK.value());
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_FINISHED);
        log.info("finishExam() - end");
        return ResponseEntity.ok(response);
    }

    // Get result for a completed exam
    @GetMapping("/{examId}/result")
    public ResponseEntity<Response<ExamResultDto>> getExamResult(
            @PathVariable Long examId, Principal principal) {
        log.info("getExamResult() - start examId={}", examId);
        ExamResultDto result = studentExamService.getExamResult(examId, principal.getName());
        Response<ExamResultDto> response = new Response<>(result);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(ExamMessages.EXAM_RESULT_FETCHED);
        log.info("getExamResult() - end");
        return ResponseEntity.ok(response);
    }
}
