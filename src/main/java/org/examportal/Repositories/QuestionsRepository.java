package org.examportal.Repositories;

import org.examportal.Models.Category;
import org.examportal.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    Set<Questions> findByCategory(Category category);
}
