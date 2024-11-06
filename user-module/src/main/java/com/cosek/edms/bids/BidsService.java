package com.cosek.edms.bids;

import com.cosek.edms.Supplier.Supplier;
import com.cosek.edms.procurement.Procurement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidsService {
    private static  BidsRepository bidsRepository;

    @Autowired
    public BidsService(BidsRepository bidsRepository) {
        this.bidsRepository = bidsRepository;
    }


    @Transactional
    public Bids saveBid(Bids bidRequest) {
        for (Supplier supplier : bidRequest.getSuppliers()) {
            supplier.setBid(bidRequest);  // Set bid reference in each supplier
        }
        return bidsRepository.save(bidRequest);
    }

    public static List<Bids> getAllBids() {

        return bidsRepository.findAll();
    }

    public Optional<Bids> getBidsById(Long id) {
        return bidsRepository.findById(id);
    }

//    public Bids updateBid(Long id, Bids bidDetails, Supplier supplier) {
//        return bidsRepository.findById(id)
//                .map(bids -> {
//                    bids.setDescription(bidDetails.getDescription());
//                    bids.setQuantity(bidDetails.getQuantity());
//                    bids.setFrequency(bidDetails.getFrequency());
//                    bids.setUnitPrice(supplier.getUnitPrice());
//                    bids.setTotalCost(supplier.getTotalCost());
//                    bids.setIsRecommended(supplier.getIsRecommended());
//                    bids.setJustification(supplier.getJustification());
//                    return bidsRepository.save(bids);
//                }).orElseThrow(() -> new RuntimeException("Bid not found"));
//    }

    public void deleteBids(Long id) {
        bidsRepository.deleteById(id);
    }
}

