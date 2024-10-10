package com.cosek.edms.casestudy;

import com.cosek.edms.user.User;
import com.cosek.edms.role.Role;
import com.cosek.edms.casestudy.Modals.CaseStudyRequest;
import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.role.RoleRepository;
import com.cosek.edms.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CaseStudyService {

    @Autowired
    private CaseStudyRepository caseStudyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  UserRepository userRepository;

    // Fetch all case studies
    public List<CaseStudy> getAllCaseStudies() {
        return caseStudyRepository.findAll();
    }

    // Get a case study by ID
    public Optional<CaseStudy> getCaseStudyById(Long id) {
        return caseStudyRepository.findById(id);
    }

    // Save a new case study
    public CaseStudy saveCaseStudy(CaseStudy caseStudy) {
        return caseStudyRepository.save(caseStudy);
    }

    // Update an existing case study
    public CaseStudy updateCaseStudy(Long id, CaseStudy updatedCaseStudy) {
        Optional<CaseStudy> caseStudyOptional = caseStudyRepository.findById(id);
        if (caseStudyOptional.isEmpty()) {
            throw new RuntimeException("Case study not found");
        }

        CaseStudy caseStudy = caseStudyOptional.get();
        caseStudy.setName(updatedCaseStudy.getName());
        caseStudy.setDescription(updatedCaseStudy.getDescription());
        caseStudy.setUsers(updatedCaseStudy.getUsers());
        return caseStudyRepository.save(caseStudy);
    }

    // Delete a case study
    public void deleteCaseStudy(Long id) {
        caseStudyRepository.deleteById(id);
    }

    public CaseStudy createCaseStudy(CaseStudyRequest request, Set<Role> roles, Set<User> users) {
        CaseStudy newCaseStudy = CaseStudy.builder()
                .name(request.getName())
                .description(request.getDescription())
                .users(users == null ? new HashSet<>() : users)
                .roles(roles == null ? new HashSet<>() : roles)  // Assign roles set
                .build();

        return caseStudyRepository.save(newCaseStudy);
    }

    // Find a role by ID for assignment
    public Role findOneRole(Long roleId) throws NotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    public CaseStudy assignUserToCaseStudy(Long caseStudyId, Long userId) throws NotFoundException {
        Optional<CaseStudy> caseStudyOptional = caseStudyRepository.findById(caseStudyId);
        if (caseStudyOptional.isEmpty()) {
            throw new NotFoundException("Case Study not found");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        CaseStudy caseStudy = caseStudyOptional.get();
        User user = userOptional.get();

        // Add the user to the case study's set of users
        caseStudy.getUsers().add(user);

        // Persist the updated case study
        return caseStudyRepository.save(caseStudy);
    }
}
