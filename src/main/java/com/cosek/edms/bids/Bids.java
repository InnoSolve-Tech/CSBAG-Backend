package com.cosek.edms.bids;

import com.cosek.edms.Supplier.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")  // Explicitly specify table name
public class Bids {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String currency;
        private String no;
        private String description;
        private int quantity;
        private int frequency;
        private String status;
        private String service;

        @OneToMany(mappedBy = "bid", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Supplier> suppliers;
}
