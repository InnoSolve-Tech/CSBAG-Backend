package com.cosek.edms.ProcurementStatusHistory;

import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.ProcurementStatusHistory.Models.ProcurementStatusHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/procurement-status-history")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProcurementStatusHistoryController {

    private final ProcurementStatusHistoryService procurementStatusHistoryService;

    @GetMapping("/procurement/{procurementId}")
    public ResponseEntity<List<ProcurementStatusHistory>> getHistoryByProcurementId(@PathVariable Long procurementId) {
        List<ProcurementStatusHistory> history = procurementStatusHistoryService.findHistoryByProcurementId(procurementId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProcurementStatusHistory> createHistoryEntry(@RequestBody ProcurementStatusHistoryRequest request) {
        ProcurementStatusHistory historyEntry = procurementStatusHistoryService.createHistoryEntry(request);
        return new ResponseEntity<>(historyEntry, HttpStatus.CREATED);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<ProcurementStatusHistory> getHistoryById(@PathVariable Long historyId) {
        ProcurementStatusHistory historyEntry = procurementStatusHistoryService.findHistoryById(historyId);
        return new ResponseEntity<>(historyEntry, HttpStatus.OK);
    }
}

