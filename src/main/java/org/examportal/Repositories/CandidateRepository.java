package org.examportal.Repositories;

import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.Exam;
import org.examportal.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByUser(User user);

    Optional<Candidate> findByUserAndExam(User user, Exam exam);

    Long countByExamId(Long examId);

    List<Candidate> findByExamId(Long examId);

    // Bulk lookup: find all candidates for a user across multiple exams
    @Query("SELECT c FROM Candidate c WHERE c.user = :user AND c.exam.id IN :examIds")
    List<Candidate> findByUserAndExamIds(@Param("user") User user,
                                         @Param("examIds") List<Long> examIds);
}
