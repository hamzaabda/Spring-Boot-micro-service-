package tn.esprit.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.usermanagement.entities.AppUser;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser , Integer> {

    Optional<AppUser> findAppUserByEmail(String username);
}
