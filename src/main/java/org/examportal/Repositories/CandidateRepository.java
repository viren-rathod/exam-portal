package org.examportal.Repositories;

import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.Exam;
import org.examportal.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByUser(User user);

    Optional<Candidate> findByUserAndExam(User user, Exam exam);

    Long countByExamId(Long examId);

    List<Candidate> findByExamId(Long examId);
}
