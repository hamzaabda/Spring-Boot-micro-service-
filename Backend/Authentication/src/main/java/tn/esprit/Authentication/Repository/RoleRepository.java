package tn.esprit.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.Authentication.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}
