package com.cosek.edms.files;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FilesController {

    private final FilesService filesService;

    @PostMapping("/add")
    public ResponseEntity<Files> addFile(@RequestBody Files file) {
        Files createdFile = filesService.addFile(file);
        return ResponseEntity.ok(createdFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Files> getFileById(@PathVariable Long id) {
        Optional<Files> file = filesService.getFileById(id);
        return file.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("all")
    public ResponseEntity<List<Files>> getAllFiles() {
        List<Files> files = filesService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Files> updateFile(@PathVariable Long id, @RequestBody Files updatedFile) {
        Files file = filesService.updateFile(id, updatedFile);
        return ResponseEntity.ok(file);
    }

    @PutMapping("/update-multiple")
    public ResponseEntity<List<Files>> updateMultipleFiles(@RequestBody List<Files> files) {
        List<Files> updatedFiles = filesService.updateMultipleFiles(files);
        return ResponseEntity.ok(updatedFiles);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        filesService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-multiple")
    public ResponseEntity<Void> deleteMultipleFiles(@RequestBody List<Long> ids) {
        filesService.deleteMultipleFiles(ids);
        return ResponseEntity.noContent().build();
    }
}
