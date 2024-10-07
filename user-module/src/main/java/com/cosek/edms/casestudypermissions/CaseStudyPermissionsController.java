package com.cosek.edms.casestudypermissions;

import com.cosek.edms.casestudypermissions.CaseStudyPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/case-study-permissions")
@CrossOrigin(origins = "*")

public class CaseStudyPermissionsController {

    @Autowired
    private CaseStudyPermissionsService caseStudyPermissionsService;

    // Get all permissions
    @GetMapping("/")
    public List<CaseStudyPermissions> getAllPermissions() {
        return caseStudyPermissionsService.getAllPermissions();
    }

    // Get a permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<CaseStudyPermissions> getPermissionById(@PathVariable Long id) {
        Optional<CaseStudyPermissions> permission = caseStudyPermissionsService.getPermissionById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new permission
    @PostMapping
    public CaseStudyPermissions createPermission(@RequestBody CaseStudyPermissions permission) {
        return caseStudyPermissionsService.savePermission(permission);
    }

    // Update an existing permission
    @PutMapping("/{id}")
    public ResponseEntity<CaseStudyPermissions> updatePermission(@PathVariable Long id, @RequestBody CaseStudyPermissions updatedPermission) {
        try {
            CaseStudyPermissions permission = caseStudyPermissionsService.updatePermission(id, updatedPermission);
            return ResponseEntity.ok(permission);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a permission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        caseStudyPermissionsService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}

