package com.cosek.edms.ProcurementStatusHistory;


import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.ProcurementStatusHistory.Models.ProcurementStatusHistoryRequest;

import com.cosek.edms.procurement.Procurement;
import com.cosek.edms.procurement.ProcurementRepository;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcurementStatusHistoryService {

    private final ProcurementStatusHistoryRepository procurementStatusHistoryRepository;
    private final ProcurementRepository procurementRepository;
    private final UserRepository userRepository;

    public List<ProcurementStatusHistory> findHistoryByProcurementId(Long procurementId) {
        return procurementStatusHistoryRepository.findByProcurementId(procurementId);
    }

    public ProcurementStatusHistory createHistoryEntry(ProcurementStatusHistoryRequest request) {
        Procurement procurement = null;
        try {
            procurement = procurementRepository.findById(request.getProcurementId())
                    .orElseThrow(() -> new NotFoundException("Procurement not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        User responsibleUser = null;
        try {
            responsibleUser = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        ProcurementStatusHistory historyEntry = ProcurementStatusHistory.builder()
                .procurement(procurement)
                .responsibleUser(responsibleUser)
                .status(request.getStatus())
                .reason(request.getReason())
                .updatedAt(LocalDateTime.now())
                .build();

        return procurementStatusHistoryRepository.save(historyEntry);
    }

    public ProcurementStatusHistory findHistoryById(Long historyId) {
        try {
            return procurementStatusHistoryRepository.findById(historyId)
                    .orElseThrow(() -> new NotFoundException("Procurement status history entry not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
