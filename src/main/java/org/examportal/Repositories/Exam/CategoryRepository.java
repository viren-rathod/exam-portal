package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
