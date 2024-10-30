package com.cosek.edms.memo.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoRequest {
    private Long id;

    private String activityName;
    private String preparedBy;
    private String activityPurpose;
    private String activitySummary;
    private int quantity;
    private int duration;
    private double activityCost;
    private LocalDate date;
}
