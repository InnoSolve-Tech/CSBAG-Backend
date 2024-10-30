package com.cosek.edms.memo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "memos")
 class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
