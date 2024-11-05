package com.cosek.edms.bids;

import com.cosek.edms.procurement.Procurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidsService {
    @Autowired
    private static BidsRepository bidsRepository;



    public Bids createBid(Bids bids) {
        return bidsRepository.save(bids);
    }

    public static List<Bids> getAllBids() {

        return bidsRepository.findAll();
    }

    public Optional<Bids> getBidsById(Long id) {
        return bidsRepository.findById(id);
    }

    public Bids updateBid(Long id, Bids bidDetails) {
        return bidsRepository.findById(id)
                .map(bids -> {
                    bids.setSupplier(bidDetails.getSupplier());
                    bids.setDescription(bidDetails.getDescription());
                    bids.setQuantity(bidDetails.getQuantity());
                    bids.setFrequency(bidDetails.getFrequency());
                    bids.setUnitPrice(bidDetails.getUnitPrice());
                    bids.setTotalCost(bidDetails.getTotalCost());
                    bids.setRecommendedSupplier(bidDetails.getRecommendedSupplier());
                    bids.setJustification(bidDetails.getJustification());
                    return bidsRepository.save(bids);
                }).orElseThrow(() -> new RuntimeException("Bid not found"));
    }

    public void deleteBids(Long id) {
        bidsRepository.deleteById(id);
    }
}

