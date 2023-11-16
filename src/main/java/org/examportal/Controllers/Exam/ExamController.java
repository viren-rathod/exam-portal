package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.ExamMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.Exam.ExamDto;
import org.examportal.DTOs.Response;
import org.examportal.Services.Exam.ExamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/exam")
@CrossOrigin("*")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    //add exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<BaseResponseDto<ExamDto>> addExam(@Valid @RequestBody ExamDto examDto, Principal principal) {
        log.info(String.format("addExam() - start %s", examDto));
        ExamDto savedExam = examService.addExam(examDto, principal.getName());
        Response<ExamDto> response = new Response<>(savedExam);
        response.setResponseCode(HttpStatus.CREATED.value());
        log.info(String.format("addExam() - end %s", savedExam));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get category
    @GetMapping("/{examId}")
    public ResponseEntity<BaseResponseDto<ExamDto>> getExam(@PathVariable("examId") Long examId) {
        log.info(String.format("getExam() - start %d", examId));
        ExamDto examDto = examService.getExam(examId);
        Response<ExamDto> response = new Response<>(examDto);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("getExam() - end %s", examDto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //    get all categories
    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<ExamDto>>> getExams() {
        log.info("getExams() - start");
        Set<ExamDto> exams = examService.getExams();
        Response<Set<ExamDto>> response = new Response<>(exams, exams.size(), exams.isEmpty());
        response.setResponseCode(exams.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (exams.isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getExams() - end %s ", exams));
        return new ResponseEntity<>(response, exams.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //get exam with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<ExamDto>>> getPaginatedExams(@RequestParam int page,
                                                                     @RequestParam int size,
                                                                     @RequestParam String sortOrder,
                                                                     @RequestParam String sortField,
                                                                     @RequestParam(required = false) String searchData) {
        log.info(String.format("getPaginatedExams() - start %d %d %s %s %s", page, size, sortOrder, sortField, searchData));
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ExamDto> paginated = examService.findPaginated(pageable, searchData);
        Response<Page<ExamDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (response.getData().isEmpty()) response.setMessage(UserMessages.NO_CONTENT);
        log.info(String.format("getPaginatedExams() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }


    //    update category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<ExamDto>> updateExam(@Valid @RequestBody ExamDto exam, Principal principal) {
        log.info(String.format("updateExam() - start %s", exam));
        ExamDto examDto = examService.updateExam(exam, principal.getName());
        Response<ExamDto> response = new Response<>(examDto);
        response.setResponseCode(HttpStatus.OK.value());
        log.info(String.format("updateExam() - end %s", examDto));
        return ResponseEntity.ok(response);
    }

    //delete exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{examId}")
    public ResponseEntity<BaseResponseDto<String>> deleteExam(@PathVariable("examId") Long examId) {
        log.info(String.format("deleteExam() - start %d", examId));
        examService.deleteExam(examId);
        Response<String> response = new Response<>(ExamMessages.EXAM_DELETED);
        response.setResponseCode(HttpStatus.OK.value());
        log.info("deleteExam() - end");
        return ResponseEntity.ok(response);
    }
}
