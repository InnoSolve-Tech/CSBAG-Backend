package com.cosek.edms.casestudypermissions;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseStudyPermissionsRepository extends JpaRepository<CaseStudyPermissions, Long> {
    // Custom query methods can be added here if needed
}

