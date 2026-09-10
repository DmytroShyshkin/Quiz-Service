package com.dmytro.quiz_service.infrastructure.kafka.consumer.userSnapshot;

import java.time.Instant;
import java.util.List;

public record UserEmailsSnapshotEvent(
    List<String> emails
    , Instant generatedAt
) {
}
