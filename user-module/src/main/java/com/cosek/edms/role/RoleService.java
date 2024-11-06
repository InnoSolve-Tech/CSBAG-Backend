package com.cosek.edms.role;

import com.cosek.edms.exception.NotFoundException;
import com.cosek.edms.permission.Permission;
import com.cosek.edms.permission.PermissionRepository;
import com.cosek.edms.permission.PermissionService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;


    @Autowired
    private PermissionRepository permissionRepository;

    public Role createRole(Role request) {
        return roleRepository.save(request);
    }

    public Role findOneRole(Long roleId) throws NotFoundException {
        return roleRepository.findById(roleId).orElse(null);
    }

    public Role addPermissionToRole(Long roleID, Long permID) throws NotFoundException {
        Role role = findOneRole(roleID);

        Permission permission = permissionService.findOnePermission(permID);
        Set<Permission> permissions = role.getPermissions();
        permissions.add(permission);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public Role removePermissionFromRole(Long roleId, Long permId) throws NotFoundException {
        // Find the role by its ID
        Role role = findOneRole(roleId);

        // Check if the role exists
        if (role == null) {
            throw new NotFoundException("Role not found with ID: " + roleId);
        }

        // Find the permission by its ID
        Permission permission = permissionService.findOnePermission(permId);

        // Check if the permission exists
        if (permission == null) {
            throw new NotFoundException("Permission not found with ID: " + permId);
        }

        // Remove the permission from the role
        Set<Permission> permissions = role.getPermissions();
        if (permissions.contains(permission)) {
            permissions.remove(permission);
            role.setPermissions(permissions);
        }

        // Save the role with updated permissions
        return roleRepository.save(role);
    }


    @Transactional
    public Role addMultiplePermissions(Long roleId, boolean status, List<Long> permissionIds) throws NotFoundException {
        // Find the role by its ID
        Role role = findOneRole(roleId);

        // Check if the role exists
        if (role == null) {
            throw new NotFoundException("Role not found with ID: " + roleId);
        }

        // Fetch permissions from the database
        List<Permission> permissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            Permission permission = permissionService.findOnePermission(permissionId);
            if (permission != null) {
                permissions.add(permission);
            } else {
                throw new NotFoundException("Permission not found with ID: " + permissionId);
            }
        }

        // Handle adding permissions
        if (status) {
            for (Permission permission : permissions) {
                // Add permission only if it's not already assigned to the role
                if (!role.getPermissions().contains(permission)) {
                    role.getPermissions().add(permission);
                }
            }
        } else {
            // Handle removing permissions
            for (Permission permission : permissions) {
                // Remove permission only if it exists in the role
                role.getPermissions().remove(permission);
            }
        }

        // Save the role with updated permissions
        return roleRepository.save(role);
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public Role findByRoleName(String roleName) throws NotFoundException {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found: " + roleName));
    }

    @Transactional()  // Ensures that the session stays open during the method execution
    public Role getRoleWithPermissions(String roleName) {
        Optional<Role> role = roleRepository.findByNameWithPermissions(roleName);

        return role.orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
    }

    @PostConstruct
    public void initializeRolesAndPermissions() {
        // Files permissions
        Permission readBids = permissionRepository.findByName("READ_BIDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_BIDS", new HashSet<>())));
        Permission createBids = permissionRepository.findByName("CREATE_BIDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_BIDS", new HashSet<>())));
        Permission updateBids = permissionRepository.findByName("UPDATE_BIDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "UPDATE_BIDS", new HashSet<>())));
        Permission deleteBids = permissionRepository.findByName("DELETE_BIDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_BIDS", new HashSet<>())));

        Permission readRequisition = permissionRepository.findByName("READ_REQUISITION")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_REQUISITION", new HashSet<>())));
        Permission createRequisition  = permissionRepository.findByName("CREATE_REQUISITION")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_REQUISITION", new HashSet<>())));
        Permission updateRequisition  = permissionRepository.findByName("UPDATE_REQUISITION")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "UPDATE_REQUISITION", new HashSet<>())));
        Permission deleteRequisition  = permissionRepository.findByName("DELETE_REQUISITION")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_REQUISITION", new HashSet<>())));


        // Folders permissions
        Permission readConflicts = permissionRepository.findByName("READ_CONFLICTS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_CONFLICTS", new HashSet<>())));
        Permission createConflicts = permissionRepository.findByName("CREATE_CONFLICTS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_CONFLICTS", new HashSet<>())));
        Permission updateConflicts = permissionRepository.findByName("UPDATE_CONFLICTS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "UPDATE_CONFLICTS", new HashSet<>())));
        Permission deleteConflicts = permissionRepository.findByName("DELETE_CONFLICTS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_CONFLICTS", new HashSet<>())));

        // Case Studies permissions
        Permission readMemo = permissionRepository.findByName("READ_MEMO")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_MEMO", new HashSet<>())));
        Permission createMemo = permissionRepository.findByName("CREATE_MEMO")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_MEMO", new HashSet<>())));
        Permission updateMemo = permissionRepository.findByName("UPDATE_MEMO")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "UPDATE_MEMO", new HashSet<>())));
        Permission deleteMemo = permissionRepository.findByName("DELETE_MEMO")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_MEMO", new HashSet<>())));

        // Existing User and Role Permissions
        Permission createUser = permissionRepository.findByName("CREATE_USER")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_USER", new HashSet<>())));
        Permission deleteUser = permissionRepository.findByName("DELETE_USER")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_USER", new HashSet<>())));
        Permission readUser = permissionRepository.findByName("READ_USER")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_USER", new HashSet<>())));


        // Existing User and Role Permissions
        Permission createFunds = permissionRepository.findByName("CREATE_FUNDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_FUNDS", new HashSet<>())));
        Permission deleteFunds = permissionRepository.findByName("DELETE_FUNDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_FUNDS", new HashSet<>())));
        Permission readFunds = permissionRepository.findByName("READ_FUNDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_FUNDS", new HashSet<>())));
        Permission updateFunds = permissionRepository.findByName("UPDATE_FUNDS")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "UPDATE_FUNDS", new HashSet<>())));

        // Assign permissions to roles
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    role.setPermissions(new HashSet<>(Arrays.asList(
                            readUser,
                            readBids, createBids, updateBids, deleteBids,
                            updateRequisition,createRequisition
                    )));
                    return role;
                });

        Role officerRole = roleRepository.findByName("OFFICER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("OFFICER");
                    role.setPermissions(new HashSet<>(Arrays.asList(
                            readBids,readRequisition
                    )));
                    return role;
                });

        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("MANAGER");
                    role.setPermissions(new HashSet<>(Arrays.asList(
                            readBids,readRequisition
                    )));
                    return role;
                });

        Role deputyRole = roleRepository.findByName("DEPUTY")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("DEPUTY");
                    role.setPermissions(new HashSet<>(Arrays.asList(
                            readBids,
                            readRequisition,updateRequisition
                    )));
                    return role;
                });


        // Save roles
        roleRepository.save(adminRole);
        roleRepository.save(officerRole);
        roleRepository.save(managerRole);
        roleRepository.save(deputyRole);


    }

    public Role addPermissionToRole(String roleName, String permissionName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new RuntimeException("Permission not found"));

        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }

    public Role removePermissionFromRole(String roleName, String permissionName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new RuntimeException("Permission not found"));

        role.getPermissions().remove(permission);
        return roleRepository.save(role);
    }

}
