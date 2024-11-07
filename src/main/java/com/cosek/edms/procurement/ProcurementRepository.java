package com.cosek.edms.procurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Long> {
}
