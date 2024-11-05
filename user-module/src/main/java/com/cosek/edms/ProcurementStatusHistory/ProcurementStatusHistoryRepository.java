package com.cosek.edms.ProcurementStatusHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcurementStatusHistoryRepository extends JpaRepository<ProcurementStatusHistory, Long> {

    List<ProcurementStatusHistory> findByProcurementId(Long procurementId);
}

