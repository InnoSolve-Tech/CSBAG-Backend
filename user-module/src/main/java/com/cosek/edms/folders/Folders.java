package com.cosek.edms.folders;

import com.cosek.edms.files.Files;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Folders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String folderName;
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<Files> files;
}
