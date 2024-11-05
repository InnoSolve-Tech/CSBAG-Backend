package com.cosek.edms.bids;

import com.cosek.edms.BidsStatusHistory.BidsStatusHistory;
import com.cosek.edms.BidsStatusHistory.BidsStatusHistoryRepository;
import com.cosek.edms.ProcurementStatusHistory.ProcurementStatusHistory;
import com.cosek.edms.exception.ResourceNotFoundException;
import com.cosek.edms.procurement.Procurement;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bids")
public class BidsController {

    @Autowired
    private BidsService bidsService;

    @Autowired
    private BidsRepository bidsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidsStatusHistoryRepository bidsStatusHistoryRepository;



    @PostMapping("create")
    public ResponseEntity<Bids> createBid(@RequestBody Bids bids) {
        Bids createdBid = bidsService.createBid(bids);
        return ResponseEntity.ok(createdBid);
    }

    @GetMapping("all")
    public List<Bids> getAllBids() {
        return BidsService.getAllBids();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bids> getBidsById(@PathVariable Long id) {
        return bidsService.getBidsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Bids> updateBids(@PathVariable Long id, @RequestBody Bids bidDetails) {
        Bids updatedBids = bidsService.updateBid(id, bidDetails);
        return ResponseEntity.ok(updatedBids);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBids(@PathVariable Long id) {
        bidsService.deleteBids(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Bids bids = bidsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        // Extract fields from updates
        String newStatus = (String) updates.get("status");
        String rejectReason = (String) updates.get("reject");
        Long userId = Long.parseLong(updates.get("userId").toString());

        // Fetch user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update current status and responsible user for the Procurement
        bids.setStatus(newStatus);
        bids.setId(id);  // if `responsible_user` is stored as String
        bidsRepository.save(bids);

        // Save history entry
        BidsStatusHistory statusHistory = BidsStatusHistory.builder()
                .bids(bids)
                .responsibleUser(user)
                .status(newStatus)
                .reason(rejectReason)
                .updatedAt(LocalDateTime.now())
                .build();

        bidsStatusHistoryRepository.save(statusHistory);
        return ResponseEntity.ok("Bid's status updated successfully");

    }

}
