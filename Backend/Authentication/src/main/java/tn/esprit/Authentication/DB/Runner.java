package tn.esprit.Authentication.DB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.entities.AppUser;

@Service
public class Runner implements CommandLineRunner {

    private UserAuthRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Runner(UserAuthRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        AppUser user = new AppUser();
        user.setEmail("achraf.abbes@esprit.tn");
        user.setPassword(passwordEncoder.encode("Achraf123!"));
        user.setIsEnabled(true);
        userRepository.save(user);
        AppUser user1 = new AppUser();
        user1.setEmail("dhia@esprit.tn");
        user1.setPassword(passwordEncoder.encode("Achraf123!"));
        userRepository.save(user1);

        AppUser user2 = new AppUser();
        user1.setEmail("achraf.abbes@hotmail.tn");
        user1.setPassword(passwordEncoder.encode("Achraf123!"));
        userRepository.save(user2);


        AppUser user3 = new AppUser();
        user1.setEmail("hamza@esprit.tn");
        user1.setPassword(passwordEncoder.encode("Achraf123!"));
        userRepository.save(user3);
    }
}