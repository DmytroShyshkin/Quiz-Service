package com.dmytro.quiz_service.application.usercase.anki;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.NextAnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NextAnkiInteractor implements NextAnkiCard {

    private final AnkiCardPort ankiCardPort;

    @SuppressWarnings("null")
    @Override
    public Optional<AnkiCard> nextAnkiCard(String userEmail) {
        List<AnkiCard> dueCards = new ArrayList<>(
            ankiCardPort.findDueCards(userEmail, LocalDateTime.now())
        );
        Collections.shuffle(dueCards);

        return dueCards.stream()
            .min(Comparator.comparing(AnkiCard::getNextReviewAt));
    }
}
