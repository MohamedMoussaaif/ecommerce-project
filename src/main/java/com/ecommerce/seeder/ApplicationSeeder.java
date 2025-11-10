package com.ecommerce.seeder;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Permission;
import com.ecommerce.entity.Role;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.PermissionService;
import com.ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class ApplicationSeeder {

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, PermissionService permissionService, RoleService roleService, CategoryService categoryService) {
        return args -> {

            //Permissions seeder

            Set<Permission> adminPer = new HashSet<>();
            Set<Permission> customerPer = new HashSet<>();

            List<String> adminPermissions = List.of(
                    "user:create", "user:read", "user:update", "user:delete",
                    "product:create", "product:read", "product:update", "product:delete",
                    "order:read", "order:update", "order:delete",
                    "role:manage", "permission:manage"
            );

            List<String> customerPermissions = List.of(
                    "product:read",
                    "cart:add", "cart:remove",
                    "order:create", "order:read_own",
                    "profile:read", "profile:update"
            );

            for(String p : adminPermissions){
                Permission per = permissionService.findPermissionByName(p);
                if(per == null){
                    Permission permission = new Permission();
                    permission.setPermissionName(p);
                    permissionService.addPermission(permission);
                    adminPer.add(permission);
                } else {
                    adminPer.add(per);
                }
            }
            for(String p : customerPermissions){
                Permission per = permissionService.findPermissionByName(p);
                if(per == null){
                    Permission permission = new Permission();
                    permission.setPermissionName(p);
                    permissionService.addPermission(permission);
                    customerPer.add(permission);
                } else{
                    customerPer.add(per);
                }
            }

            //Roles Seeder

            /*Role role1 = new Role();
            role1.setRoleName("ROLE_ADMIN");
            role1.setPermissions(adminPer);
            Role role2 = new Role();
            role2.setRoleName("ROLE_CUSTOMER");
            role2.setPermissions(customerPer);

            roleService.addRole(role1);
            roleService.addRole(role2);*/


            //Category Seeder

            List<String> categories = List.of(
                    "Electronics",
                    "Home goods",
                    "Health care",
                    "Women's fashion",
                    "Men's fashion"
            );

            for(String c : categories){
                Category cat = categoryService.findByName(c);
                if(cat == null){
                    Category category = new Category();
                    category.setName(c);
                    categoryService.addCategory(category);
                }
            }
        };
    }
}
