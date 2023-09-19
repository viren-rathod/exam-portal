package org.examportal.Repositories;

import org.examportal.Models.Exam.Exam.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
