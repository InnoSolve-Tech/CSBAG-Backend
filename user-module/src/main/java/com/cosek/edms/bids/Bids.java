package com.cosek.edms.bids;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bids {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String supplier;
        private String currency;
        private String no;
        private String description;
        private int quantity;
        private int frequency;
        private int unitPrice;
        private int totalCost;
        private String recommendedSupplier;
        private String justification;
        private String status;




}
