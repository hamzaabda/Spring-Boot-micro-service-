package tn.esprit.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.usermanagement.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}