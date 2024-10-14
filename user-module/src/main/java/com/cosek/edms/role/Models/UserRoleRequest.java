package com.cosek.edms.role.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequest {
    private Long userId;
    private String userType;
}

