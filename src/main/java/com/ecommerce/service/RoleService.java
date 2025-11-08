package com.ecommerce.service;

import com.ecommerce.entity.Role;
import com.ecommerce.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public String addRole(Role role){
        roleRepository.save(role);
        return "Role added successfully";
    }

    public Role findRoleByName(String name){
        return roleRepository.findByRoleName(name).orElse(null);
    }
}
