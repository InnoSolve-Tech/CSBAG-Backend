package com.cosek.edms.Supplier;

import com.cosek.edms.bids.Bids;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double unitPrice;
    private double totalCost;

    @JsonProperty("isRecommended")
    private boolean isRecommended;

    private String justification;

    @ManyToOne
    @JoinColumn(name = "bid_id")
    @JsonIgnore
    private Bids bid;
}