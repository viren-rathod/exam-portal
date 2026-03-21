package org.examportal.Repositories.Exam;

import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

    List<ExamQuestion> findByCandidate(Candidate candidate);

    boolean existsByCandidate(Candidate candidate);

    long countByCandidateAndIsCorrectTrue(Candidate candidate);
}
