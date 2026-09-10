package com.dmytro.quiz_service.domain.ports.in;

import java.util.Set;

public interface ReconcileOrphanedDataUseCase {
    void reconcile(Set<String> liveEmails);
}
