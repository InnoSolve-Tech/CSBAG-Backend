package com.cosek.edms.conflict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConflictRepository extends JpaRepository<ConflictOfInterest, Long> {
}
