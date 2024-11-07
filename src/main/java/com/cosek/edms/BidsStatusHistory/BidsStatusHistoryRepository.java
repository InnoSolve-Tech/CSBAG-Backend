package com.cosek.edms.BidsStatusHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidsStatusHistoryRepository extends JpaRepository<BidsStatusHistory, Long> {
}
