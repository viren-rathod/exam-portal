package org.examportal.Repositories.Exam;

import org.examportal.Models.Exam.Options;
import org.examportal.Models.Exam.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OptionRepository extends JpaRepository<Options, Long> {
    Set<Options> findByQuestion(Questions questions);
}
