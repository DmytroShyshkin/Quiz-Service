package com.dmytro.quiz_service.adapters.out.persistence.quiz;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.infrastructure.persistence.quiz.JpaQuizSessionRepository;
import com.dmytro.quiz_service.infrastructure.persistence.quiz.QuizSessionDocument;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizSessionRepositoryAdapter implements QuizRepositoryPort {
    private final JpaQuizSessionRepository repository;
    private final QuizSessionMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public QuizSession save(QuizSession session) {
        return mapper.toDomain(
                repository.save(
                        mapper.toDocument(session)
                )
        );
    }

    @Override
    public Optional<QuizSession> findSessionById(UUID sessionId) {
        return repository.findById(sessionId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID sessionId) {
        repository.deleteById(sessionId);
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    public List<UUID> findDistinctUserIds() {
        return mongoTemplate.findDistinct(new Query(), "userId", QuizSessionDocument.class, UUID.class);
    }
}
