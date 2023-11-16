package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE " +
            "(:searchData IS NULL OR " +
            "LOWER(c.title) LIKE LOWER(CONCAT('%', :searchData, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :searchData, '%')))")
    Page<Category> findAllWithFilters(@Param("searchData") String searchData, Pageable pageable);
}
