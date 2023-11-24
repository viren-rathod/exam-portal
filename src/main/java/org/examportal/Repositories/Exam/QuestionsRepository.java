package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Options;
import org.examportal.Models.Exam.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    Set<Questions> findByCategory(Category category);

    @Query("SELECT q FROM Questions q WHERE " +
            "(:searchData IS NULL OR " +
            "LOWER(q.title) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(CAST(q.category.title AS string)) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(q.description) LIKE LOWER(CONCAT('%', :searchData, '%')))")
    Page<Questions> findAllWithFilters(@Param("searchData") String searchData, Pageable pageable);

    @Query("SELECT q.answer FROM Questions q WHERE q.id = :questionId")
    Options findAnswerById(Long questionId);
}
