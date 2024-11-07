package com.cosek.edms.ProcurementStatusHistory.Models;

import lombok.Data;

@Data
public class ProcurementStatusHistoryRequest {
    private Long procurementId;
    private Long userId;
    private String status;
    private String reason;
}
