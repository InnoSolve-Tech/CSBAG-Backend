package com.cosek.edms.departments;

import com.cosek.edms.departments.DepartmentModule.DepartmentUserAssign;
import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.user.User;
import com.cosek.edms.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.cosek.edms.departments.DepartmentService;

//import jakarta.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final UserService userService;


    @PostMapping("/create-department")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.createDepartment(department);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDepartment.getId())
                .toUri();
        return ResponseEntity.created(location).body(newDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping("/departmentById/{id}")
    public ResponseEntity<List<Department>> getDepartmentsByUserId(@PathVariable Long id) {
        List<Department> departments = departmentService.getDepartmentsByUserId(id);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{departmentName}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String departmentName) throws NotFoundException {
        return ResponseEntity.ok(departmentService.getDepartmentByName(departmentName));
    }

    @GetMapping("/")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @Validated @RequestBody Department departmentDetails) throws NotFoundException {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) throws NotFoundException {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign-user-to-department")
    public ResponseEntity<?> assignDepartmentToUser(@RequestBody DepartmentUserAssign request) {
        try {
            Long userId = request.getUserId();
            List<Long> departmentIds = request.getDepartmentIds();

            if (departmentIds == null || departmentIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department list cannot be empty.");
            }

            // Assign multiple departments to the user
            User updatedUser = departmentService.assignUserDepartments(userId, departmentIds);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/unassign-user-from-department")
    public ResponseEntity<?> unassignUserFromDepartment(@RequestBody DepartmentUserAssign request) {
        try {
            Long userId = request.getUserId();
            List<Long> departmentIds = request.getDepartmentIds();

            // If departmentIds is empty, unassign user from all departments
            User updatedUser;
            if (departmentIds == null || departmentIds.isEmpty()) {
                updatedUser = departmentService.unassignAllDepartmentsFromUser(userId);
            } else {
                updatedUser = departmentService.unassignUserDepartments(userId, departmentIds);
            }

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDepartments(@PathVariable Long userId) {
        try {
            List<Long> departmentIds = departmentService.getDepartmentIdsByUserId(userId);
            return ResponseEntity.ok(Collections.singletonMap("departmentIds", departmentIds));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving departments.");
        }
    }
}