package com.cosek.edms.procurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcurementService {

    @Autowired
    private ProcurementRepository procurementRepository;


    public Procurement createProcurement(Procurement procurement) {
        return procurementRepository.save(procurement);
    }

    public List<Procurement> getAllProcurements() {
        return procurementRepository.findAll();
    }

    public Optional<Procurement> getProcurementById(Long id) {
        return procurementRepository.findById(id);
    }

    public Procurement updateProcurement(Long id, Procurement procurementDetails) {
        return procurementRepository.findById(id)
                .map(procurement -> {
                    procurement.setServiceName(procurementDetails.getServiceName());
                    procurement.setDescription(procurementDetails.getDescription());
                    procurement.setDepartment(procurementDetails.getDepartment());
                    procurement.setServiceUsage(procurementDetails.getServiceUsage());
                    procurement.setDonor(procurementDetails.getDonor());
                    procurement.setBudgetLine(procurementDetails.getBudgetLine());
                    procurement.setSize(procurementDetails.getSize());
                    procurement.setQuantity(procurementDetails.getQuantity());
                    procurement.setAmount(procurementDetails.getAmount());
                    procurement.setDateNeeded(procurementDetails.getDateNeeded());
                    return procurementRepository.save(procurement);
                }).orElseThrow(() -> new RuntimeException("Procurement not found"));
    }

    public void deleteProcurement(Long id) {
        procurementRepository.deleteById(id);
    }
}
