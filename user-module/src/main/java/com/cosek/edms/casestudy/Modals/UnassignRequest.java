package com.cosek.edms.casestudy.Modals;

import lombok.Data;

import java.util.List;

@Data
public class UnassignRequest {
    private Long caseStudyId;
    private Long userId;
}
