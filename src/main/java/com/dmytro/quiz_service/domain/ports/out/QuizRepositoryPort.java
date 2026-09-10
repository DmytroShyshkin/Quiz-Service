package com.dmytro.quiz_service.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dmytro.quiz_service.domain.model.QuizSession;

public interface QuizRepositoryPort {
    QuizSession save(QuizSession session);
    Optional<QuizSession> findSessionById(UUID sessionId);
    void deleteById(UUID sessionId);
    void deleteAllByUserId(UUID userId);
    List<UUID> findDistinctUserIds();
}
