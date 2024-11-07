package com.cosek.edms.bids.Models;

import lombok.Data;

@Data
public class bidRequest {
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

}
