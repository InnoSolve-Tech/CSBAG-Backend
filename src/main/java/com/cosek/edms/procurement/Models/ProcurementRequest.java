package com.cosek.edms.procurement.Models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProcurementRequest {
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
