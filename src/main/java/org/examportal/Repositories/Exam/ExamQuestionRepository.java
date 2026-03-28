package org.examportal.Repositories.Exam;

import org.examportal.Models.Candidate;
import org.examportal.Models.Exam.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

    List<ExamQuestion> findByCandidate(Candidate candidate);

    boolean existsByCandidate(Candidate candidate);

    long countByCandidateAndIsCorrectTrue(Candidate candidate);

    // JOIN FETCH to load questions + options in one query
    @Query("SELECT DISTINCT eq FROM ExamQuestion eq " +
            "JOIN FETCH eq.question q " +
            "LEFT JOIN FETCH q.options " +
            "WHERE eq.candidate = :candidate")
    List<ExamQuestion> findByCandidateWithQuestionsAndOptions(
            @Param("candidate") Candidate candidate);

    // JOIN FETCH to load questions + options + answers for grading
    @Query("SELECT DISTINCT eq FROM ExamQuestion eq " +
            "JOIN FETCH eq.question q " +
            "LEFT JOIN FETCH q.options " +
            "LEFT JOIN FETCH q.answer " +
            "LEFT JOIN FETCH eq.selectedOption " +
            "WHERE eq.candidate = :candidate")
    List<ExamQuestion> findByCandidateWithFullDetails(
            @Param("candidate") Candidate candidate);
}
