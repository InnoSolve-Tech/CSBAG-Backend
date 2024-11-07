package com.cosek.edms.bids;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidsRepository extends JpaRepository<Bids, Long> {

}
