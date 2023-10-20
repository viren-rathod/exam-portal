package org.examportal.Services;

import org.examportal.DTOs.CandidateDto;
import org.examportal.Models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CandidateService {
    Candidate create(CandidateDto candidateDto, String user);

    Set<CandidateDto> findAll();
    Page<CandidateDto> findPaginated(Pageable pageable);
}
