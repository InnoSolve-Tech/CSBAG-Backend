package com.cosek.edms.files;

import com.cosek.edms.exception.ResourceNotFoundException;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username).orElseThrow(() ->
                new IllegalArgumentException("User not found")
        );
    }

    public Files addFile(Files file) {
        User loggedInUser = getLoggedInUser();
        file.setResponsibleUser(loggedInUser);
        return filesRepository.save(file);
    }

    public Optional<Files> getFileById(Long id) {
        return filesRepository.findById(id);
    }

    public List<Files> getAllFiles() {
        return filesRepository.findAll();
    }

    public Files updateFile(Long id, Files updatedFile) {
        User loggedInUser = getLoggedInUser();
        return filesRepository.findById(id)
                .map(file -> {
                    file.setFileName(updatedFile.getFileName());
                    file.setFolder(updatedFile.getFolder());
                    file.setResponsibleUser(loggedInUser);
                    return filesRepository.save(file);
                })
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id " + id));
    }

    public List<Files> updateMultipleFiles(List<Files> files) {
        User loggedInUser = getLoggedInUser();
        files.forEach(file -> file.setResponsibleUser(loggedInUser));
        return filesRepository.saveAll(files);
    }

    public void deleteFile(Long id) {
        filesRepository.deleteById(id);
    }

    public void deleteMultipleFiles(List<Long> ids) {
        filesRepository.deleteAllById(ids);
    }
}
