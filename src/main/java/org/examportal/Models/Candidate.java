package org.examportal.Models;

import jakarta.persistence.*;
import lombok.*;
import org.examportal.Constants.ExamStatus;
import org.examportal.Models.Exam.Exam;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "exam_id"})
})
@ToString
public class Candidate extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    private String enrollmentNumber;

    private String contactNumber;

    private Long sscPercentage;

    private Long hscPercentage;

    private Long cgpa;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ATTENDED','ATTENDING','NOT_ATTENDED')")
    private ExamStatus candidateStatus;

    private LocalDateTime examStartTime;

    private LocalDateTime examEndTime;

    private Integer score;

    private Integer totalQuestions;

}
