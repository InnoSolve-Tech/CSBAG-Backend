package com.cosek.edms.casestudy;

import com.cosek.edms.casestudy.Modals.AssignUserRequest;
import com.cosek.edms.casestudy.Modals.CaseStudyRequest;
import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.role.Role;
import com.cosek.edms.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/case-studies")
@CrossOrigin(origins = "*")
public class CaseStudyController {

    @Autowired
    private CaseStudyService caseStudyService;

    @Autowired
    private RoleService roleService;

    // Get all case studies
    @GetMapping("/all")
    public List<CaseStudy> getAllCaseStudies() {
        return caseStudyService.getAllCaseStudies();
    }

    // Get a case study by ID
    @GetMapping("/{id}")
    public ResponseEntity<CaseStudy> getCaseStudyById(@PathVariable Long id) {
        Optional<CaseStudy> caseStudy = caseStudyService.getCaseStudyById(id);
        return caseStudy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing case study
    @PutMapping("/update/{id}")
    public ResponseEntity<CaseStudy> updateCaseStudy(@PathVariable Long id, @RequestBody CaseStudy updatedCaseStudy) {
        try {
            CaseStudy caseStudy = caseStudyService.updateCaseStudy(id, updatedCaseStudy);
            return ResponseEntity.ok(caseStudy);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a case study
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCaseStudy(@PathVariable Long id) {
        caseStudyService.deleteCaseStudy(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-cases")
    public ResponseEntity<?> createCaseStudy(@RequestBody CaseStudyRequest request) {
        try {
            // Create the case study without any role assignment
            CaseStudy newCaseStudy = caseStudyService.createCaseStudy(request, null, null);

            return ResponseEntity.status(HttpStatus.CREATED).body(newCaseStudy);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create CaseStudy: " + e.getMessage());
        }
    }

    @PostMapping("/assign-user")
    public ResponseEntity<?> assignUserToCaseStudy(@RequestBody AssignUserRequest request) {
        try {
            CaseStudy updatedCaseStudy = caseStudyService.assignUserToCaseStudy(request.getCaseStudyId(), request.getUserId());
            return ResponseEntity.ok(updatedCaseStudy);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign user: " + e.getMessage());
        }
    }

}
