package com.cosek.edms.casestudypermissions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "case_study_permissions") // This is your permissions table
public class CaseStudyPermissions {
    private boolean userSpecific; // New field to indicate if the permission is user-specific


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name", nullable = false)
    private String permissionName; // Represents the permission name

    private boolean checked; // Indicates if the permission is granted

}
