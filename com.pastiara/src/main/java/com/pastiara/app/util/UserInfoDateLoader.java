package com.pastiara.app.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pastiara.app.model.User;
import com.pastiara.app.repository.UserRepository;

@Component
@Order(1)
@Profile("devh2")
public class UserInfoDateLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoDateLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Inicializamos el encoder
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Probando guardar los datos de un usuario");

        // Guardamos usuarios con password encriptado
        userRepository.save(new User("vic@gmail.com", passwordEncoder.encode("122345")));
        userRepository.save(new User("vicmauel@gmail.com", passwordEncoder.encode("12234325")));
        userRepository.save(new User("pastiara@gmail.com", passwordEncoder.encode("4234sdfs")));

        // Borramos usuario con id 2
        userRepository.deleteById(2L);
    }
}