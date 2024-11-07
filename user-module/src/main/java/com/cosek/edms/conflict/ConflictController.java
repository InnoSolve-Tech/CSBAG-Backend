package com.cosek.edms.conflict;

import com.cosek.edms.conflict.Models.ConflictRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conflicts")
public class ConflictController {

    @Autowired
    private ConflictService service;

    @GetMapping("/all")
    public List<ConflictOfInterest> getAllConflicts() {
        return service.getAllConflicts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConflictOfInterest> getConflictById(@PathVariable Long id) {
        return service.getConflictById(id)
                .map(conflict -> ResponseEntity.ok().body(conflict))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ConflictOfInterest> createConflict(@RequestBody ConflictRequest conflictRequest) {
        ConflictOfInterest conflict = ConflictOfInterest.builder()
                .name(conflictRequest.getName())
                .department(conflictRequest.getDepartment())
                .position(conflictRequest.getPosition())
                .natureOfInterest(conflictRequest.getNatureOfInterest())
                .date(conflictRequest.getCreated_at())
                .build();

        ConflictOfInterest createdConflict = service.createConflict(conflict);
        return new ResponseEntity<>(createdConflict, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ConflictOfInterest> updateConflict(
            @PathVariable Long id,
            @RequestBody ConflictOfInterest conflictDetails
    ) {
        try {
            ConflictOfInterest updatedConflict = service.updateConflict(id, conflictDetails);
            return ResponseEntity.ok(updatedConflict);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConflict(@PathVariable Long id) {
        service.deleteConflict(id);
        return ResponseEntity.noContent().build();
    }
}
