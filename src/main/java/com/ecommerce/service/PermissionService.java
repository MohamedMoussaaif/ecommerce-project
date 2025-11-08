package com.ecommerce.service;

import com.ecommerce.entity.Permission;
import com.ecommerce.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public String addPermission(Permission permission){
        permissionRepository.save(permission);
        return "Permission added successfully";
    }

    public Permission findPermissionByName(String name){
        return permissionRepository.findByPermissionName(name).orElse(null);
    }
}
