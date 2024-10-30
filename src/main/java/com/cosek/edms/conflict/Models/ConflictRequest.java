package com.cosek.edms.conflict.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConflictRequest {
    private String name;
    private String department;       // Department or other details
    private String position;
    private String natureOfInterest;
    private String status;            // Optional field, can be set to "Pending"
    private LocalDate created_at;     // Date of creation or report
}
