package edu.miu.cs544.identityprovider;

import edu.miu.cs544.identityprovider.dto.RoleDto;
import edu.miu.cs544.identityprovider.dto.UserCreateDto;
import edu.miu.cs544.identityprovider.service.RoleService;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUsers();
        loginUsingSuperAdmin();
        createRoles();
        addRoleToUsers();
    }

    private void addRoleToUsers() {
        userSecurityService.addRoleToUser(1L,1L);
        userSecurityService.addRoleToUser(2L,2L);
        userSecurityService.addRoleToUser(3L,3L);
        userSecurityService.addRoleToUser(4L,4L);
    }

    private void loginUsingSuperAdmin() {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("super@admin.com", "super.admin")
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void createUsers() {
        UserCreateDto userCreateDto = new UserCreateDto();

        userCreateDto.setFirstName("Super");
        userCreateDto.setLastName("Admin");
        userCreateDto.setEmail("super@admin.com");
        userCreateDto.setPhoneNumber("5555555555");
        userCreateDto.setPassword("super.admin");
        userCreateDto.setDateOfBirth(LocalDate.now().minusYears(1));
        userCreateDto.setImageUrl("profiles/super.jpg");
        userSecurityService.createUser(userCreateDto);

        userCreateDto.setFirstName("Jhone");
        userCreateDto.setLastName("Mieckel");
        userCreateDto.setEmail("jhone@gmail.com");
        userCreateDto.setPhoneNumber("555-123-4567");
        userCreateDto.setPassword("123");
        userCreateDto.setDateOfBirth(LocalDate.now().minusYears(20));
        userCreateDto.setImageUrl("profiles/jhone.jpg");
        userSecurityService.createUser(userCreateDto);

        userCreateDto.setFirstName("Mathew");
        userCreateDto.setLastName("Barel");
        userCreateDto.setEmail("mathew@gmail.com");
        userCreateDto.setPhoneNumber("555-987-6543");
        userCreateDto.setPassword("123");
        userCreateDto.setDateOfBirth(LocalDate.now().minusYears(30));
        userCreateDto.setImageUrl("profiles/mathew.jpg");
        userSecurityService.createUser(userCreateDto);

        userCreateDto.setFirstName("Linda");
        userCreateDto.setLastName("Tompson");
        userCreateDto.setEmail("linda@gmail.com");
        userCreateDto.setPhoneNumber("555-456-7890");
        userCreateDto.setPassword("123");
        userCreateDto.setDateOfBirth(LocalDate.now().minusYears(25));
        userCreateDto.setImageUrl("profiles/linda.jpg");
        userSecurityService.createUser(userCreateDto);

    }

    private void createRoles() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("SuperAdmin");
        roleDto.setDescription("SuperAdmin Role For Application management");
        roleService.save(roleDto);

        roleDto.setName("Admin");
        roleDto.setDescription("Admin Role For blog administration");
        roleService.save(roleDto);

        roleDto.setName("Blogger");
        roleDto.setDescription("Blogger Role For blog creation");
        roleService.save(roleDto);

        roleDto.setName("Consumer");
        roleDto.setDescription("Consumer Role For reading blogs");
        roleService.save(roleDto);
    }
}
