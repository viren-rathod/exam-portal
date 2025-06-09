package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.ExamMessages;
import org.examportal.Constants.Status;
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
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_CREATED);
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
        response.setMessage(ExamMessages.EXAM_FETCHED);
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
        response.setMessage(exams.isEmpty() ? UserMessages.NO_CONTENT : ExamMessages.EXAM_FETCHED);
        log.info(String.format("getExams() - end %s ", exams));
        return new ResponseEntity<>(response, exams.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //get exam with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<ExamDto>>> getPaginatedExams(@RequestParam(required = false, defaultValue = "0") int page,
                                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                                     @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                     @RequestParam(required = false) String searchData,
                                                                     @RequestParam(required = false) Status status
    ) {
        log.info(String.format("getPaginatedExams() - start %d %d %s %s %s", page, size, sortOrder, sortField, searchData));
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ExamDto> paginated = examService.findPaginated(pageable, searchData, status);
        Response<Page<ExamDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setMessage(response.getData().isEmpty() ? UserMessages.NO_CONTENT : ExamMessages.EXAM_FETCHED);
        log.info(String.format("getPaginatedExams() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    // update exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<ExamDto>> updateExam(@Valid @RequestBody ExamDto exam, Principal principal) {
        log.info(String.format("updateExam() - start %s", exam));
        ExamDto examDto = examService.updateExam(exam, principal.getName());
        Response<ExamDto> response = new Response<>(examDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_UPDATED);
        log.info(String.format("updateExam() - end %s", examDto));
        return ResponseEntity.ok(response);
    }

    // start exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/start")
    public ResponseEntity<BaseResponseDto<ExamDto>> startExam(@Valid @RequestBody Long examId, Principal principal) {
        log.info(String.format("startExam() - start %d", examId));
        ExamDto examDto = examService.startExam(examId, principal.getName());
        Response<ExamDto> response = new Response<>(examDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_STARTED);
        log.info(String.format("startExam() - end %s", examDto));
        return ResponseEntity.ok(response);
    }

    // stop exam
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/stop")
    public ResponseEntity<BaseResponseDto<ExamDto>> stopExam(@Valid @RequestBody Long examId, Principal principal) {
        log.info(String.format("stopExam() - start %d", examId));
        ExamDto examDto = examService.stopExam(examId, principal.getName());
        Response<ExamDto> response = new Response<>(examDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_STOPPED);
        log.info(String.format("stopExam() - end %s", examDto));
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
        response.setToast(true);
        response.setMessage(ExamMessages.EXAM_DELETED);
        log.info("deleteExam() - end");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<Response<Page<ExamDto>>> getAllActiveExams(@RequestParam(required = false, defaultValue = "0") int page,
                                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                                     @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                     @RequestParam(required = false) String searchData,
                                                                     @RequestParam(required = false) Long userId) {
        log.info(String.format("getAllActiveExams() - start %d %d %s %s %s", page, size, sortOrder, sortField, searchData));
        Sort sort = sortOrder.equals("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        Page<ExamDto> paginated = examService.getAllActiveExams(PageRequest.of(page, size, sort), searchData, userId);
        Response<Page<ExamDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setMessage(response.getData().isEmpty() ? UserMessages.NO_CONTENT : ExamMessages.EXAM_FETCHED);
        log.info(String.format("getAllActiveExams() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
