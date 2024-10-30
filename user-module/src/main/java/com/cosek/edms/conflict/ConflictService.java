package com.cosek.edms.conflict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConflictService {

    @Autowired
    private ConflictRepository repository;

    public List<ConflictOfInterest> getAllConflicts() {
        return repository.findAll();
    }

    public Optional<ConflictOfInterest> getConflictById(Long id) {
        return repository.findById(id);
    }

    public ConflictOfInterest createConflict(ConflictOfInterest conflict) {
        return repository.save(conflict);
    }

    public ConflictOfInterest updateConflict(Long id, ConflictOfInterest conflictDetails) {
        return repository.findById(id)
                .map(conflict -> {
                    conflict.setName(conflictDetails.getName());
                    conflict.setPosition(conflictDetails.getPosition());
                    conflict.setDepartment(conflictDetails.getDepartment());
                    conflict.setNatureOfInterest(conflictDetails.getNatureOfInterest());
                    conflict.setDate(conflictDetails.getDate());
                    return repository.save(conflict);
                })
                .orElseThrow(() -> new RuntimeException("Conflict not found with id " + id));
    }

    public void deleteConflict(Long id) {
        repository.deleteById(id);
    }
}
