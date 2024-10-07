package com.cosek.edms.departments.DepartmentModule;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DepartmentModule {
    private Long id;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
