package com.cosek.edms.ProcurementStatusHistory;

import com.cosek.edms.procurement.Procurement;
import com.cosek.edms.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProcurementStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "procurement_id", nullable = false)
    private Procurement procurement;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User responsibleUser;

    private String status;
    private String reason;
    private LocalDateTime updatedAt;
}
