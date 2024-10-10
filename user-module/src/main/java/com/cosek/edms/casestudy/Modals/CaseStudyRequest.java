package com.cosek.edms.casestudy.Modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseStudyRequest {
    private String name;
    private String description;
}
