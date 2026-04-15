package com.example.Session1.Exercises.init;

import com.example.Session1.Exercises.model.AppUser;
import com.example.Session1.Exercises.model.Role;
import com.example.Session1.Exercises.repository.AppUserRepository;
import com.example.Session1.Exercises.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitalizer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DataInitalizer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        // Opret roller kun hvis de ikke findes
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }

        // Hent rollerne fra databasen
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        // Opret brugere kun hvis de ikke findes
        if (appUserRepository.findByUsername("admin").isEmpty()) {
            AppUser adminUser = new AppUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setRoles(Set.of(adminRole));
            appUserRepository.save(adminUser);
        }

        if (appUserRepository.findByUsername("user").isEmpty()) {
            AppUser userUser = new AppUser();
            userUser.setUsername("user");
            userUser.setPassword(passwordEncoder.encode("user"));
            userUser.setRoles(Set.of(userRole));
            appUserRepository.save(userUser);
        }
    }
}