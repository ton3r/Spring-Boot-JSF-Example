package application.configuration.startup;

import application.entity.SystemUser;
import application.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seed information injection
 */
@Component
public class DataInject {

    @Autowired
    SystemUserRepository systemUserRepository;

    public void insertUsers(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("12345");

        SystemUser systemUser = new SystemUser();
        systemUser.setUser("usuario");
        systemUser.setRole("USER");
        systemUser.setPassword(hashedPassword);
        systemUserRepository.save(systemUser);

        systemUser = new SystemUser();
        systemUser.setUser("admin");
        systemUser.setRole("ADMIN");
        systemUser.setPassword(hashedPassword);
        systemUserRepository.save(systemUser);

    }
}
