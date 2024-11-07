package com.cosek.edms.BidsStatusHistory;

import com.cosek.edms.bids.Bids;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.cosek.edms.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidsStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bids bids;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User responsibleUser;

    @Column(nullable = false)
    private String status;

    private String reason;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
