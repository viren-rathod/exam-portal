package org.examportal.Services;

import org.examportal.DTOs.CandidateDto;
import org.examportal.Models.Candidate;
import org.springframework.stereotype.Service;

@Service
public interface CandidateService {
    Candidate create(CandidateDto candidateDto, String user);
}
