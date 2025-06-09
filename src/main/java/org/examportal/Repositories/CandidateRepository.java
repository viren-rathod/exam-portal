package org.examportal.Repositories;

import org.examportal.Models.Candidate;
import org.examportal.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUser(User user);

    Long countByExamId(Long examId);
}
