package com.cosek.edms.procurement;

import com.cosek.edms.ProcurementStatusHistory.ProcurementStatusHistory;
import com.cosek.edms.ProcurementStatusHistory.ProcurementStatusHistoryRepository;
import com.cosek.edms.exception.ResourceNotFoundException;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private ProcurementRepository procurementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcurementStatusHistoryRepository procurementStatusHistoryRepository;  // Correct this line


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

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procurement not found"));

        // Extract fields from updates
        String newStatus = (String) updates.get("status");
        String rejectReason = (String) updates.get("reject");
        Long userId = Long.parseLong(updates.get("userId").toString());

        // Fetch user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update current status and responsible user for the Procurement
        procurement.setStatus(newStatus);
        procurement.setUserId(userId);  // if `responsible_user` is stored as String
        procurementRepository.save(procurement);

        // Save history entry
        ProcurementStatusHistory statusHistory = ProcurementStatusHistory.builder()
                .procurement(procurement)
                .responsibleUser(user)
                .status(newStatus)
                .reason(rejectReason)
                .updatedAt(LocalDateTime.now())
                .build();

        procurementStatusHistoryRepository.save(statusHistory);
        return ResponseEntity.ok("Procurement status updated successfully");

    }

}

