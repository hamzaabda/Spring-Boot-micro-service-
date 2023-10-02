package tn.esprit.Authentication.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.Authentication.entities.AppUser;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findAppUserByUsername(String username);
    Optional<AppUser> findAppUserByEmail(String email);

    public boolean existsByEmail(String email);
}
