package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.Resume;
import ru.otus.spring.petrova.repository.ResumeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {

  private final ResumeRepository resumeRepository;

  public Resume save(Resume resume) {
    return resumeRepository.save(resume);
  }

  public Optional<Resume> getById(Long id) {
    return resumeRepository.findById(id);
  }
}
