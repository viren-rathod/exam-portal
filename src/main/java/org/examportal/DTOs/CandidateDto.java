package org.examportal.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Candidate Dto Model")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {

    private String username;
    private Long examId;
    private String email;
    private Long collegeId;
    private String enrollmentNumber;
    private String contactNumber;
    private Long sscPercentage;
    private Long hscPercentage;
    private Long cgpa;
}
