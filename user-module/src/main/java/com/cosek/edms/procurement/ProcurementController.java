package com.cosek.edms.procurement;

import com.cosek.edms.ProcurementStatusHistory.ProcurementStatusHistory;
import com.cosek.edms.ProcurementStatusHistory.ProcurementStatusHistoryRepository;
import com.cosek.edms.exception.ResourceNotFoundException;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserRepository;
import com.cosek.edms.util.PdfGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @Autowired
    private PdfGenerator pdfGenerator;

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

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadProcurementPdf(@PathVariable Long id) throws DocumentException, IOException {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procurement not found"));

        List<ProcurementStatusHistory> statusHistoryList = procurementStatusHistoryRepository.findByProcurementId(id);

        // Create a data map for procurement details
        Map<String, Object> procurementData = new HashMap<>();
        procurementData.put("Service Name", procurement.getServiceName());
        procurementData.put("Description", procurement.getDescription());
        procurementData.put("Department", procurement.getDepartment());
        procurementData.put("Quantity", procurement.getQuantity());
        procurementData.put("Amount", procurement.getAmount());
        procurementData.put("Date Needed", procurement.getDateNeeded());
        procurementData.put("Status", procurement.getStatus());

        // Generate the PDF using the generic PDF generator
        ByteArrayOutputStream pdfStream = pdfGenerator.generateGenericPdf("Procurement Details", procurementData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=procurement_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.toByteArray());
    }
}

