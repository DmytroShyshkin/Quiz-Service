package com.dmytro.quiz_service.adapters.out.persistence.anki;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.infrastructure.persistence.anki.AnkiCardDocument;
import com.dmytro.quiz_service.infrastructure.persistence.anki.JpaAnkiCardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnkiCardRepositoryAdapter implements AnkiCardPort {

    private final JpaAnkiCardRepository repository;
    private final AnkiCardMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public AnkiCard save(AnkiCard card) {
        return mapper.toDomain(
                repository.save(
                                mapper.toDocument(card)
                        )
        );
    }

    @Override
    public Optional<AnkiCard> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<AnkiCard> findByWordIdAndUserEmail(UUID wordId, String userEmail) {
        return repository.findByWordIdAndUserEmail(wordId, userEmail)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<AnkiCard> deleteByWordIdAndUserEmail(UUID wordId, String userEmail) {
        return repository.deleteByWordIdAndUserEmail(wordId, userEmail)
                .map(mapper::toDomain);
    }

    @Override
    public List<AnkiCard> deleteAllByUserEmail(String userEmail) {
        return repository.deleteAllByUserEmail(userEmail)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AnkiCard> findDueCards(String userEmail, LocalDateTime before) {
        return repository
                .findByUserEmailAndNextReviewAtBefore(userEmail, before)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<String> findDistinctUserEmails() {
        return mongoTemplate.findDistinct(new Query(), "userEmail", AnkiCardDocument.class, String.class);
    }
}
