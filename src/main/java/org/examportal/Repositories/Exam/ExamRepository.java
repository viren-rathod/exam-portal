package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT e FROM Exam e WHERE " +
            "(:searchData IS NULL OR " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(CAST(e.examTime AS string)) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(e.maxMarks) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(e.totalQuestions) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(e.examCode) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(CAST(e.status AS string)) LIKE :searchData)")
    Page<Exam> findAllWithFilters(@Param("searchData") String searchData, Pageable pageable);
}
