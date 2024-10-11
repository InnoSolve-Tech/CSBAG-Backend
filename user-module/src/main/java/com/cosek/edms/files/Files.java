package com.cosek.edms.files;


import com.cosek.edms.casestudy.CaseStudy;
import com.cosek.edms.folders.Folders;
import com.cosek.edms.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String PIDInfant;
    private String PIDMother;
    private int boxNumber;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User responsibleUser;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folders folder;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "case_study_id", referencedColumnName = "id")
    private CaseStudy caseStudy;

}
