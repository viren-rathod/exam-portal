package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Category;
import org.examportal.Models.Exam.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    Set<Questions> findByCategory(Category category);
}
