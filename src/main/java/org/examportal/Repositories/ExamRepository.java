package org.examportal.Repositories;

import org.examportal.Models.Exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Long> {
}
