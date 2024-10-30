package com.cosek.edms.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @PostMapping("create")
    public ResponseEntity<Procurement> createProcurement(@RequestBody Procurement procurement) {
        Procurement createdProcurement = procurementService.createProcurement(procurement);
        return ResponseEntity.ok(createdProcurement);
    }

    @GetMapping("all")
    public List<Procurement> getAllProcurements() {
        return procurementService.getAllProcurements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Procurement> getProcurementById(@PathVariable Long id) {
        return procurementService.getProcurementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Procurement> updateProcurement(@PathVariable Long id, @RequestBody Procurement procurementDetails) {
        Procurement updatedProcurement = procurementService.updateProcurement(id, procurementDetails);
        return ResponseEntity.ok(updatedProcurement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProcurement(@PathVariable Long id) {
        procurementService.deleteProcurement(id);
        return ResponseEntity.noContent().build();
    }
}
