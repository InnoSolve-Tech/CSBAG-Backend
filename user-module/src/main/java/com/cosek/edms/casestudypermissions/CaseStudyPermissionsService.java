package com.cosek.edms.casestudypermissions;

import com.cosek.edms.casestudypermissions.CaseStudyPermissionsRepository;
import com.cosek.edms.casestudypermissions.CaseStudyPermissions;
import com.cosek.edms.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CaseStudyPermissionsService {

    @Autowired
    private CaseStudyPermissionsRepository caseStudyPermissionsRepository;

    // Fetch permissions by a set of IDs
    public Set<CaseStudyPermissions> getPermissionsByIds(Set<Long> ids) {
        return caseStudyPermissionsRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    // Fetch all permissions
    public List<CaseStudyPermissions> getAllPermissions() {
        return caseStudyPermissionsRepository.findAll();
    }

    // Fetch a single permission by ID
    public Optional<CaseStudyPermissions> getPermissionById(Long id) {
        return caseStudyPermissionsRepository.findById(id);
    }

    // Save a new permission
    public CaseStudyPermissions savePermission(CaseStudyPermissions permission) {
        return caseStudyPermissionsRepository.save(permission);
    }

    // Update an existing permission
    public CaseStudyPermissions updatePermission(Long id, CaseStudyPermissions updatedPermission) {
        Optional<CaseStudyPermissions> optionalPermission = caseStudyPermissionsRepository.findById(id);

        if (optionalPermission.isPresent()) {
            CaseStudyPermissions existingPermission = optionalPermission.get();
            existingPermission.setPermissionName(updatedPermission.getPermissionName());
            existingPermission.setChecked(updatedPermission.isChecked());
            return caseStudyPermissionsRepository.save(existingPermission);
        } else {
            throw new RuntimeException("Permission not found");
        }
    }

    // Delete a permission by ID
    public void deletePermission(Long id) {
        caseStudyPermissionsRepository.deleteById(id);
    }

    public Set<CaseStudyPermissions> getCaseStudyPermissionsByRole(Role role) {
        // Define the names of permissions that you consider for case studies
        List<String> caseStudyPermissionNames = List.of("READ_CASE_STUDY", "WRITE_CASE_STUDY", "DELETE_CASE_STUDY");

        // Filter the role's permissions and create CaseStudyPermissions
        return role.getPermissions().stream()
                .filter(permission -> caseStudyPermissionNames.contains(permission.getName()))
                .map(permission -> CaseStudyPermissions.builder()
                        .permissionName(permission.getName())
                        .checked(true) // or false based on your logic
                        .userSpecific(false) // Adjust as needed
                        .build())
                .collect(Collectors.toSet());
    }


}
