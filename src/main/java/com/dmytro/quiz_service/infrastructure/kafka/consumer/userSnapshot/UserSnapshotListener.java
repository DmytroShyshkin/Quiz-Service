package com.dmytro.quiz_service.infrastructure.kafka.consumer.userSnapshot;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.ports.in.ReconcileOrphanedDataUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSnapshotListener {
    private final ReconcileOrphanedDataUseCase reconcileUseCase;
    private final AtomicReference<Set<String>> liveEmails = new AtomicReference<>(null);

    @KafkaListener(
            topics = "recall.user.snapshot",
            groupId = "user-snapshot-consumer",
            containerFactory = "userSnapshotContainerFactory"
    )
    public void onSnapshot(UserEmailsSnapshotEvent event) {
        liveEmails.set(new HashSet<>(event.emails()));
        reconcileUseCase.reconcile(liveEmails.get());
    }
}
