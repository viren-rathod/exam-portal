package org.examportal.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.examportal.Constants.Status;
import org.examportal.Models.Exam.Exam;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private String email;

    private Long collegeId;

    private String enrollment_number;

    private String contact_number;

    private Long ssc_percentage;

    private Long hsc_percentage;

    private Long cgpa;

    @Enumerated(EnumType.STRING)
    private Status candidate_status;

    private LocalDateTime exam_start_time;

    private LocalDateTime exam_end_time;

}
