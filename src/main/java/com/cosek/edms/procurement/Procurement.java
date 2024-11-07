package com.cosek.edms.procurement;

import com.cosek.edms.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private String serviceName;
    private String description;
    private String department;
    private String serviceUsage;
    private String donor;
    private String budgetLine;
    private String size;
    private Integer quantity;
    private Double amount;
    private LocalDate dateNeeded;
    private String status;
    private String reject;
}
