package com.cosek.edms.casestudy.Modals;

import lombok.Data;

@Data
public class AssignUserRequest {
    private Long caseStudyId;
    private Long userId;
}
