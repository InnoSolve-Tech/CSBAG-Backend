package com.cosek.edms.casestudy;

import com.cosek.edms.user.User;
import com.cosek.edms.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "case_study")
public class CaseStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Many-to-many relationship with users
    @ManyToMany
    @JoinTable(
            name = "case_study_user",
            joinColumns = @JoinColumn(name = "case_study_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    // Many-to-many relationship with roles
    @ManyToMany
    @JoinTable(
            name = "case_study_role",
            joinColumns = @JoinColumn(name = "case_study_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
